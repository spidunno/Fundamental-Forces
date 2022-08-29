package team.lodestar.fufo.core.programming.port;

import team.lodestar.fufo.core.programming.BlockPort;
import team.lodestar.fufo.core.programming.ProgrammingBlock;

public class MetaPort extends BlockPort<ProgrammingBlock> {

    @Override
    public Class<ProgrammingBlock> getType() {return ProgrammingBlock.class; }

}
