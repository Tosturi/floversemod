package com.tosturi.floversemod.item;

import com.tosturi.floversemod.FloVerseMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FloVerseMod.MODID);


    public static final DeferredItem<Item> FLORICS = ITEMS.registerItem(
            "florics",
            props -> new Item(props.stacksTo(64))
    );

}