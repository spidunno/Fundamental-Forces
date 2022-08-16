package team.lodestar.fufo.core.systems.programming.port;

import team.lodestar.fufo.core.systems.programming.BlockPort;
import team.lodestar.fufo.core.systems.programming.ProgrammingBlock;

public class MetaPort extends BlockPort<ProgrammingBlock> {

    @Override
    public Class<ProgrammingBlock> getType() {return ProgrammingBlock.class; }

}
