package com.project_esoterica.esoterica.core.systems.screenshake;

import com.project_esoterica.esoterica.core.config.ClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;

import java.util.Random;

public class ScreenshakeHandler {

    public static float intensity;
    public static float falloff = 0.98f;
    public static float yawOffset;
    public static float pitchOffset;

    public static void cameraTick(Camera camera, Random random)
    {
        yawOffset = randomizeOffset(random);
        pitchOffset = randomizeOffset(random);
        camera.setRotation(camera.getYRot()+yawOffset, camera.getXRot()+pitchOffset);
    }
    public static void clientTick(Random random)
    {
        if (intensity > 0)
        {
            intensity *= falloff;
        }
    }
    public static void addScreenshake(float factor, float newFalloff)
    {
        float max = ClientConfig.MAX_SCREENSHAKE_INTENSITY.get();
        float needed = max - Math.min(intensity,max);
        intensity += Mth.lerp(factor, 0, needed);
        falloff = newFalloff;
    }
    public static float randomizeOffset(Random random)
    {
        return Mth.nextFloat(random, -intensity*2, intensity*2);
    }
}
