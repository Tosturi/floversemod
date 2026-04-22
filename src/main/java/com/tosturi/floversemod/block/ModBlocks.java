package com.tosturi.floversemod.block;

import com.tosturi.floversemod.FloVerseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.tosturi.floversemod.item.ModItems.ITEMS;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FloVerseMod.MODID);

    public static final DeferredBlock<Block> FLORICS_BOX = BLOCKS.registerBlock(
            "florics_box",
            properties -> new Block(properties
                    .destroyTime(1.5f)
                    .explosionResistance(3.0f)
                    .sound(SoundType.WOOD)
            )
    );

    public static final DeferredItem<BlockItem> FLORICS_BOX_ITEM = ITEMS.registerSimpleBlockItem(
            "florics_box",
            ModBlocks.FLORICS_BOX
    );
}