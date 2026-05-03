package com.tosturi.floversemod.datagen;

import com.tosturi.floversemod.block.ModBlocks;
import com.tosturi.floversemod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider.Runner {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            protected void buildRecipes() {
                ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ModBlocks.FLORICS_BOX.get())
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', ModItems.FLORICS.get())
                        .unlockedBy("has_florics", has(ModItems.FLORICS.get()))
                        .save(this.output);

                ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ModItems.FLORICS.get(), 9)
                        .requires(ModBlocks.FLORICS_BOX.get())
                        .unlockedBy("has_florics_box", has(ModBlocks.FLORICS_BOX.get()))
                        .save(this.output, "florics_from_box");
            }
        };
    }

    @Override
    public String getName() {
        return "FloVerse Recipes";
    }
}
