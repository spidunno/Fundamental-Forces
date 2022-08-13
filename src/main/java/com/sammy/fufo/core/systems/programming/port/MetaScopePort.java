package com.sammy.fufo.core.systems.programming.port;

import com.sammy.fufo.core.systems.programming.BlockPort;
import com.sammy.fufo.core.systems.programming.BuiltScope;
import com.sammy.fufo.core.systems.programming.ProgrammingBlock;

public class MetaScopePort extends BlockPort<BuiltScope> {

    @Override
    public Class<BuiltScope> getType() {return BuiltScope.class; }

}
