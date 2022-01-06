package com.project_esoterica.esoterica.core.systems.screenshake;

import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class PositionedScreenshakeInstance extends ScreenshakeInstance{
    public Vec3 position;

    public PositionedScreenshakeInstance(Vec3 position, float intensity, float falloffTransformSpeed, int timeBeforeFastFalloff, float slowFalloff, float fastFalloff) {
        super(intensity, falloffTransformSpeed, timeBeforeFastFalloff, slowFalloff, fastFalloff);
        this.position = position;
    }

    @Override
    public float updateIntensity(Camera camera, float falloff) {
        float intensity = super.updateIntensity(camera, falloff);
        Vector3f lookDirection = camera.getLookVector();
        Vec3 directionToScreenshake = camera.getPosition().subtract(position).normalize();
        float angle = lookDirection.dot(new Vector3f(directionToScreenshake));
        if (angle > 0)
        {
            return 0;
        }
        return intensity * -angle;
    }
}
