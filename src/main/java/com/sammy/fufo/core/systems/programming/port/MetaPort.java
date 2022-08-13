package com.sammy.fufo.core.systems.programming.port;

import com.sammy.fufo.core.systems.programming.BlockPort;
import com.sammy.fufo.core.systems.programming.ProgrammingBlock;

public class MetaPort extends BlockPort<ProgrammingBlock> {

    @Override
    public Class<ProgrammingBlock> getType() {return ProgrammingBlock.class; }

}
