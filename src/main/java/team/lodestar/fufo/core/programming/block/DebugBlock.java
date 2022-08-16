package team.lodestar.fufo.core.programming.block;

import team.lodestar.fufo.core.programming.ProgrammingBlock;
import team.lodestar.fufo.core.programming.ProgrammingBlockEntry;
import team.lodestar.fufo.core.programming.port.TextPort;

public class DebugBlock extends ProgrammingBlock {

    TextPort identifier;
    TextPort message;

    public DebugBlock(ProgrammingBlockEntry<DebugBlock> type) {
        super(type);
    }

    @Override
    public void registerPorts() {
        input("identifier", identifier = new TextPort());
        input("message", message = new TextPort());
    }

    @Override
    public void execute() {
        System.out.println("Debug block says [" + get(identifier) + "] " + get(message));
    }

}
