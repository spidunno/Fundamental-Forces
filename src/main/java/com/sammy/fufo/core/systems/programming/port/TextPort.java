package com.sammy.fufo.core.systems.programming.port;

import com.sammy.fufo.core.systems.programming.BlockPort;

public class TextPort extends BlockPort<String> {

    @Override
    public Class<String> getType() {return String.class; }

}
