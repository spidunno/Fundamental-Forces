package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.math.Vector3f;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

public class WorldHighlightFx extends DynamicShaderFxInstance {
    public Vector3f center;
    public float radius;
    public Vector3f color;

    public WorldHighlightFx(Vector3f center, float radius, Vector3f color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, center.x());
        writer.accept(1, center.y());
        writer.accept(2, center.z());
        writer.accept(3, radius);
        writer.accept(4, color.x());
        writer.accept(5, color.y());
        writer.accept(6, color.z());
    }
}
