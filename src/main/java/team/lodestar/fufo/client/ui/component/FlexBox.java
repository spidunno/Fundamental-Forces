package team.lodestar.fufo.client.ui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.client.ui.ClickHandler;
import team.lodestar.fufo.client.ui.RectTransform;
import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.transition.Transition;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Building block for most UI layouts, a FlexBox is a container that can
 * contain other components and rearrange them in a variety of different ways.
 */
public class FlexBox extends UIComponent<FlexBox> implements IParent<FlexBox> {

    private static final ResourceLocation WHITE_PIXEL = FufoMod.fufoPath("textures/vfx/white.png");
    private static final RenderType WHITE_SQUARE_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(WHITE_PIXEL);

    /**
     * The children of this flexbox.
     */
    private List<UIComponent<?>> children = new ArrayList<>();

    /**
     * Whether or not to stencil the contents of this flexbox.
     */
    private boolean stencil = false;

    /**
     * Spacing between components.
     */
    private float spacing = 0;

    /**
     * The current direction/axis of the flexbox.
     */
    private Axis axis = Axis.VERTICAL;

    /**
     * The current alignment of the flexbox.
     */
    private FlexBox.Alignment alignmentAlongAxis = Alignment.START;

    /**
     * The current alignment of the flexbox.
     */
    private FlexBox.Alignment alignmentAgainstAxis = Alignment.START;

    /**
     * Sets the axis of the flexbox.
     * @param axis The axis of the flexbox.
     * @return This flexbox.
     */
    public FlexBox withAxis(Axis axis) {
        this.axis = axis;
        return this;
    }

    /**
     * Sets the alignment of the flexbox along the axis.
     * @param alignment The alignment of the flexbox along the axis.
     * @return This flexbox.
     */
    public FlexBox withAlignmentAlongAxis(Alignment alignment) {
        this.alignmentAlongAxis = alignment;
        return this;
    }

    /**
     * Sets the alignment of the flexbox against the axis.
     * @param alignment The alignment of the flexbox against the axis.
     * @return This flexbox.
     */
    public FlexBox withAlignmentAgainstAxis(Alignment alignment) {
        this.alignmentAgainstAxis = alignment;
        return this;
    }

    /**
     * The amount of padding to add to the flexbox.
     * Padding does not modify the size of this element, rather pads the inside offsetting interior elements.
     * In pixels.
     */
    private Vector2 padding = Vector2.ZERO;

    @Override
    public Vector2 getPreferredSize() {
        return new Vector2(xConstraint.apply(getParent() != null ? getParent().getInnerSize().x : 0), yConstraint.apply(getParent() != null ? getParent().getInnerSize().y : 0));
    }

    /**
     * Current shader texture.
     */
    private ResourceLocation shaderTexture = WHITE_PIXEL;

    /**
     * Sets the shader texture.
     */
    public FlexBox withShaderTexture(ResourceLocation shaderTexture) {
        this.shaderTexture = shaderTexture;
        return this;
    }

    /**
     * The current click handler
     */
    private ClickHandler clickHandler = null;

    /**
     * Sets the click handler.
     */
    public FlexBox withClickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    /**
     * The current UV
     */
    public RectTransform uv = new RectTransform(0, 0, 1, 1);

    /**
     * Sets the UV.
     */
    public FlexBox withUV(RectTransform uv) {
        this.uv = uv;
        return this;
    }


