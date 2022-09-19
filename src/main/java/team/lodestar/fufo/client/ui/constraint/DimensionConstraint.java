package team.lodestar.fufo.client.ui.constraint;

/**
 * Represents a dimension-based constraint, such as 50 pixels or 25% of the parent's width.
 */
public abstract class DimensionConstraint {

    /**
     * Applies this constraint given a parent's dimensions and returns the resulting dimensions.
     * @param parentDimensions the usable dimension of the axis of the parent component
     * @return the resulting dimension on the axis
     */
    public abstract double apply(double parentDimensions);


}
