package team.lodestar.fufo.core.systems.programming.port;

import team.lodestar.fufo.core.systems.programming.BlockPort;

public class DoublePort extends BlockPort<Double> {

    @Override
    public Class<Double> getType() {return Double.class; }

}
