package team.lodestar.fufo.core.programming.port;

import team.lodestar.fufo.core.programming.BlockPort;
import team.lodestar.fufo.core.programming.BuiltScope;

public class MetaScopePort extends BlockPort<BuiltScope> {

    @Override
    public Class<BuiltScope> getType() {return BuiltScope.class; }

}
