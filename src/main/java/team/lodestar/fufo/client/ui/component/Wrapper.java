package team.lodestar.fufo.client.ui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.transition.Transition;

import java.util.ArrayList;
import java.util.List;


/**
 * Wrapper UI component, utilizing multiple flexboxes.
 */
public class Wrapper extends UIComponent<Wrapper> implements IParent<Wrapper> {


    /**
     * The amount of padding to add to the flexbox.
     * Padding does not modify the size of this element, rather pads the inside offsetting interior elements.
     * In pixels.
     */
    private Vector2 padding = Vector2.ZERO;

    /**
     * Sets the padding of this wrapper and returns itself.
     * @param padding The padding of this box.
     * @return This box.
     */
    public Wrapper padded(Vector2 padding) {
        this.padding = padding;
        return this;
    }

    /**
     * The children of this flexbox.
     */
    private List<UIComponent<?>> children = new ArrayList<>();


    /**
     * The current direction/axis of the flexbox.
     */
    private FlexBox.Axis axis = FlexBox.Axis.VERTICAL;

    /**
     * Sets the axis of the wrapper.
     * @param axis The axis of the wrapper.
     * @return This wrapper.
     */
    public Wrapper withAxis(FlexBox.Axis axis) {
        this.axis = axis;
        return this;
    }

    /**
     * Spacing between components.
     */
    private float spacing = 0;

    /**
     * Sets the spacing of this wrapper and returns itself.
     * @param spacing The new spacing of this wrapper.
     * @return This box.
     */
    public Wrapper withSpacing(float spacing) {
        this.spacing = spacing;
        return this;
    }

    /**
     * Adds a child to this box and returns itself.
     * @param child The child to add.
     * @return This box.
     */
    @Override
    public Wrapper withChild(UIComponent<?> child) {
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
    public Wrapper removeChild(int index) {
        children.remove(index).setParent(null);
        if(getParent() != null)
            getParent().reform();
        else
            reform();
        return this;
    }

    @Override
    public Wrapper removeChild(UIComponent<?> child) {
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


    @Override
    public Vector2 getPreferredSize() {
        return new Vector2(xConstraint.apply(getParent() != null ? getParent().getInnerSize().x : 0), yConstraint.apply(getParent() != null ? getParent().getInnerSize().y : 0));
    }


    @Override
    public void reform() {
        double availableSizeAlongAxis = axis == FlexBox.Axis.HORIZONTAL ? getInnerSize().x : getInnerSize().y;

        double placementAlongAxis = 0;
        double placementAgainstAxis = 0;
        double maxRowSizeAgainstAxis = 0;

        for (UIComponent<?> child : children) {
            double childSizeAlongAxis = axis == FlexBox.Axis.HORIZONTAL ? child.getPreferredSize().x : child.getPreferredSize().y;
            double childSizeAgainstAxis = axis == FlexBox.Axis.HORIZONTAL ? child.getPreferredSize().y : child.getPreferredSize().x;

            if(childSizeAgainstAxis > maxRowSizeAgainstAxis) {
                maxRowSizeAgainstAxis = childSizeAgainstAxis;
            }

            if (placementAlongAxis + childSizeAlongAxis > availableSizeAlongAxis) {
                placementAlongAxis = 0;
                placementAgainstAxis += maxRowSizeAgainstAxis + spacing;
                maxRowSizeAgainstAxis = 0;
            }

            child.setPosition(new Vector2(
                    axis == FlexBox.Axis.HORIZONTAL ? placementAlongAxis : placementAgainstAxis,
                    axis == FlexBox.Axis.HORIZONTAL ? placementAgainstAxis : placementAlongAxis
            ));

            placementAlongAxis += childSizeAlongAxis + spacing;
        }
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
        Vector2 size = animatedSize;

        stack.pushPose();
        stack.translate(topLeft.x, topLeft.y, 0.0);

        stack.pushPose();
        stack.translate(0.0, 0.0, depth() * 0.01);

        // because render quad renders around the origin, we need to translate by the centerpoint - topleft
        //FXBuilders.createScreen().setShader(GameRenderer::getPositionColorTexShader).setShaderTexture( FufoMod.fufoPath("textures/vfx/white.png")).setPositionWithWidth(0, 0, (float) size.x, (float) size.y).setColor(getColor()).setAlpha(animatedOpacity).setPosColorTexDefaultFormat().draw(stack);
        stack.popPose();

        // render children
        for(UIComponent<?> child : children) {
            child.render(stack, deltaTime);
        }

        stack.popPose();
    }

    @Override
    public Vector2 getInnerSize() {
        return getSize().minus(padding.times(2));
    }

    @Override
    public void jerk() {
        super.jerk();
        for(UIComponent<?> child : children) {
            child.jerk();
        }
    }


}
