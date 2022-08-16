package team.lodestar.fufo.client.ui;

/**
 * Functional interface as a handler for click events.
 */
public interface ClickHandler {

    /**
     * Handles a click
     * @param position the position of the click on this component
     * @param button the mouse button that was clicked
     */
    void onClick(Vector2 position, int button);

}
