package team.lodestar.fufo.core.systems.programming.port;

import team.lodestar.fufo.core.systems.programming.BlockPort;

public class BooleanPort extends BlockPort<Boolean> {

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

}
