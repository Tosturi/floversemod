package com.tosturi.floversemod.item;

import com.tosturi.floversemod.FloVerseMod;
import com.tosturi.floversemod.entity.ModEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.TypedEntityData;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FloVerseMod.MODID);


    public static final DeferredItem<Item> FLORICS = ITEMS.registerItem(
            "florics",
            props -> new Item(props.stacksTo(64))
    );

    public static final DeferredItem<Item> TIGER_GIRL_SUMMONING_EGG = ITEMS.registerItem(
            "tiger_girl_summoning_egg",
            props -> new SpawnEggItem(props.component(
                    DataComponents.ENTITY_DATA,
                    TypedEntityData.of(ModEntities.TIGER_GIRL.get(), new CompoundTag())
            ))
    );

}