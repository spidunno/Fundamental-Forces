package com.sammy.fufo.core.systems.programming.port;

import com.sammy.fufo.core.systems.programming.BlockPort;

public class DoublePort extends BlockPort<Double> {

    @Override
    public Class<Double> getType() {return Double.class; }

}
