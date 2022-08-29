package team.lodestar.fufo.core.programming;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.programming.block.DebugBlock;
import team.lodestar.fufo.core.programming.block.StartBlock;
import team.lodestar.fufo.core.programming.block.SumBlock;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FufoProgramming {

    // TODO: This really should be a registry, fix this sometime
    public static Map<ResourceLocation, ProgrammingBlockEntry<?>> PROGRAMMING_BLOCK_REGISTRY = new HashMap<>();
    public static Map<ProgrammingBlockEntry<?>, ResourceLocation> INVERSE_PROGRAMMING_BLOCK_REGISTRY = new HashMap<>();

    /**
     * Registers a new programming block
     * @param id Resource location/id of this block
     * @param supplier Supplies a new instance of this block given the entry
     */
    public static <T extends ProgrammingBlock> ProgrammingBlockEntry<T> registerBlock(ResourceLocation id, ResourceLocation texture, Function<ProgrammingBlockEntry<T>, T> supplier) {
        final ProgrammingBlockEntry<T> entry = new ProgrammingBlockEntry<>(supplier, texture);
        PROGRAMMING_BLOCK_REGISTRY.put(id, entry);
        INVERSE_PROGRAMMING_BLOCK_REGISTRY.put(entry, id);

        return entry;
    }

    class Blocks {

        public static ProgrammingBlockEntry START, SUM, DEBUG;

        /**
         * Constructs a texture path to a given block texture name
         * @param name Name of the block texture
         */
        public static ResourceLocation quickTexturePath(String name) {
            return FufoMod.fufoPath("textures/ui/programming/blocks/actions/" + name + ".png");
        }

        public static void register() {

            START = registerBlock(FufoMod.fufoPath("start"), quickTexturePath("ResetBlock"), StartBlock::new);
            SUM = registerBlock(FufoMod.fufoPath("sum"), quickTexturePath("RepeatBlock"), SumBlock::new);
            DEBUG = registerBlock(FufoMod.fufoPath("debug"), quickTexturePath("WaitBlock"), DebugBlock::new);

        }
    }

}
