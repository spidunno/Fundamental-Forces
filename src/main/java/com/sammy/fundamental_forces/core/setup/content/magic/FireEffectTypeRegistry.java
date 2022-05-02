package com.sammy.fundamental_forces.core.setup.content.magic;


import com.sammy.ortus.systems.fireeffect.FireEffectType;

import static com.sammy.ortus.setup.OrtusFireEffectRegistry.registerType;

public class FireEffectTypeRegistry {
    public static final FireEffectType METEOR_FIRE = registerType(new FireEffectType("meteor_fire", 1, 20));
    public static final FireEffectType GREATER_FIRE = registerType(new FireEffectType("greater_fire", 1, 10));
}