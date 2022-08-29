package team.lodestar.fufo.client.ui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.transition.Transition;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * UI component for a scrollable area
 */
public class ScrollBox extends UIComponent<ScrollBox> {

    public FlexBox content;
    float scrollBarWidth = 1f;
    float scrollBarPadding = 0.2f;
    public ScrollBox() {
        content = new FlexBox();
        content.useAllocatedSpace(true);
        content.withColor(Color.BLUE);
        content.setParent(this);
    }

    @Override
    public Vector2 getPreferredSize() {
        return new Vector2(xConstraint.apply(getParent() != null ? getParent().getInnerSize().x : 0), yConstraint.apply(getParent() != null ? getParent().getInnerSize().y : 0));
    }

    @Override
    public void reform() {
        content.setAllocatedSize(getInnerSize());
    }

    @Override
    public void render(PoseStack stack, double deltaTime) {

        boolean stencil = true;

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
        Vector2 size = animatedSize;

        stack.pushPose();
        stack.translate(topLeft.x, -topLeft.y, 0.0);

        stack.pushPose();
        stack.translate(0.0, 0.0, depth() * 0.01);

        stack.pushPose();
        stack.translate(animatedSize.x / 2, -animatedSize.y / 2, 0.0);

//        TemporaryWorldVFXBuilderExtension.renderQuad(VFXBuilders.createWorld().setColor(getColor()).setAlpha(animatedOpacity).setPosColorTexLightmapDefaultFormat(), consumer, stack, (float) size.x / 2, (float) size.y / 2);
        stack.popPose();


        stack.translate(size.x - scrollBarWidth + scrollBarPadding, -scrollBarPadding, 0);

        float scrollBarHeight = (float) (size.y - scrollBarPadding * 2);
        float scrollBarWidth = this.scrollBarWidth - (scrollBarPadding * 2);
        stack.translate(scrollBarWidth / 2, -scrollBarHeight / 2, 0.01);

        // because render quad renders around the origin, we need to translate by the centerpoint - topleft
        VFXBuilders.createScreen().setColor(Color.WHITE).setAlpha(animatedOpacity).setPosColorTexDefaultFormat();
        stack.popPose();

        // render children
        if(stencil) {
            GL11.glDisable(GL11.GL_STENCIL_TEST);
            RenderSystem.stencilMask(~0);
            RenderSystem.clear(GL11.GL_STENCIL_BUFFER_BIT, Minecraft.ON_OSX);
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            RenderSystem.stencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
            RenderSystem.stencilMask(0xFF);
            RenderSystem.stencilFunc(GL11.GL_NEVER, 1, 0xFF);
        }

        // render stencil
        stack.pushPose();
        stack.translate(topLeft.x, -topLeft.y, 0.0);
        stack.translate(0.0, 0.0, depth() * 0.01);
        stack.translate(animatedSize.x / 2, -animatedSize.y / 2, 0.0);

//       VFXBuilders.createScreen().setColor(new Color(0xff_000000)).setAlpha(1f).setPosColorTexLightmapDefaultFormat(), consumer, stack, (float) size.x / 3, (float) size.y / 3);
        stack.popPose();

        if(stencil) {
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            RenderSystem.stencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
            RenderSystem.stencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        }

        content.render(stack, deltaTime);

        if(stencil) GL11.glDisable(GL11.GL_STENCIL_TEST);

        stack.popPose();
    }

    @Override
    public Vector2 getInnerSize() {
        return getSize().minus(new Vector2(scrollBarWidth, 0));
    }

    /**
     * Adds a child to this box and returns itself.
     * @param child The child to add.
     * @return This box.
     */
    public ScrollBox withChild(UIComponent<?> child) {
        this.content.withChild(child);
        return this;
    }

    /**
     * Removes a child given an index
     * @param index The index of the child to remove.
     * @return This box.
     */
    public ScrollBox removeChild(int index) {
        this.content.removeChild(index);
        return this;
    }
}
