package team.lodestar.fufo.core.programming;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * A scope- or container for programming blocks
 */
public class BuiltScope {
    public BuiltScope parent = null;
    public HashMap<UUID, ProgrammingBlock> uuidToBlock = new HashMap<>();
    public List<ProgrammingBlock> blocks;

    public Object getOutput(PortDependency dependency) {
        ProgrammingBlock block = uuidToBlock.get(dependency.portId);

        if(block != null) {
            return block.results.get(block.outputs.get(dependency.portName));
        } else {
            if(parent != null) return parent.getOutput(dependency);
            else return null;
        }
    }
}
