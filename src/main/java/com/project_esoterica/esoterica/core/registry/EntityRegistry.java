package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.client.renderers.entity.bibit.BibitRenderer;
import com.project_esoterica.esoterica.client.renderers.entity.falling.FallingStarRenderer;
import com.project_esoterica.esoterica.common.entity.falling.FallingCrashpodEntity;
import com.project_esoterica.esoterica.common.entity.robot.BibitEntity;
import com.project_esoterica.esoterica.core.helper.DataHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, EsotericaMod.MODID);

    public static final RegistryObject<EntityType<BibitEntity>> BIBIT = ENTITY_TYPES.register("bibit",
            () -> EntityType.Builder.<BibitEntity>of((e, w) -> new BibitEntity(w), MobCategory.CREATURE).sized(0.5f, 0.5f).setTrackingRange(10)
                    .build(DataHelper.prefix("bibit").toString()));

    public static final RegistryObject<EntityType<FallingCrashpodEntity>> FALLING_CRASHPOD = ENTITY_TYPES.register("falling_crashpod",
            () -> EntityType.Builder.<FallingCrashpodEntity>of((e, w) -> new FallingCrashpodEntity(w), MobCategory.MISC).sized(0.5f, 0.5f).build(DataHelper.prefix("falling_crashpod").toString()));

    @SubscribeEvent
    public static void assignAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.BIBIT.get(), BibitEntity.createAttributes());
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly
    {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.BIBIT.get(), BibitRenderer::new);
            event.registerEntityRenderer(EntityRegistry.FALLING_CRASHPOD.get(), FallingStarRenderer::new);
        }
    }
}
