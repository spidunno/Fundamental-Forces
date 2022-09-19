package team.lodestar.fufo.client.ui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.transition.Transition;

import java.util.List;

/**
 * Component for text rendering
 */
public class TextComponent extends UIComponent<TextComponent>{

    /**
     * The text of this component
     */
    private String text;

    /**
     * The size, or line height of this text component in pixels
     */
    private float fontSize = 1f;

    public TextComponent(String text, double fontSize) {
        this.text = text;
        this.fontSize = (float) fontSize;
    }

    /**
     * Sets the text of this component
     * @param text The text to set
     * @return This component
     */
    public TextComponent setText(String text) {
        this.text = text;
        if(this.getParent() != null) {
            this.getParent().reform();
        }
        return this;
    }

    /**
     * Gets the text of this component
     * @return The text of this component
     */
    public String getText() {
        return text;
    }

    @Override
    public Vector2 getPreferredSize() {
        Font font = Minecraft.getInstance().font;
        float size = fontSize / font.lineHeight;
        List<FormattedCharSequence> split;
        double width;
        if(shouldUseAllocatedSpace()) {
            width = getAllocatedSize().x;
            split = font.split(Component.literal(text), (int) Math.floor(width / size));
        } else {
            if(getParent() != null) {
                width = getParent().getInnerSize().x;
                split = font.split(Component.literal(text), (int) Math.floor(width / size));
            } else {
                throw new RuntimeException("TextComponent must have a parent to use getPreferredSize()");
            }
        }
        return new Vector2(split.size() > 1 ? width :  (float)font.width(text) * size, (split.size() * (float)font.lineHeight) * size);
    }


    @Override
    public void reform() {

    }

    @Override
    public void render(PoseStack stack, double deltaTime) {
        // apply transitions
        for (Transition transition : transitions.values()) {
            transition.apply(this, deltaTime);
        }

        if(!transitions.containsKey(Transition.Type.OPACITY)) {
            animatedOpacity = getOpacity();
        }

        if(!transitions.containsKey(Transition.Type.POSITION)) {
            animatedPosition = getPosition();
        }

        if(!transitions.containsKey(Transition.Type.SIZE)) {
            animatedSize = getSize();
        }

        Vector2 topLeft = animatedPosition;

        stack.pushPose();
        stack.translate(0.0, 0.0, depth() * 0.01);
//        stack.mulPose(new Quaternion(new Vector3f(1, 0, 0), 180, true));
        stack.translate(topLeft.x, topLeft.y, 0.0);

        Font font = Minecraft.getInstance().font;

        float scale = fontSize / font.lineHeight;
        stack.scale(scale, scale, scale);


        // draw text w/wrap
//        font.draw(stack, text, 0, 0, 0xFFFFFF);
//        stack.translate(0, font.lineHeight, 0.0);
//        font.draw(stack, "Hello World", 0, 0, 0xFFFFFF);

        // TODO: precalc this shit and make it only change when the text changes
        List<FormattedCharSequence> split;
        double width;
        if(shouldUseAllocatedSpace()) {
            width = getAllocatedSize().x;
            split = font.split(Component.literal(text), (int) Math.floor(width / scale));
        } else {
            if(getParent() != null) {
                width = getParent().getInnerSize().x;
                split = font.split(Component.literal(text), (int) Math.floor(width / scale));
            } else {
                throw new RuntimeException("TextComponent must have a parent to use getPreferredSize()");
            }
        }

        float[] rgb = getColor().getRGBColorComponents(null);

        float red = rgb[0];
        float green = rgb[1];
        float blue = rgb[2];
        int alpha = (int) (animatedOpacity * 255f);

        int combinedColors = (alpha << 24) |
                (((int) (red * 255f)) << 16) |
                (((int) (green * 255f)) << 8) |
                (((int) (blue * 255f)) << 0);

        if (animatedOpacity > 0.105) {
            for (FormattedCharSequence line : split) {
                RenderSystem.enableBlend();
                font.draw(stack, line, 0, 0, combinedColors);
                stack.translate(0, font.lineHeight, 0.0);
            }
        }

        stack.popPose();

        stack.pushPose();
        stack.translate(topLeft.x, -topLeft.y, 0.0);
        stack.pushPose();
        stack.translate(animatedSize.x / 2, -animatedSize.y / 2, 0.0);
        stack.translate(0.0, 0.0, depth() * 0.01);

        // because render quad renders around the origin, we need to translate by the centerpoint - topleft
//        TemporaryWorldVFXBuilderExtension.renderQuad(VFXBuilders.createWorld().setColor(getColor()).setAlpha(animatedOpacity).setPosColorTexLightmapDefaultFormat(), consumer, stack, (float) getSize().x / 2, (float) getSize().y / 2);
        stack.popPose();
        stack.popPose();
        RenderSystem.disableBlend();
    }

    @Override
    public Vector2 getInnerSize() {
        return getSize();
    }
}
