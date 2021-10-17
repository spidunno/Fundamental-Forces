package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.client.renderer.BibitRenderer;
import com.project_esoterica.esoterica.common.entity.Bibit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, EsotericaMod.MOD_ID);

    public static final RegistryObject<EntityType<Bibit>> BIBIT = ENTITY_TYPES.register("bibit",
            () -> EntityType.Builder.<Bibit>of((e,w)->new Bibit(w), MobCategory.CREATURE).sized(0.5f, 0.5f).setTrackingRange(10)
                    .build(EsotericaHelper.prefix("bibit").toString()));

    @SubscribeEvent
    public static void assignAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.BIBIT.get(), Bibit.createAttributes());
    }
}