    @Override
    public void reform() {
        List<UIComponent<?>> children = this.children.stream().filter((x) -> !x.isAbsolutePositioned()).toList();

        Vector2 size = getSize();

        // account for padding
        size = size.minus(padding.times(2));

        double availableSpaceAlongAxis = axis == Axis.HORIZONTAL ? size.x : size.y;

        // account for spacing
        availableSpaceAlongAxis -= spacing * (children.size() - 1);

        // first pass: get the available space we have to put each child
        double totalFlex = 0;
        for(UIComponent<?> child : children) {
            double childFlex = child.getFlex();
            if(childFlex == 0) {
                availableSpaceAlongAxis -= axis == Axis.HORIZONTAL ? child.getPreferredSize().x : child.getPreferredSize().y;
            } else {
                totalFlex += childFlex;
            }
        }

        // second pass: distribute the available space
        double placementAlongAxis = axis == Axis.HORIZONTAL ? padding.x : padding.y;

        double availableSpaceAgainstAxis = axis == Axis.HORIZONTAL ? getInnerSize().y : getInnerSize().x;

        for(UIComponent<?> child : children) {
            double childFlex = child.getFlex();
            if(childFlex != 0) {
                double childSize = childFlex / totalFlex * availableSpaceAlongAxis;
                child.useAllocatedSpace(true);
                child.setAllocatedSize(new Vector2(
                        axis == Axis.HORIZONTAL ? childSize : child.getPreferredSize().x,
                        axis == Axis.HORIZONTAL ? child.getPreferredSize().y : childSize
                ));
            }

            // get child size along axis
            double childSizeAlongAxis = axis == Axis.HORIZONTAL ? child.getSize().x : child.getSize().y;

            // get child size against axis
            double childSizeAgainstAxis = axis == Axis.HORIZONTAL ? child.getSize().y : child.getSize().x;

            // before we set child position, we need to account for alignment
            child.setPosition(new Vector2(
                    axis == Axis.HORIZONTAL ? placementAlongAxis : 0,
                    axis == Axis.HORIZONTAL ? 0 : placementAlongAxis
            ).plus(new Vector2(0, padding.y)));

            if(axis == Axis.VERTICAL) {
                child.setPosition(child.getPosition().plus(new Vector2(padding.x, -padding.y)));
            }


            placementAlongAxis +=  childSizeAlongAxis + spacing;
        }

        // third pass: apply alignment


        if (alignmentAlongAxis == Alignment.START) {
            // do nothing
        }

        for (Axis axis : Axis.values()) {
            Alignment alignment = this.axis == axis ? this.alignmentAlongAxis : this.alignmentAgainstAxis;
            if (alignment == Alignment.CENTER) {
                // we need to get the center position of all the children, which can be done by taking the average of the two extremes

                if (children.size() > 0) {
                    double start = axis == Axis.HORIZONTAL ? children.get(0).getPosition().y : children.get(0).getPosition().x;
                    UIComponent<?> last = children.get(children.size() - 1);
                    double lastSizeAgainstAxis = axis == Axis.HORIZONTAL ? last.getSize().y : last.getSize().x;
                    double end = axis == Axis.HORIZONTAL ? last.getPosition().y + lastSizeAgainstAxis : last.getPosition().x + lastSizeAgainstAxis;

                    double center = (start + end) / 2;
                    double desiredCenter = axis == Axis.HORIZONTAL ? getSize().y / 2 : getSize().x / 2;

                    for (UIComponent<?> child : children) {
                        child.setPosition(child.getPosition().plus(new Vector2(
                                axis == Axis.HORIZONTAL ? 0 : desiredCenter - center,
                                axis == Axis.HORIZONTAL ? desiredCenter - center : 0
                        )));

                        if(axis == this.axis) {
                            Vector2 childCenter = child.getPosition().plus(child.getSize().div(2));
                            child.setPosition(child.getPosition().plus(new Vector2(
                                    axis == Axis.HORIZONTAL ? 0 : desiredCenter - childCenter.x,
                                    axis == Axis.HORIZONTAL ? desiredCenter - childCenter.y : 0
                            )));
                        }

//                        axis == Axis.HORIZONTAL ? getSize().y / 2 : getSize().x / 2;
                    }
                }
            }

            if(axis == this.axis) {
                // main axis
                if (alignment == Alignment.END) {
                    for (UIComponent<?> child : children) {

                        Vector2 childEnd = child.getPosition().plus(child.getSize());

                        child.setPosition(child.getPosition().plus(new Vector2(
                                axis == Axis.HORIZONTAL ? 0 : getInnerSize().x - childEnd.x,
                                axis == Axis.HORIZONTAL ? getInnerSize().y - childEnd.y : 0
                        )));
                    }
                }
            } else {
                // cross axis
                // find furthest point on child in the cross axis
                if (alignment == Alignment.END) {
                    double furthestPoint = 0;

                    for (UIComponent<?> child : children) {
                        double childEnd = axis == Axis.HORIZONTAL ? child.getPosition().y + child.getSize().y : child.getPosition().x + child.getSize().x;
                        if (childEnd > furthestPoint) {
                            furthestPoint = childEnd;
                        }
                    }

                    for (UIComponent<?> child : children) {
                        child.setPosition(child.getPosition().plus(new Vector2(
                                axis == Axis.HORIZONTAL ? 0 : getInnerSize().x - furthestPoint,
                                axis == Axis.HORIZONTAL ? getInnerSize().y - furthestPoint : 0
                        )));
                    }
                }
            }
        }

        // third pass: tell children to reform
        for(UIComponent<?> child : children) {
            child.reform();
        }
    }

    public enum Axis {
        HORIZONTAL, VERTICAL
    }

    public enum Alignment {
        START, CENTER, END
    }

    /**
     * Renders this box, given a parent box.
     */
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
        Vector2 size = animatedSize;

        stack.pushPose();
        stack.translate(topLeft.x, topLeft.y, 0.0);

        stack.pushPose();
//        stack.translate(animatedSize.x / 2, animatedSize.y / 2, 0.0);
        stack.translate(0.0, 0.0, depth() * 0.01);

