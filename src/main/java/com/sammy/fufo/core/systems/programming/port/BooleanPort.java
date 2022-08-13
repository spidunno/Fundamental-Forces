package com.sammy.fufo.core.systems.programming.port;

import com.sammy.fufo.core.systems.programming.BlockPort;

public class BooleanPort extends BlockPort<Boolean> {

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

}
