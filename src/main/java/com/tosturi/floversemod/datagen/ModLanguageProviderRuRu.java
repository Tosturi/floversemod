package com.tosturi.floversemod.datagen;

import com.tosturi.floversemod.FloVerseMod;
import com.tosturi.floversemod.block.ModBlocks;
import com.tosturi.floversemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProviderRuRu extends LanguageProvider {

    public ModLanguageProviderRuRu(PackOutput output) {
        super(output, FloVerseMod.MODID, "ru_ru");
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.FLORICS, "Флорики");
        addItem(ModItems.TIGER_GIRL_SUMMONING_EGG, "Яйцо Вызова Девочки Тигрицы");
        addBlock(ModBlocks.FLORICS_BOX, "Ящик Флориков");
        add("entity.floversemod.tiger_girl", "Девочка Тигрица");
        add("floverse_tab.title", "Flo-Verse Мод");
    }
}
