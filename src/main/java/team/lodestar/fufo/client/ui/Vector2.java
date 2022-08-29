package team.lodestar.fufo.client.ui;

/**
 * Represents a vector in 2D space.
 * Non-mutable.
 */
public class Vector2 {
    public final double x;
    public final double y;

    /**
     * Vector at the origin, (0, 0).
     */
    public static final Vector2 ZERO = new Vector2(0.0D, 0.0D);

    /**
     * Vector at (1, 1).
     */
    public static final Vector2 ONE = new Vector2(1.0D, 1.0D);

    /**
     * Undefined vector.
     */
    public static final Vector2 NaN = new Vector2(Double.NaN, Double.NaN);

    /**
     * Constructs a new Vector2 with the given x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the given Vector2 to this Vector2.
     * @param other the Vector2 to add
     * @return the sum of the two Vector2's
     */
    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtracts the given Vector2 from this Vector2.
     * @param other the Vector2 to subtract from this
     * @return the difference of the two Vector2's
     */
    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiplies the given Vector2 by this Vector2.
     * @param other the Vector2 to multiply by this
     * @return the product of the two Vector2's
     */
    public Vector2 times(Vector2 other) {
        return new Vector2(this.x * other.x, this.y * other.y);
    }

    /**
     * Divides this Vector2 by the given other Vector2.
     * @param other the Vector2 to divide by
     * @return the quotient of the two Vector2's
     */
    public Vector2 div(Vector2 other) {
        return new Vector2(this.x / other.x, this.y / other.y);
    }

    /**
     * Multiplies this Vector2 by the given scalar.
     * @param scalar the scalar to multiply by
     * @return the product of the scalar and this Vector2
     */
    public Vector2 times(Number scalar) {
        return new Vector2(this.x * scalar.doubleValue(), this.y * scalar.doubleValue());
    }

    /**
     * Divides this Vector2 by the given scalar.
     * @param scalar the scalar to divide by
     * @return the quotient of the scalar and this Vector2
     */
    public Vector2 div(Number scalar) {
        return new Vector2(this.x / scalar.doubleValue(), this.y / scalar.doubleValue());
    }

    /**
     * Computes the dot product of this vector and the given other vector.
     * @param other the other vector
     * @return the dot product of this vector and the other vector
     */
    public final double dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Computes the cross product of this vector and the given other vector.
     * @param other the other vector
     * @return the cross product of this vector and the other vector
     */
    public final double cross(Vector2 other) {
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Computes the distance between this vector and the given other vector
     * @param other the other vector
     * @return the distance between the two vectors
     */
    public final double distanceTo(Vector2 other) {
        double var2 = (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
        return Math.sqrt(var2);
    }

    /**
     * Computes the magnitude, length, or distance from the origin, of this Vector2.
     * @return the magnitude of this Vector2
     */
    public final double getMagnitude() {
        return ZERO.distanceTo(this);
    }

    /**
     * Returns a normalized version of vector to a magnitude of 1, or a unit vector.
     * @return the normalized vector
     */
    public final Vector2 getNormalized() {
        return this.equals(ZERO) ? ZERO : this.div((Number) this.getMagnitude());
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
        return other != null && other instanceof Vector2 && this.x == ((Vector2) other).x && this.y == ((Vector2) other).y;
    }
}
