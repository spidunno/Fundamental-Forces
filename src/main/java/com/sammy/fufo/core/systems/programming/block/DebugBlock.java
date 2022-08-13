package com.sammy.fufo.core.systems.programming.block;

import com.sammy.fufo.core.systems.programming.BuiltScope;
import com.sammy.fufo.core.systems.programming.ProgrammingBlock;
import com.sammy.fufo.core.systems.programming.ProgrammingBlockEntry;
import com.sammy.fufo.core.systems.programming.port.DoublePort;
import com.sammy.fufo.core.systems.programming.port.TextPort;

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
