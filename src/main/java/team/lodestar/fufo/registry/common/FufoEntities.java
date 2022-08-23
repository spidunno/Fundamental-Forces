package team.lodestar.fufo.registry.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.client.rendering.entity.falling.FallingStarRenderer;
import team.lodestar.fufo.client.rendering.entity.magic.spell.tier0.MissileProjectileRenderer;
import team.lodestar.fufo.client.rendering.entity.weave.HologramWeaveEntityRenderer;
import team.lodestar.fufo.client.rendering.entity.weave.WeaveEntityRenderer;
import team.lodestar.fufo.client.rendering.entity.wisp.SparkEntityRenderer;
import team.lodestar.fufo.client.rendering.entity.wisp.WispEntityRenderer;
import team.lodestar.fufo.common.entity.falling.FallingCrashpodEntity;
import team.lodestar.fufo.common.entity.magic.spell.tier1.SpellBolt;
import team.lodestar.fufo.common.entity.weave.HologramWeaveEntity;
import team.lodestar.fufo.common.entity.weave.WeaveEntity;
import team.lodestar.fufo.common.entity.wisp.SparkEntity;
import team.lodestar.fufo.common.entity.wisp.WispEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FufoEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FufoMod.FUFO);

    public static final RegistryObject<EntityType<FallingCrashpodEntity>> FALLING_CRASHPOD = register("falling_crashpod", EntityType.Builder.<FallingCrashpodEntity>of((t, l) -> new FallingCrashpodEntity(l), MobCategory.MISC).sized(0.5f, 0.5f));

    public static final RegistryObject<EntityType<SparkEntity>> METEOR_FIRE_SPARK = register("spark", EntityType.Builder.<SparkEntity>of((t, l) -> new SparkEntity(l), MobCategory.MISC).sized(0.25f, 0.25f));
    public static final RegistryObject<EntityType<WispEntity>> METEOR_FIRE_WISP = register("wisp", EntityType.Builder.<WispEntity>of((t, l) -> new WispEntity(l), MobCategory.MISC).sized(0.5f, 0.5f));

    // TODO: Pollen 1.19
//    public static final RegistryObject<EntityType<StoneWraith>> STONE_WRAITH = register("stone_wraith", EntityType.Builder.<StoneWraith>of((t, l) -> new StoneWraith(l), MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(6));

    public static final RegistryObject<EntityType<HologramWeaveEntity>> HOLOGRAM_WEAVE = register("hologram_weave", EntityType.Builder.<HologramWeaveEntity>of((t, l) -> new HologramWeaveEntity(l), MobCategory.MISC).sized(1.0F, 1.0F));
    public static final RegistryObject<EntityType<WeaveEntity>> BASIC_WEAVE = register("basic_weave", EntityType.Builder.<WeaveEntity>of((t, l) -> new WeaveEntity(l), MobCategory.MISC).sized(1.0F, 1.0F));

    // SPELLS
    public static final RegistryObject<EntityType<SpellBolt>> SPELL_BOLT = register("spell_bolt", EntityType.Builder.<SpellBolt>of((t, l) -> new SpellBolt(l), MobCategory.MISC).sized(0.1F, 0.1F));

// TODO: Pollen 1.19
//    @SubscribeEvent
//    public static void assignAttributes(EntityAttributeCreationEvent event) {
//        event.put(STONE_WRAITH.get(), StoneWraith.createAttributes().build());
//    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(id, () -> builder.build(id));
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(FufoEntities.FALLING_CRASHPOD.get(), FallingStarRenderer::new);
            event.registerEntityRenderer(FufoEntities.METEOR_FIRE_SPARK.get(), SparkEntityRenderer::new);
            event.registerEntityRenderer(FufoEntities.METEOR_FIRE_WISP.get(), WispEntityRenderer::new);
            // TODO: Pollen 1.19
//            event.registerEntityRenderer(FufoEntities.STONE_WRAITH.get(), StoneWraithRenderer::new);
            event.registerEntityRenderer(FufoEntities.HOLOGRAM_WEAVE.get(), HologramWeaveEntityRenderer::new);
            event.registerEntityRenderer(FufoEntities.BASIC_WEAVE.get(), WeaveEntityRenderer::new);
            event.registerEntityRenderer(FufoEntities.SPELL_BOLT.get(), MissileProjectileRenderer::new);
        }
    }
}