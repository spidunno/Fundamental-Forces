package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.entity.BibitEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, EsotericaMod.MOD_ID);

    public static final RegistryObject<EntityType<BibitEntity>> BIBIT = ENTITY_TYPES.register("bibit",
            () -> EntityType.Builder.<BibitEntity>of((e, w)->new BibitEntity(w), MobCategory.CREATURE).sized(0.5f, 0.5f).setTrackingRange(10)
                    .build(EsotericaHelper.prefix("bibit").toString()));

    @SubscribeEvent
    public static void assignAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.BIBIT.get(), BibitEntity.createAttributes());
    }
}
