package com.sammy.fundamental_forces.core.setup.content.entity;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.client.renderers.entity.falling.FallingStarRenderer;
import com.sammy.fundamental_forces.common.entity.falling.FallingCrashpodEntity;
import com.sammy.fundamental_forces.core.helper.DataHelper;
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
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, FundamentalForcesMod.MODID);


    public static final RegistryObject<EntityType<FallingCrashpodEntity>> FALLING_CRASHPOD = ENTITY_TYPES.register("falling_crashpod",
            () -> EntityType.Builder.<FallingCrashpodEntity>of((e, w) -> new FallingCrashpodEntity(w), MobCategory.MISC).sized(0.5f, 0.5f).build(DataHelper.prefix("falling_crashpod").toString()));

    @SubscribeEvent
    public static void assignAttributes(EntityAttributeCreationEvent event) {
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly
    {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.FALLING_CRASHPOD.get(), FallingStarRenderer::new);
        }
    }
}
