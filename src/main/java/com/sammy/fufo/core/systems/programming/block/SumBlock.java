package com.sammy.fufo.core.systems.programming.block;

import com.sammy.fufo.core.systems.programming.BuiltScope;
import com.sammy.fufo.core.systems.programming.ProgrammingBlock;
import com.sammy.fufo.core.systems.programming.ProgrammingBlockEntry;
import com.sammy.fufo.core.systems.programming.port.DoublePort;

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
