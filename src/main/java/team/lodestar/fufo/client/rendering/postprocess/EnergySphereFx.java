package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.math.Vector3f;
import team.lodestar.lodestone.helpers.util.Color;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

public class EnergySphereFx extends DynamicShaderFxInstance {
    public Vector3f center;
    public Color color;
    public float radius;
    public float intensity;

    public EnergySphereFx(Vector3f pos, Color color, float radius, float intensity) {
        this.center = pos;
        this.color = color;
        this.radius = radius;
        this.intensity = intensity;
    }

    public EnergySphereFx(Vector3f pos, float radius, float intensity) {
        this(pos, new Color(.2F, .1F, .4F, 1F), radius, intensity);
    }

    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, center.x());
        writer.accept(1, center.y());
        writer.accept(2, center.z());
        writer.accept(3, color.getRedAsFloat());
        writer.accept(4, color.getGreenAsFloat());
        writer.accept(5, color.getBlueAsFloat());
        writer.accept(6, radius);
        writer.accept(7, intensity);
    }
}
