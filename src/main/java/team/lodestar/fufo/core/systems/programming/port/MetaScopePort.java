package team.lodestar.fufo.core.systems.programming.port;

import team.lodestar.fufo.core.systems.programming.BlockPort;
import team.lodestar.fufo.core.systems.programming.BuiltScope;

public class MetaScopePort extends BlockPort<BuiltScope> {

    @Override
    public Class<BuiltScope> getType() {return BuiltScope.class; }

}
