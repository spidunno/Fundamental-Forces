package com.project_esoterica.empirical_esoterica.core.systems.screenshake;

import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallInstance;
import com.project_esoterica.empirical_esoterica.core.config.ClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import oshi.util.tuples.Pair;

import java.util.Random;

public class ScreenshakeHandler {

    public static float intensity;

    public static float yawOffset;
    public static float pitchOffset;

    public static void tick(Camera camera, Random random)
    {
        if (intensity > 0)
        {
           intensity = Mth.lerp(0.8f, intensity, 0);
        }
        yawOffset = randomizeOffset(random);
        pitchOffset = randomizeOffset(random);
        camera.setRotation(camera.getYRot()+yawOffset, camera.getXRot()+pitchOffset);

    }
    public static void increaseIntensity(float factor)
    {
        float needed = ClientConfig.MAX_SCREENSHAKE_INTENSITY.get() - intensity;
        intensity += Mth.lerp(factor, 0, needed);
    }
    public static float randomizeOffset(Random random)
    {
        return Mth.nextFloat(random, -intensity*2, intensity*2);
    }
}
