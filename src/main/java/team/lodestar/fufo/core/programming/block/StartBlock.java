package team.lodestar.fufo.core.programming.block;

import team.lodestar.fufo.core.programming.ProgrammingBlock;
import team.lodestar.fufo.core.programming.ProgrammingBlockEntry;

public class StartBlock extends ProgrammingBlock {

    public StartBlock(ProgrammingBlockEntry<StartBlock> type) {
        super(type);
        canAttachLeft = false;
    }

    @Override
    public void registerPorts() {
        // noop
    }

    @Override
    public void execute() {
        // noop
    }

}
