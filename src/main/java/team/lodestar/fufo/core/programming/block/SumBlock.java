package team.lodestar.fufo.core.programming.block;

import team.lodestar.fufo.core.programming.ProgrammingBlock;
import team.lodestar.fufo.core.programming.ProgrammingBlockEntry;
import team.lodestar.fufo.core.programming.port.DoublePort;

public class SumBlock extends ProgrammingBlock {

    DoublePort a, b, out;

    public SumBlock(ProgrammingBlockEntry<SumBlock> type) {
        super(type);
    }

    @Override
    public void registerPorts() {
        input("a", a = new DoublePort());
        input("b", b = new DoublePort());
        output("sum", out = new DoublePort());
    }

    @Override
    public void execute() {
        set(out, get(a) + get(b));
    }

}
