package com.space_mod_group.space_mod.core.registry;

import com.space_mod_group.space_mod.SpaceMod;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SpaceMod.MOD_ID);

}
