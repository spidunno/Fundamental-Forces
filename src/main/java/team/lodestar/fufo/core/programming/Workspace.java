package team.lodestar.fufo.core.programming;

import team.lodestar.fufo.client.ui.Vector2i;
import team.lodestar.fufo.core.programming.block.StartBlock;

import java.util.*;

public class Workspace {

    private HashMap<Vector2i, ProgrammingBlock> blocks = new HashMap<>();
    private HashMap<UUID, ProgrammingBlock> uuidToBlock = new HashMap<>();

    public void addBlock(ProgrammingBlock block) {
        if(hasBlock(block.position)) {
            removeBlock(block);
        }

        blocks.put(block.position, block);
        uuidToBlock.put(block.uuid, block);
    }

    public void removeBlock(ProgrammingBlock block) {
        blocks.remove(block.position);
        uuidToBlock.remove(block.uuid);
    }


    public boolean hasBlock(Vector2i position) {
        return blocks.containsKey(position);
    }

    public ProgrammingBlock getBlock(Vector2i position) {
        return blocks.get(position);
    }

    /**
     * Moves a block
     */
    public void moveBlock(ProgrammingBlock block, Vector2i newPosition) {
        if (hasBlock(newPosition)) {
            removeBlock(block);
        }

        blocks.put(newPosition, block);
        block.position = newPosition;
    }

    /**
     * Builds a list of independant built scopes
     */
    public List<BuiltScope> buildScopes() {

        // find all start node locations
        HashMap<Vector2i, ProgrammingBlock> startNodes = new HashMap<>();

        for (ProgrammingBlock block : blocks.values()) {
            if (block instanceof StartBlock) {
                startNodes.put(block.position, block);
            }
        }

        // for every start node, build a scope
        List<BuiltScope> scopes = new ArrayList<>();

        for (Map.Entry<Vector2i, ProgrammingBlock> entry : startNodes.entrySet()) {
            // traverse until the sequence ends
            BuiltScope scope = new BuiltScope();

            int offsetX = 1;

            while (hasBlock(entry.getKey().plus(new Vector2i(offsetX, 0)))) {
                ProgrammingBlock block = getBlock(entry.getKey().plus(new Vector2i(offsetX, 0)));

                if(!block.canAttachLeft) break;
                scope.blocks.add(block);
                scope.uuidToBlock.put(block.uuid, block);
                block.setScope(scope);
                offsetX++;

                if(!block.canAttachRight) break;
            }

            scopes.add(scope);
        }

        return scopes;
    }

    /**
     * temp
     */
    public void executeScope(BuiltScope scope) {
        for (ProgrammingBlock block : scope.blocks) {
            block.execute();
        }
    }
}
