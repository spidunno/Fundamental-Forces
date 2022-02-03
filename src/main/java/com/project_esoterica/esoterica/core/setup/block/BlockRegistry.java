package com.project_esoterica.esoterica.core.setup.block;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.common.block.FlammableMeteoriteBlock;
import com.project_esoterica.esoterica.common.block.MeteorFlameBlock;
import com.project_esoterica.esoterica.common.block.OrbBlock;
import com.project_esoterica.esoterica.common.block.ScorchedEarthBlock;
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
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EsotericaMod.MODID);

    public static BlockBehaviour.Properties ASTEROID_ROCK_PROPERTIES() {
        return new SimpleBlockProperties(Material.STONE, MaterialColor.STONE).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.25F, 9.0F);
    }
    public static SimpleBlockProperties METEOR_FIRE_PROPERTIES() {
        return new SimpleBlockProperties(Material.FIRE, MaterialColor.FIRE).ignoreBlockStateDatagen().ignoreLootDatagen().sound(SoundType.WOOL).noCollission().instabreak().lightLevel(b-> 15);
    }
    public static SimpleBlockProperties CHARRED_ROCK_PROPERTIES() {
        return new SimpleBlockProperties(Material.STONE, MaterialColor.STONE).needsPickaxe().sound(SoundType.BASALT).requiresCorrectToolForDrops().strength(1.5F, 9.0F);
    }
    public static SimpleBlockProperties VOLCANIC_GLASS_PROPERTIES() {
        return new SimpleBlockProperties(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.4f);
    }
    public static SimpleBlockProperties SCORCHED_EARTH_PROPERTIES() {
        return new SimpleBlockProperties(Material.GRASS).sound(SoundType.GRASS).noOcclusion().strength(0.7f);
    }
    public static SimpleBlockProperties ORB_PROPERTIES() {
        return new SimpleBlockProperties(Material.WOOL, MaterialColor.COLOR_BLUE).sound(SoundType.WOOL).noCollission().instabreak().lightLevel((b) -> 14);
    }

    public static final RegistryObject<Block> FORCE_ORB = BLOCKS.register("force_orb", () -> new OrbBlock(ORB_PROPERTIES()));

    public static final RegistryObject<Block> METEOR_FIRE = BLOCKS.register("meteor_fire", () -> new MeteorFlameBlock(METEOR_FIRE_PROPERTIES()));
    public static final RegistryObject<Block> ASTEROID_ROCK = BLOCKS.register("asteroid_rock", () -> new FlammableMeteoriteBlock(ASTEROID_ROCK_PROPERTIES(), (s, p)-> METEOR_FIRE.get().defaultBlockState()));

    public static final RegistryObject<Block> CHARRED_ROCK = BLOCKS.register("charred_rock", () -> new Block(CHARRED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CHARRED_ROCK = BLOCKS.register("polished_charred_rock", () -> new Block(CHARRED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CHARRED_ROCK_SLAB = BLOCKS.register("charred_rock_slab", () -> new SlabBlock(CHARRED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CHARRED_ROCK_SLAB = BLOCKS.register("polished_charred_rock_slab", () -> new SlabBlock(CHARRED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CHARRED_ROCK_STAIRS = BLOCKS.register("charred_rock_stairs", () -> new StairBlock(() -> CHARRED_ROCK.get().defaultBlockState(), CHARRED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CHARRED_ROCK_STAIRS = BLOCKS.register("polished_charred_rock_stairs", () -> new StairBlock(() -> POLISHED_CHARRED_ROCK.get().defaultBlockState(), CHARRED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> VOLCANIC_GLASS = BLOCKS.register("volcanic_glass", () -> new GlassBlock(VOLCANIC_GLASS_PROPERTIES()));
    public static final RegistryObject<Block> SCORCHED_EARTH = BLOCKS.register("scorched_earth", () -> new ScorchedEarthBlock(SCORCHED_EARTH_PROPERTIES()));


    @Mod.EventBusSubscriber(modid= EsotericaMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void setRenderLayers(FMLClientSetupEvent event) {
            Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
            DataHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof GlassBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(ClientOnly::setCutout);
            DataHelper.takeAll(blocks, b -> b.get() instanceof MeteorFlameBlock).forEach(ClientOnly::setCutout);
        }

        public static void setCutout(RegistryObject<Block> b) {
            ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped());
        }
    }
}