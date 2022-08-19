package team.lodestar.fufo.client.ui.component;

import java.util.List;

/**
 * Interface for the components that can have children.
 */
public interface IParent<T> {

    /**
     * Adds a child to this parent and returns itself.
     * @param child The child to add.
     * @return This box.
     */
    T withChild(UIComponent<?> child);

    /**
     * Removes a child given an index
     * @param index The index of the child to remove.
     * @return This box.
     */
    T removeChild(int index);

    /**
     * Removes a child given the child
     * @param child The child to remove.
     * @return This box.
     */
    T removeChild(UIComponent<?> child);

    /**
     * Gets all the children of this parent.
     * @return The children.
     */
    List<UIComponent<?>> getChildren();

}
