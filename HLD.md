# FloVerse Mod — High-Level Design

## Purpose

A NeoForge mod created for the streamer **Floneriel**. It introduces a single custom NPC — the **TigerGirl** — that automatically appears in every Minecraft village, sells a custom currency item (**Florics**), and respawns after being killed. The mod is intentionally minimal: one NPC, one item, one block, one economy loop.

---

## File Tree

```
src/main/
├── java/com/tosturi/floversemod/
│   ├── FloVerseMod.java                  — mod entry point, event bus wiring
│   ├── FloVerseCreativeModeTabs.java     — creative tab registration
│   ├── Config.java                       — configurable constants
│   ├── block/ModBlocks.java              — block registry
│   ├── item/ModItems.java                — item registry
│   ├── entity/
│   │   ├── ModEntities.java              — entity type registry
│   │   └── custom/TigerGirlEntity.java  — NPC logic and AI
│   │   └── client/PlaceholderRenderer.java — temporary renderer (vanilla villager skin)
│   ├── data/TigerGirlVillageData.java    — persistent world state (SavedData)
│   ├── event/
│   │   ├── TigerGirlVillageHandler.java  — spawn / death / respawn event logic
│   │   └── TigerGirlRespawnManager.java  — respawn timer manager
│   └── datagen/
│       ├── ModModelProvider.java         — block + item model generation
│       ├── ModRecipeProvider.java        — crafting recipe generation
│       ├── ModLootTableProvider.java     — block loot table generation
│       ├── ModLanguageProvider.java      — English translations
│       └── ModLanguageProviderRuRu.java  — Russian translations
└── resources/
    └── data/floversemod/
        ├── trade_set/tiger_girl_trades.json
        └── villager_trade/
            ├── gold_to_florics.json
            └── diamond_to_florics.json
```

---

## Content

### Florics (item)
A custom currency. Has no use beyond being traded for. Appears in the FloVerse creative tab.

### Florics Box (block)
A storage-compression block. Crafted from a 3×3 grid of Florics (9 → 1 box). Breaks back into 9 Florics when mined (loot table). Primarily exists as a convenient way to store large quantities of Florics.

### TigerGirl (NPC entity)
A villager-like NPC that inhabits villages. She does not despawn naturally, does not breed, and cannot age. She is the only source of Florics in the world.

---

## Systems

### 1 — Village Detection

**Trigger:** every time any villager entity joins a server level (world load, chunk load, natural spawn).

**Logic:**
1. Take the villager's current position.
2. Search the POI (Point of Interest) system for a `MEETING` type POI (the bell) within 64 blocks.
3. If a bell is found, check whether this village is already tracked in persistent storage.
4. If it is new — register it and immediately spawn a TigerGirl near the bell.
5. If it is already registered — do nothing.

**Why villager position instead of HOME memory:** the HOME brain memory is not populated when `EntityJoinLevelEvent` fires on world load (memories restore after the event). The villager's physical position is always available and is always near the village.

**Village key:** the bell position is snapped to the nearest 32-block grid (X and Z only) to prevent multiple registrations for the same village due to minor position differences.

**Multiple bells:** if a second bell is found within `VILLAGE_RADIUS` blocks of an already-registered village, it is treated as belonging to the same village and ignored. This ensures villages with more than one bell still get exactly one TigerGirl.

---

### 2 — Spawn Position Selection

When a TigerGirl needs to be spawned near a bell, the system picks a random surface position nearby:

1. Choose a random offset within a radius derived from `VILLAGE_RADIUS` config (capped at 16 blocks).
2. Query the heightmap for the top solid surface at that X/Z.
3. Validate the candidate: the surface block and the one below it must both be solid (rejects rooftops, which have hollow space underneath), and the two blocks above must be air (enough headroom).
4. Retry up to 10 times. If no valid position is found, the spawn is skipped (will be retried by the liveness check on the next cycle).

---

### 3 — TigerGirl AI

TigerGirl uses Minecraft's **Brain** system (the same used by vanilla villagers), which is activity-based rather than goal-based.

**CORE activity** (always running):
- Floats in water instead of drowning.
- Continuously tracks and rotates toward the current look target.

**IDLE activity** (default):
- Wanders randomly around the area at a slow pace.
- Occasionally turns to look at nearby players.

She has no combat, no schedule, and no job site. She exists purely to interact with the player via trade.

---

### 4 — Trading

**Trigger:** player right-clicks TigerGirl with an empty hand.

**Logic:**
1. If her trade offer list is empty (first interaction), load offers from the `tiger_girl_trades` trade set defined in JSON.
2. Open the standard merchant trade screen.

**Available trades (defined in JSON):**
| Cost | Reward |
|---|---|
| 8 Gold Ingots | 1 Florics |
| 1 Diamond | 4 Florics |

Both trades have unlimited uses and grant no experience. The trade set uses Minecraft's data-driven `TradeSet` / `VillagerTrade` registry, so trades can be modified without recompiling.

---

### 5 — Death and Respawn

**On death:**
1. The death event fires.
2. The system looks up which bell this TigerGirl belongs to using an in-memory UUID → bell reverse index (O(1) lookup).
3. Her UUID is cleared from the village record.
4. A respawn timer is scheduled: `currentGameTick + RESPAWN_DELAY_TICKS` (default 3000 ticks ≈ 2.5 minutes).
5. The timer is persisted to disk immediately so it survives a server restart.

**Respawn tick loop** (runs every 600 ticks ≈ 30 seconds):
1. Collect all pending respawn entries whose scheduled tick has passed.
2. For each due entry, spawn a new TigerGirl near the bell.

**Liveness check** (same 600-tick loop):
- For each registered village that has a TigerGirl UUID recorded, verify the entity still exists and is alive.
- This check is **skipped if the bell's chunk is not loaded** — an unloaded chunk means `getEntity()` would return null even for a living entity, producing a false positive.
- If the entity is genuinely missing (chunk loaded, entity not found), treat it the same as a death and schedule a respawn.

---

### 6 — Persistent State

All village data is stored per-world using Minecraft's `SavedData` mechanism (written to the world's `data/` folder as NBT).

**Stored per village (keyed by bell position):**
- TigerGirl's UUID (null if not currently spawned)
- `villageDead` flag (reserved for future use — e.g., if all villagers are gone)

**Stored globally:**
- Pending respawn timers: bell position → game tick at which to fire

**Transient (in-memory only, rebuilt on load):**
- UUID → bell reverse index for O(1) death lookups

---

### 7 — Configuration

Two values exposed in `floversemod-common.toml`:

| Key | Default | Description |
|---|---|---|
| `respawnDelayTicks` | 3000 | Ticks between TigerGirl death and respawn (~2.5 min) |
| `villageRadius` | 80.0 | Radius used for spawn position search scaling |

---

## Known Limitations

- **TigerGirl model is a placeholder** — she currently renders using the vanilla villager texture. A custom Blockbench model has not been created yet.
- **Villager with no nearby bell** — a villager that loads more than 64 blocks from any bell will not trigger village registration. The village will be registered when another villager with a closer HOME loads.
- **No hostile mob protection** — TigerGirl has no combat AI and will not flee from threats.
