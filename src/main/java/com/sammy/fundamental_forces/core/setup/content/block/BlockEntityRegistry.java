package com.sammy.fundamental_forces.core.setup.content.block;

import com.sammy.fundamental_forces.FufoMod;
import com.sammy.fundamental_forces.client.renderers.block.OrbRenderer;
import com.sammy.fundamental_forces.common.block.MeteorFlameBlock;
import com.sammy.fundamental_forces.common.block.OrbBlock;
import com.sammy.fundamental_forces.common.blockentity.MeteorFlameBlockEntity;
import com.sammy.fundamental_forces.common.blockentity.OrbBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FufoMod.FUFO);
    public static final RegistryObject<BlockEntityType<MeteorFlameBlockEntity>> METEOR_FLAME = BLOCK_ENTITY_TYPES.register("meteor_flame", () -> BlockEntityType.Builder.of(MeteorFlameBlockEntity::new, getBlocks(MeteorFlameBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<OrbBlockEntity>> ORB = BLOCK_ENTITY_TYPES.register("orb", () -> BlockEntityType.Builder.of(OrbBlockEntity::new, getBlocks(OrbBlock.class)).build(null));

    public static Block[] getBlocks(Class<?>... blockClasses) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (clazz.equals(registryObject.get().getClass())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }
    @Mod.EventBusSubscriber(modid= FufoMod.FUFO, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ORB.get(), OrbRenderer::new);
        }
    }
}
