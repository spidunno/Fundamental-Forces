package team.lodestar.fufo.client.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class TemporaryWorldVFXBuilderExtension {

    public static VFXBuilders.WorldVFXBuilder renderQuad(VFXBuilders.WorldVFXBuilder builder, VertexConsumer vertexConsumer, PoseStack stack, float width, float height) {
        Vector3f[] positions = new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(1, -1, 0), new Vector3f(1, 1, 0), new Vector3f(-1, 1, 0)};
        return builder.renderQuad(vertexConsumer, stack, positions, width, height);
    }


}