        // because render quad renders around the origin, we need to translate by the centerpoint - topleft
        RenderSystem.enableBlend();
        VFXBuilders.createScreen().setShader(GameRenderer::getPositionColorTexShader).setShaderTexture(shaderTexture).setPositionWithWidth(0, 0, (float) size.x, (float) size.y).setUV((float) uv.topLeft().x, (float) uv.topLeft().y, (float) uv.bottomRight().x, (float) uv.bottomRight().y).setColor(getColor()).setAlpha(animatedOpacity).setPosColorTexDefaultFormat().draw(stack);
        RenderSystem.disableBlend();

        stencil = (children.size() > 0) && (children.get(0) instanceof IParent<?>) && ((IParent<?>)children.get(0)).getChildren().size() > 15;
        // render children
        if(stencil) {
            GL11.glDisable(GL11.GL_STENCIL_TEST);
//            RenderSystem.stencilMask(~0);
//            RenderSystem.clear(GL11.GL_STENCIL_BUFFER_BIT, Minecraft.ON_OSX);
//            GL11.glEnable(GL11.GL_STENCIL_TEST);
//            RenderSystem.stencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
//            RenderSystem.stencilMask(0xFF);
//            RenderSystem.stencilFunc(GL11.GL_NEVER, 1, 0xFF);
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            RenderSystem.clearStencil(0);
            RenderSystem.stencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
            RenderSystem.stencilFunc(GL11.GL_NEVER, 1, 0xFF);
            RenderSystem.stencilMask(0xFF);

            VFXBuilders.createScreen().setShader(GameRenderer::getPositionColorTexShader).setShaderTexture(WHITE_PIXEL).setColor(Color.BLACK).setPositionWithWidth(0, 0, (float) size.x, (float) size.y).setUV((float) uv.topLeft().x, (float) uv.topLeft().y, (float) uv.bottomRight().x, (float) uv.bottomRight().y).setColor(getColor()).setAlpha(1.0f).setPosColorTexDefaultFormat().draw(stack);

        }

        stack.popPose();

        Minecraft client = Minecraft.getInstance();
        double scale = client.getWindow().getGuiScale();

//        RectTransform bounds = new RectTransform(getPosition(), size);

//        Vector2 bottomRight = bounds.bottomRight();
//        Vector2 bottomRightScreenSpace = getScreenPosition(bottomRight);



        if(stencil) {
            GL11.glEnable(GL11.GL_STENCIL_TEST);
//            RenderSystem.stencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
//            RenderSystem.stencilFunc(GL11.GL_EQUAL, 1, 0xFF);
            RenderSystem.stencilMask(0x00);
            RenderSystem.stencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        }

        // render children
        for(UIComponent<?> child : children) {
            child.render(stack, deltaTime);
        }

        if(stencil) GL11.glDisable(GL11.GL_STENCIL_TEST);


        stack.popPose();
    }

    @Override
    public Vector2 getInnerSize() {
        return getSize().minus(padding.times(2));
    }


    /**
     * Sets the spacing of this flexbox and returns itself.
     * @param spacing The new spacing of this flexbox.
     * @return This box.
     */
    public FlexBox withSpacing(float spacing) {
        this.spacing = spacing;
        return this;
    }

    /**
     * Adds a child to this box and returns itself.
     * @param child The child to add.
     * @return This box.
     */
    @Override
    public FlexBox withChild(UIComponent<?> child) {
        children.add(child);
        child.setParent(this);
        if(child.getParent() != null)
            child.getParent().reform();
        else
            child.reform();

        return this;
    }

    /**
     * Removes a child given an index
     * @param index The index of the child to remove.
     * @return This box.
     */
    @Override
    public FlexBox removeChild(int index) {
        children.remove(index).setParent(null);
        if(getParent() != null)
            getParent().reform();
        else
            reform();
        return this;
    }

    @Override
    public FlexBox removeChild(UIComponent<?> child) {
        children.remove(child);
        child.setParent(null);
        if(getParent() != null)
            getParent().reform();
        else
            reform();
        return this;
    }


    @Override
    public List<UIComponent<?>> getChildren() {
        return children;
    }

    /**
     * Sets the padding of this box and returns itself.
     * @param padding The padding of this box.
     * @return This box.
     */
    public FlexBox padded(Vector2 padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public boolean handleClick(Vector2 position, int button) {
        if(new RectTransform(getPosition(), getSize()).contains(position)) {

            // children onclick need to be handled before us
            for(UIComponent<?> child : children) {
                if(child.handleClick(position.minus(getPosition()), button)) {
                    return true;
                }
            }

            if(clickHandler != null) {
                clickHandler.onClick(position.minus(getPosition()), button);
            }


        }

        return false;
    }

    @Override
    public void jerk() {
        super.jerk();
        for(UIComponent<?> child : children) {
            child.jerk();
        }
    }

}
