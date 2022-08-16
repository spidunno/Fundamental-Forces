package team.lodestar.fufo.client.ui.component;

import team.lodestar.fufo.client.ui.constraint.DimensionConstraint;
import team.lodestar.fufo.client.ui.constraint.PixelConstraint;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

/**
 * Simple extension of {@link FlexBox} for a box with a width, height, texture, opacity, and color.
 */
public class QuickBox extends FlexBox {

    public QuickBox(DimensionConstraint width, DimensionConstraint height, ResourceLocation shader, Color color, float opacity) {
        super();
        this.withWidth(width).withHeight(height).withShaderTexture(shader).withColor(color).withOpacity(opacity);
    }

    public QuickBox(double width, double height, ResourceLocation shader, Color color, float opacity) {
        this(new PixelConstraint(width), new PixelConstraint(height), shader, color, opacity);
    }

    public QuickBox(DimensionConstraint width, DimensionConstraint height, Color color, float opacity) {
        super();
        this.withWidth(width).withHeight(height).withColor(color).withOpacity(opacity);
    }

    public QuickBox(double width, double height, Color color, float opacity) {
        this(new PixelConstraint(width), new PixelConstraint(height), color, opacity);
    }


}
