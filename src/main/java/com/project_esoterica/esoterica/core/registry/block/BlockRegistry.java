package com.project_esoterica.esoterica.core.registry.block;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.block.FlammableMeteoriteBlock;
import com.project_esoterica.esoterica.common.block.MeteorFlameBlock;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import com.project_esoterica.esoterica.core.systems.block.SimpleBlockProperties;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EsotericaMod.MOD_ID);

    public static BlockBehaviour.Properties ASTEROID_ROCK_PROPERTIES() {
        return new SimpleBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F);
    }
    public static SimpleBlockProperties METEOR_FIRE_PROPERTIES() {
        return new SimpleBlockProperties(Material.FIRE, MaterialColor.FIRE).ignoreBlockStateDatagen().ignoreLootDatagen().sound(SoundType.WOOL).noCollission().instabreak().lightLevel(b-> 15);
    }

    public static final RegistryObject<Block> METEOR_FIRE = BLOCKS.register("meteor_fire", () -> new MeteorFlameBlock(METEOR_FIRE_PROPERTIES()));
    public static final RegistryObject<Block> ASTEROID_ROCK = BLOCKS.register("asteroid_rock", () -> new FlammableMeteoriteBlock(ASTEROID_ROCK_PROPERTIES(), (s, p)-> METEOR_FIRE.get().defaultBlockState()));


    @Mod.EventBusSubscriber(modid= EsotericaMod.MOD_ID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void setRenderLayers(FMLClientSetupEvent event) {
            Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
            DataHelper.takeAll(blocks, b -> b.get() instanceof MeteorFlameBlock).forEach(ClientOnly::setCutout);
        }

        public static void setCutout(RegistryObject<Block> b) {
            ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped());
        }
    }

}