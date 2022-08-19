package team.lodestar.fufo.client.rendering.entity.weave;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import java.awt.*;

public class WeaveEntityRenderer extends AbstractWeaveEntityRenderer{
    public WeaveEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.beamColor = new Color(187,123,0,255);
    }
}
