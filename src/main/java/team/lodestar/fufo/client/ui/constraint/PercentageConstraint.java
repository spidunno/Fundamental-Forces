package team.lodestar.fufo.client.ui.constraint;

/**
 * A simple {@link DimensionConstraint} that specifies a percentage of the parent dimension.
 */
public class PercentageConstraint extends DimensionConstraint {

    double percentage = 0;

    /**
     * Constructs a new {@link PercentageConstraint} with the specified percentage.
     * @param percentage the percentage of the parent dimension, .
     */
    public PercentageConstraint(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double apply(double parentDimensions) {
        return parentDimensions * percentage;
    }

}
