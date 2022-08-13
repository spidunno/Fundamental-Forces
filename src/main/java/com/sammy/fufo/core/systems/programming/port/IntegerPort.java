package com.sammy.fufo.core.systems.programming.port;

import com.sammy.fufo.core.systems.programming.BlockPort;

public class IntegerPort extends BlockPort<Integer> {

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

}
