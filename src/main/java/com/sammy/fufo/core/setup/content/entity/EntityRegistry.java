package com.sammy.fufo.core.setup.content.entity;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.client.renderers.entity.falling.FallingStarRenderer;
import com.sammy.fufo.client.renderers.entity.magic.spell.tier0.MissileProjectileRenderer;
import com.sammy.fufo.client.renderers.entity.weave.HologramWeaveEntityRenderer;
import com.sammy.fufo.client.renderers.entity.weave.WeaveEntityRenderer;
import com.sammy.fufo.client.renderers.entity.wisp.SparkEntityRenderer;
import com.sammy.fufo.client.renderers.entity.wisp.WispEntityRenderer;
import com.sammy.fufo.client.renderers.entity.wraith.StoneWraithRenderer;
import com.sammy.fufo.common.entity.falling.FallingCrashpodEntity;
import com.sammy.fufo.common.entity.magic.spell.tier1.SpellBolt;
import com.sammy.fufo.common.entity.weave.HologramWeaveEntity;
import com.sammy.fufo.common.entity.weave.WeaveEntity;
import com.sammy.fufo.common.entity.wisp.SparkEntity;
import com.sammy.fufo.common.entity.wisp.WispEntity;
import com.sammy.fufo.common.entity.wraith.StoneWraith;
import com.sammy.fufo.core.setup.content.item.ItemRegistry;
import gg.moonflower.pollen.api.item.SpawnEggItemBase;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, FufoMod.FUFO);

    public static final RegistryObject<EntityType<FallingCrashpodEntity>> FALLING_CRASHPOD = register("falling_crashpod", EntityType.Builder.<FallingCrashpodEntity>of((t, l)->new FallingCrashpodEntity(l), MobCategory.MISC).sized(0.5f, 0.5f));

    public static final RegistryObject<EntityType<SparkEntity>> METEOR_FIRE_SPARK = register("spark", EntityType.Builder.<SparkEntity>of((t, l)->new SparkEntity(l), MobCategory.MISC).sized(0.25f, 0.25f));
    public static final RegistryObject<EntityType<WispEntity>> METEOR_FIRE_WISP = register("wisp", EntityType.Builder.<WispEntity>of((t, l)->new WispEntity(l), MobCategory.MISC).sized(0.5f, 0.5f));

    public static final RegistryObject<EntityType<StoneWraith>> STONE_WRAITH = register("stone_wraith", EntityType.Builder.<StoneWraith>of((t, l)->new StoneWraith(l), MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(6));

    public static final RegistryObject<EntityType<HologramWeaveEntity>> HOLOGRAM_WEAVE = register("hologram_weave", EntityType.Builder.<HologramWeaveEntity>of((t,l) -> new HologramWeaveEntity(l), MobCategory.MISC).sized(1.0F,1.0F));
    public static final RegistryObject<EntityType<WeaveEntity>> BASIC_WEAVE = register("basic_weave", EntityType.Builder.<WeaveEntity>of((t,l) -> new WeaveEntity(l), MobCategory.MISC).sized(1.0F,1.0F));

    // SPELLS
    public static final RegistryObject<EntityType<SpellBolt>> SPELL_BOLT = register("spell_bolt", EntityType.Builder.<SpellBolt>of((t, l) -> new SpellBolt(l), MobCategory.MISC).sized(0.1F,0.1F));


    @SubscribeEvent
    public static void assignAttributes(EntityAttributeCreationEvent event) {
        event.put(STONE_WRAITH.get(), StoneWraith.createAttributes().build());
    }

    //TODO: make this wokr, for whatever reason the attempt to register a spawn egg makes it cry
    private static <T extends Mob> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder, int primaryColor, int secondaryColor) {
        RegistryObject<EntityType<T>> object = ENTITY_TYPES.register(id, () -> builder.build(id));
        ItemRegistry.ITEMS.register(id + "_spawn_egg", () -> new SpawnEggItemBase<>(object, primaryColor, secondaryColor, true, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        return object;
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(id, () -> builder.build(id));
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.FALLING_CRASHPOD.get(), FallingStarRenderer::new);
            event.registerEntityRenderer(EntityRegistry.METEOR_FIRE_SPARK.get(), SparkEntityRenderer::new);
            event.registerEntityRenderer(EntityRegistry.METEOR_FIRE_WISP.get(), WispEntityRenderer::new);
            event.registerEntityRenderer(EntityRegistry.STONE_WRAITH.get(), StoneWraithRenderer::new);
            event.registerEntityRenderer(EntityRegistry.HOLOGRAM_WEAVE.get(), HologramWeaveEntityRenderer::new);
            event.registerEntityRenderer(EntityRegistry.BASIC_WEAVE.get(), WeaveEntityRenderer::new);
            event.registerEntityRenderer(EntityRegistry.SPELL_BOLT.get(), MissileProjectileRenderer::new);
        }
    }
}