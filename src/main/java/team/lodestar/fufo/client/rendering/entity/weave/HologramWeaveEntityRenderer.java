package team.lodestar.fufo.client.rendering.entity.weave;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import java.awt.*;

public class HologramWeaveEntityRenderer extends AbstractWeaveEntityRenderer{
    public HologramWeaveEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.beamColor = new Color(0, 123, 187, 255);
    }
}
