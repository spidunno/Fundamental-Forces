package team.lodestar.fufo.core.programming.port;

import team.lodestar.fufo.core.programming.BlockPort;

public class DoublePort extends BlockPort<Double> {

    @Override
    public Class<Double> getType() {return Double.class; }

}
