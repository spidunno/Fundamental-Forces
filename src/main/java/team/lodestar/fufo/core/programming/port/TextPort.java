package team.lodestar.fufo.core.programming.port;

import team.lodestar.fufo.core.programming.BlockPort;

public class TextPort extends BlockPort<String> {

    @Override
    public Class<String> getType() {return String.class; }

}
