package team.lodestar.fufo.client.ui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.constraint.DimensionConstraint;
import team.lodestar.fufo.client.ui.constraint.PixelConstraint;
import team.lodestar.fufo.client.ui.transition.Transition;

import java.awt.*;
import java.util.HashMap;

/**
 * Abstract class for all UI components.
 */
public abstract class UIComponent<T extends UIComponent> {

    public DimensionConstraint xConstraint = new PixelConstraint(0);
    public DimensionConstraint yConstraint = new PixelConstraint(0);

    /**
     * The current parent of this component.
     */
    private UIComponent<?> parent = null;

    /**
     * Obtains the current parent of this box.
     */
    public UIComponent<?> getParent() {
        return parent;
    }

    /**
     * Sets the current parent of this box.
     */
    public void setParent(UIComponent<?> parent) {
        if(this.parent != null && parent != null) {
            throw new RuntimeException("UI Component attempted to be set to a new parent, but already had a parent. This can cause quantum entanglement!");
        }
        this.parent = parent;
    }

    /**
     * Obtains the "depth" of this box, or how many parents it is contained in.
     * If this box has no parents, it is at depth 0.
     */
    public int depth() {
        return parent != null ? parent.depth() + 1 : 0;
    }

    /**
     * The flex of this component.
     */
    private double flex = 0;

    /**
     * Obtains the flex of this component.
     */
    public double getFlex() {
        return flex;
    }


    /**
     * All current transitions of this box.
     */
    public HashMap<Transition.Type, Transition> transitions = new HashMap<>();

    /**
     * The current alpha of this box.
     */
    private float opacity = 1.0f;

    /**
     * The current animated opacity of this box.
     */
    public float animatedOpacity = 1f;

    /**
     * The current animated size of this box.
     */
    public Vector2 animatedSize = new Vector2(0, 0);

    /**
     * The current animated position of this box.
     */
    public Vector2 animatedPosition = new Vector2(0, 0);

    /**
     * Obtains the current opacity of this box.
     */
    public float getOpacity() {
        return opacity;
    }

    /**
     * The current color of this box.
     */
    private Color color = Color.WHITE;

    /**
     * Gets the current color of this box.
     */
    public Color getColor() {
        return color;
    }

    /**
     * If the parent's overriden size should be used, or ignored
     */
    private boolean useAllocatedSpace = false;

    /**
     * Tells this component whether the parent's overridden size should be used, or if it should be ignored.
     */
    public void useAllocatedSpace(boolean use) {
        this.useAllocatedSpace = use;
    }

    /**
     * Gets whether this component should use the parent's overridden size, or ignore it.
     */
    public boolean shouldUseAllocatedSpace() {
        return useAllocatedSpace;
    }

    /**
     * Allocated size of this component.
     */
    private Vector2 allocatedSize = new Vector2(0, 0);

    /**
     * Sets the allocated size of this component.
     */
    public void setAllocatedSize(Vector2 size) {
        allocatedSize = size;
    }

    /**
     * Gets the allocated size of this component.
     */
    public Vector2 getAllocatedSize() {
        return allocatedSize;
    }

    /**
     * The location of this component.
     * This is relative to the parent's top left corner of their outer rect transform.
     */
    private Vector2 position = new Vector2(0, 0);

    /**
     * Gets the location of this component.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the location of this component.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * If this component is absolute positioned.
     * Absolutely positioned components are not affected by the layout of the parent.
     */
    private boolean absolutePositioned = false;

    /**
     * Gets whether this component is absolute positioned.
     */
    public boolean isAbsolutePositioned() {
        return absolutePositioned;
    }

    /**
     * Sets whether this component is absolute positioned.
     */
    public void setAbsolutePositioned(boolean absolutePositioned) {
        this.absolutePositioned = absolutePositioned;
    }

    /**
     * Gets the size of this component.
     */
    public Vector2 getSize() {
        if (parent != null && shouldUseAllocatedSpace()) {
            return allocatedSize;
        } else {
            return getPreferredSize();
        }
    }

    /**
     * Gets the preferred size of this component.
     */
    public abstract Vector2 getPreferredSize();

    /**
     * Updates this component.
     */
    public abstract void reform();

    /**
     * Sets the width constraint of this box and returns itself.
     * @param constraint The width constraint of this box.
     * @return This box.
     */
    public T withWidth(DimensionConstraint constraint) {
        this.xConstraint = constraint;
        return (T) this;
    }

    /**
     * Sets the height constraint of this box and returns itself.
     * @param constraint The height constraint of this box.
     * @return This box.
     */
    public T withHeight(DimensionConstraint constraint) {
        this.yConstraint = constraint;
        return (T) this;
    }

    /**
     * Sets the color of this box and returns itself.
     * @param color The color of this box.
     * @return This box.
     */
    public T withColor(Color color) {
        this.color = color;
        return (T) this;
    }

    /**
     * Sets the opacity of this box and returns itself.
     * @param opacity The opacity of this box.
     * @return This box.
     */
    public T withOpacity(float opacity) {
        this.opacity = opacity;
        return (T) this;
    }

    /**
     * Sets the flex of this box and returns itself.
     * @param flex The flex of this box.
     * @return This box.
     */
    public T withFlex(double flex) {
        this.flex = flex;
        return (T) this;
    }

    /**
     * Adds a transition to this box and returns itself.
     * @param transition The transition to add.
     * @return This box.
     */
    public T withTransition(Transition transition) {
        transitions.put(transition.getType(), transition);
        return (T) this;
    }

    /**
     * Renders this UI component.
     */
    public abstract void render(PoseStack stack, double deltaTime);

    /**
     * Obtains the usable inner size of this component.
     * @return The usable inner size of this component.
     */
    public abstract Vector2 getInnerSize();

    /**
     * Handles a click at the given position relative to the parent component.
     * @param position The position relative to the parent component.
     * @param button The button that was clicked.
     * @return Whether the click was handled.
     */
    public boolean handleClick(Vector2 position, int button) {
        return false;
    }

    /**
     * Jerks this component, automatically jerking all transitions
     */
    public void jerk() {
        for (Transition transition : transitions.values()) {
            transition.jerk(this);
        }
    }

    /**
     * Moves this child to a new parent.
     * @param parent The new parent.
     */
    public void moveTo(UIComponent parent) {
        if(this.getParent() != null) {
            ((IParent) this.getParent()).removeChild(this);
        }

        if(parent != null) {
            ((IParent) parent).withChild(this);
        }
    }

    /**
     * Gets the screen-space position of a position relative to this component.
     */
    public Vector2 getScreenPosition(Vector2 position) {
        return parent.getScreenPosition(position.plus(getPosition()));
    }
}
