package com.tosturi.floversemod.datagen;

import com.tosturi.floversemod.FloVerseMod;
import com.tosturi.floversemod.block.ModBlocks;
import com.tosturi.floversemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, FloVerseMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.FLORICS, "Florics");
        addItem(ModItems.TIGER_GIRL_SUMMONING_EGG, "Tiger Girl Summoning Egg");
        addBlock(ModBlocks.FLORICS_BOX, "Florics Box");
        add("entity.floversemod.tiger_girl", "Tiger Girl");
        add("floverse_tab.title", "FloVerse");
    }
}
