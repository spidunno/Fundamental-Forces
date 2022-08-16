package team.lodestar.fufo.client.ui;

/**
 * Represents a vector in 2D space.
 * Non-mutable.
 */
public class Vector2i {
    public final int x;
    public final int y;

    /**
     * Constructs a new Vector2 with the given x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the given Vector2 to this Vector2.
     * @param other the Vector2 to add
     * @return the sum of the two Vector2's
     */
    public Vector2i plus(Vector2i other) {
        return new Vector2i(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtracts the given Vector2 from this Vector2.
     * @param other the Vector2 to subtract from this
     * @return the difference of the two Vector2's
     */
    public Vector2i minus(Vector2i other) {
        return new Vector2i(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiplies the given Vector2 by this Vector2.
     * @param other the Vector2 to multiply by this
     * @return the product of the two Vector2's
     */
    public Vector2i times(Vector2i other) {
        return new Vector2i(this.x * other.x, this.y * other.y);
    }

    /**
     * Divides this Vector2 by the given other Vector2.
     * @param other the Vector2 to divide by
     * @return the quotient of the two Vector2's
     */
    public Vector2i div(Vector2i other) {
        return new Vector2i(this.x / other.x, this.y / other.y);
    }

    /**
     * Multiplies this Vector2 by the given scalar.
     * @param scalar the scalar to multiply by
     * @return the product of the scalar and this Vector2
     */
    public Vector2i times(int scalar) {
        return new Vector2i(this.x * scalar, this.y * scalar);
    }

    /**
     * Divides this Vector2 by the given scalar.
     * @param scalar the scalar to divide by
     * @return the quotient of the scalar and this Vector2
     */
    public Vector2i div(int scalar) {
        return new Vector2i(this.x / scalar, this.y / scalar);
    }

    /**
     * Computes the dot product of this vector and the given other vector.
     * @param other the other vector
     * @return the dot product of this vector and the other vector
     */
    public final double dot(Vector2i other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Computes the cross product of this vector and the given other vector.
     * @param other the other vector
     * @return the cross product of this vector and the other vector
     */
    public final double cross(Vector2i other) {
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Computes the distance between this vector and the given other vector
     * @param other the other vector
     * @return the distance between the two vectors
     */
    public final double distanceTo(Vector2i other) {
        double var2 = (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
        return Math.sqrt(var2);
    }

    /**
     * Converts this Vector2 to a String representation
     * @return a String representation of this Vector2
     */
    public String toString() {
        return "Vector2(" + this.x + ", " + this.y + ')';
    }

    /**
     * Checks equality of two Vector2's
     * @param other the Vector2 to compare to
     * @return true if the two Vector2's have the same x and y coordinates
     */
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Vector2i && this.x == ((Vector2i) other).x && this.y == ((Vector2i) other).y;
    }
}
