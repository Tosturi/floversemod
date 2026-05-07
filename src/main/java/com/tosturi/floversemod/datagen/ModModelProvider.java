package com.tosturi.floversemod.datagen;

import com.tosturi.floversemod.FloVerseMod;
import com.tosturi.floversemod.block.ModBlocks;
import com.tosturi.floversemod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, FloVerseMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(ModBlocks.FLORICS_BOX.get());
        itemModels.generateFlatItem(ModItems.FLORICS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TIGER_GIRL_SUMMONING_EGG.get(), ModelTemplates.FLAT_ITEM);
    }
}
