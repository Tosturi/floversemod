package com.tosturi.floversemod;

import net.neoforged.neoforge.common.ModConfigSpec;


public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue RESPAWN_DELAY_TICKS;
    public static final ModConfigSpec.DoubleValue VILLAGE_RADIUS;

    static {
        RESPAWN_DELAY_TICKS = BUILDER
                .comment("Ticks before TigerGirl respawns after death (default 3000 ≈ 2.5 min)")
                .defineInRange("respawnDelayTicks", 3000, 0, Integer.MAX_VALUE);
        VILLAGE_RADIUS = BUILDER
                .comment("Radius around the bell to search for living villagers")
                .defineInRange("villageRadius", 80.0, 1.0, 512.0);
    }

    static final ModConfigSpec SPEC = BUILDER.build();
}