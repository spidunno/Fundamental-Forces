package team.lodestar.fufo.core.programming;

import team.lodestar.fufo.client.ui.Vector2i;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Abstract block for the Fundamental Forces programming system
 */
public abstract class ProgrammingBlock {

    /**
     * The inputs, or parameters of this programming block.
     * These can technically be dynamic, but the system is not designed around it
     */
    public Map<String, BlockPort<?>> inputs = new LinkedHashMap<>();

    /**
     * The outputs of this programming block.
     * These can technically be dynamic, but the system is not designed around it
     */
    public Map<String, BlockPort<?>> outputs = new LinkedHashMap<>();

    /**
     * Output results
     */
    public Map<BlockPort<?>, Object> results = new LinkedHashMap<>();

    /**
     * Port dependencies for this block
     */
    public Map<BlockPort<?>, PortDependency> dependencies = new LinkedHashMap<>();

    /**
     * Resource location of this programming block
     */
    public ResourceLocation typeID;

    /**
     * Type of this programming block
     */
    public ProgrammingBlockEntry<?> type;

    /**
     * UUID of this node
     */
    public UUID uuid;

    /**
     * Position, x/y coordinates of this blocks leftmost piece
     */
    public Vector2i position;

    /**
     * Width in grid spaces this block occupies
     */
    public int width = 1;

    /**
     * Can this block attach as part of a scope on the left
     */
    public boolean canAttachLeft = true;

    /**
     * Can this block attach as part of a scope on the right
     */
    public boolean canAttachRight = true;

    /**
     * Constructs a new programming block
     */
    public ProgrammingBlock(ProgrammingBlockEntry type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
        this.typeID = FufoProgramming.INVERSE_PROGRAMMING_BLOCK_REGISTRY.get(type);
        registerPorts();
    }

    /**
     * Registers a new input
     * Recommended to be used in the syntax input(myParam = new ...) so that the param is not lost
     */
    public <T> void input(String name, BlockPort<T> port) {
        inputs.put(name, port);
    }

    /**
     * Registers a new output
     * Recommended to be used in the syntax output(myOutput = new ...) so that the param is not lost
     */
    public <T> void output(String name, BlockPort<T> port) {
        outputs.put(name, port);
    }

    /**
     * Current scope this block is a part of
     */
    private BuiltScope scope;

    /**
     * Sets the current scope of this block
     */
    public void setScope(BuiltScope scope) {
        this.scope = scope;
    }

    /**
     * Obtains the value of a parameter through a scope
     */
    public <T> T get(BlockPort<T> port) {

        return (T) scope.getOutput(dependencies.get(inputs.get(port)));
    }

    /**
     * Sets the result of an output
     */
    public void set(BlockPort<?> port, Object value) {
        results.put(port, value);
    }

    /**
     * Register all inputs and outputs of this programming block
     */
    public abstract void registerPorts();

    /**
     * Called when this block is executed
     * All computation should occur here and outputs should be set
     */
    public abstract void execute();



}
