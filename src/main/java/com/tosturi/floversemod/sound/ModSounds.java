package com.tosturi.floversemod.sound;

import com.tosturi.floversemod.FloVerseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, FloVerseMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TIGER_GIRL_TRADE =
            SOUND_EVENTS.register("tiger_girl.trade",
                    () -> SoundEvent.createVariableRangeEvent(
                            Identifier.fromNamespaceAndPath(FloVerseMod.MODID, "tiger_girl.trade")));
}
