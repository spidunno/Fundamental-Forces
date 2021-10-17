package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.EmpiricalEsoterica;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EmpiricalEsoterica.MOD_ID);

}
