package team.lodestar.fufo.core.programming;

/**
 * Port/parameter/output of a programming block
 */
public abstract class BlockPort<T> {

    public abstract Class<T> getType();

    public boolean canConvertFrom(BlockPort<?> other) {
        return false;
    }

}
