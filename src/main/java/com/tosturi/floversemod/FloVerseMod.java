package com.tosturi.floversemod;

import com.tosturi.floversemod.block.ModBlocks;
import com.tosturi.floversemod.datagen.ModLanguageProvider;
import com.tosturi.floversemod.datagen.ModLanguageProviderRuRu;
import com.tosturi.floversemod.datagen.ModLootTableProvider;
import com.tosturi.floversemod.datagen.ModModelProvider;
import com.tosturi.floversemod.datagen.ModRecipeProvider;
import com.tosturi.floversemod.entity.ModEntities;
import com.tosturi.floversemod.entity.custom.TigerGirlEntity;
import com.tosturi.floversemod.item.ModItems;
import com.tosturi.floversemod.sound.ModSounds;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(com.tosturi.floversemod.FloVerseMod.MODID)
public class FloVerseMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "floversemod";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FloVerseMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerAttributes);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::onGatherDataServer);
        modEventBus.addListener(this::onGatherDataClient);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        FloVerseCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TIGER_GIRL.get(), TigerGirlEntity.createAttributes().build());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    private void onGatherDataServer(GatherDataEvent.Server event) {
        event.createProvider(ModRecipeProvider::new);
        event.createProvider(ModLootTableProvider::new);
    }

    private void onGatherDataClient(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(output -> new ModLanguageProvider(output, "en_us"));
        event.createProvider(ModLanguageProviderRuRu::new);
    }
}
