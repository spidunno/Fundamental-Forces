package team.lodestar.fufo.client.ui;

/**
 * Represents a rectangle in 2D space.
 */
public class RectTransform {

    /**
     * The top left corner of the rectangle.
     */
    private Vector2 position;

    /**
     * The size of the rectangle.
     */
    private Vector2 size;

    /**
     * Obtains the top left corner of the rectangle.
     */
    public Vector2 topLeft() {
        return position;
    }

    /**
     * Obtains the bottom right corner of the rectangle.
     */
    public Vector2 bottomRight() {
        return new Vector2(position.x + size.x, position.y + size.y);
    }

    /**
     * Obtains the top right corner of the rectangle.
     */
    public Vector2 topRight() {
        return new Vector2(position.x + size.x, position.y);
    }

    /**
     * Obtains the bottom left corner of the rectangle.
     */
    public Vector2 bottomLeft() {
        return new Vector2(position.x, position.y + size.y);
    }

    /**
     * Obtains the size of the rectangle.
     */
    public Vector2 size() {
        return size;
    }

    /**
     * Obtains the width of the rectangle.
     */
    public double width() {
        return size.x;
    }

    /**
     * Obtains the height of the rectangle.
     */
    public double height() {
        return size.y;
    }

    /**
     * Obtains the center of the rectangle.
     */
    public Vector2 center() {
        return new Vector2(position.x + size.x / 2, position.y + size.y / 2);
    }

    /**
     * Checks if this RectTransform contains the given point.
     */
    public boolean contains(Vector2 point) {
        return point.x >= position.x && point.x <= position.x + size.x &&
               point.y >= position.y && point.y <= position.y + size.y;
    }

    /**
     * Checks if this RectTransform contains the given RectTransform.
     */
    public boolean contains(RectTransform rect) {
        return contains(rect.topLeft()) && contains(rect.bottomRight());
    }

    /**
     * Checks if this RectTransform intersects the given RectTransform.
     */
    public boolean intersects(RectTransform rect) {
        return contains(rect.topLeft()) || contains(rect.topRight()) ||
               contains(rect.bottomLeft()) || contains(rect.bottomRight());
    }

    /**
     * Creates a new RectTransform.
     */
    public RectTransform(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    /**
     * Creates a new RectTransform.
     */
    public RectTransform(double x, double y, double width, double height) {
        this(new Vector2(x, y), new Vector2(width, height));
    }

    /**
     * Shrinks this RectTransform by the given amount.
     * @param x The amount to shrink the x-axis.
     * @param y The amount to shrink the y-axis.
     * @return The new RectTransform.
     */
    public RectTransform shrink(double x, double y) {
        return new RectTransform(position.x + x, position.y + y, size.x - 2 * x, size.y - 2 * y);
    }
}
