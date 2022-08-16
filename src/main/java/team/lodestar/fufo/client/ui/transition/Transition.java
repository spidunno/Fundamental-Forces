package team.lodestar.fufo.client.ui.transition;

import team.lodestar.fufo.client.ui.component.UIComponent;

/**
 * Abstract transition for UI elements.
 */
public abstract class Transition {

    /**
     * The type of this transition.
     */
    private Transition.Type type;

    /**
     * Applies this transition to the given element.
     */
    public abstract void apply(UIComponent<?> element, double deltaTime);

    /**
     * "Jerks" this transition, i.e. the transition will be applied immediately.
     */
    public abstract void jerk(UIComponent<?> element);

    /**
     * Returns the type of this transition.
     * @return the type of this transition
     */
    public abstract Type getType();

    public static enum Type {
        /**
         * Opacity/transparency transition.
         */
        OPACITY,
        /**
         * Position transition.
         */
        POSITION,
        /**
         * Size transition.
         */
        SIZE
    }
}
