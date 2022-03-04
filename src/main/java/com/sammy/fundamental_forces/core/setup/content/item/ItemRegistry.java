package com.sammy.fundamental_forces.core.setup.content.item;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.common.item.DevTool;
import com.sammy.fundamental_forces.core.systems.rendering.particle.ParticleBuilders;
import com.sammy.fundamental_forces.core.setup.client.ScreenParticleRegistry;
import com.sammy.fundamental_forces.core.setup.content.block.BlockRegistry;
import com.sammy.fundamental_forces.core.setup.content.item.tabs.ContentTab;
import com.sammy.fundamental_forces.core.systems.rendering.particle.ParticleRenderTypes;
import com.sammy.fundamental_forces.core.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

import static com.sammy.fundamental_forces.core.handlers.ScreenParticleHandler.registerItemParticleEmitter;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FundamentalForcesMod.MODID);

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE);
    }

    public static Item.Properties GEAR_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE).stacksTo(1);
    }

    public static final RegistryObject<Item> CRACK = ITEMS.register("crack", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_CRACK = ITEMS.register("block_of_crack", () -> new BlockItem(BlockRegistry.BLOCK_OF_CRACK.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> ASTEROID_ROCK = ITEMS.register("asteroid_rock", () -> new BlockItem(BlockRegistry.ASTEROID_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ASTEROID_CHUNK = ITEMS.register("asteroid_chunk", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ASTRAL_SHARD = ITEMS.register("astral_shard", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DEV_TOOL = ITEMS.register("dev_tool", () -> new DevTool(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> CHARRED_ROCK = ITEMS.register("charred_rock", () -> new BlockItem(BlockRegistry.CHARRED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CHARRED_ROCK = ITEMS.register("polished_charred_rock", () -> new BlockItem(BlockRegistry.POLISHED_CHARRED_ROCK.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> CHARRED_ROCK_SLAB = ITEMS.register("charred_rock_slab", () -> new BlockItem(BlockRegistry.CHARRED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CHARRED_ROCK_SLAB = ITEMS.register("polished_charred_rock_slab", () -> new BlockItem(BlockRegistry.POLISHED_CHARRED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> CHARRED_ROCK_STAIRS = ITEMS.register("charred_rock_stairs", () -> new BlockItem(BlockRegistry.CHARRED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CHARRED_ROCK_STAIRS = ITEMS.register("polished_charred_rock_stairs", () -> new BlockItem(BlockRegistry.POLISHED_CHARRED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> VOLCANIC_GLASS = ITEMS.register("volcanic_glass", () -> new BlockItem(BlockRegistry.VOLCANIC_GLASS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SCORCHED_EARTH = ITEMS.register("scorched_earth", () -> new BlockItem(BlockRegistry.SCORCHED_EARTH.get(), DEFAULT_PROPERTIES()));

    public static class ClientOnly {

        public static void registerParticleEmitters(FMLClientSetupEvent event) {
            registerItemParticleEmitter((s, x, y, order) -> {
                Random random = Minecraft.getInstance().level.random;
                if (Minecraft.getInstance().level.getGameTime() % 6L == 0) {
                    ParticleBuilders.create(ScreenParticleRegistry.STAR)
                            .setLifetime(15 + random.nextInt(10))
                            .setColor(255, 142, 139, 248, 69, 106)
                            .setAlphaCurveMultiplier(0.5f)
                            .setColorCurveMultiplier(0.8f)
                            .setMotionCurveMultiplier(0.98f)
                            .setScale(0.15f + random.nextFloat() * 0.1f)
                            .setAlpha(0.8f + random.nextFloat() * 0.2f, 0.1f)
                            .randomOffset(8, 4)
                            .randomMotion(0.2f, 0.2f)
                            .overwriteRenderType(ParticleRenderTypes.TRANSPARENT)
                            .overwriteRenderOrder(order)
                            .overwriteAnimator(SimpleParticleOptions.Animator.LAST_INDEX)
                            .repeat(x, y-0.25f, 1);
                }
            }, CRACK.get(), BLOCK_OF_CRACK.get());
        }
    }
}