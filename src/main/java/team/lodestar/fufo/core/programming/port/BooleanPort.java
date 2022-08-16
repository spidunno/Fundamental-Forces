package team.lodestar.fufo.core.programming.port;

import team.lodestar.fufo.core.programming.BlockPort;

public class BooleanPort extends BlockPort<Boolean> {

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

}
