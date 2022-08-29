package team.lodestar.fufo.client.ui.constraint;

/**
 * A simple {@link DimensionConstraint} that specifies a pixel value.
 */
public class PixelConstraint extends DimensionConstraint {

    double pixels = 0;

    public PixelConstraint(double pixels) {
        this.pixels = pixels;
    }

    @Override
    public double apply(double parentDimensions) {
        return pixels;
    }

}
