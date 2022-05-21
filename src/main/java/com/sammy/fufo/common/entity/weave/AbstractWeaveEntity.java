package com.sammy.fufo.common.entity.weave;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import com.sammy.fufo.common.recipe.WeaveRecipe;
import com.sammy.fufo.core.setup.content.RecipeTypeRegistry;
import com.sammy.fufo.core.systems.magic.weaving.BindingType;
import com.sammy.fufo.core.systems.magic.weaving.StandardWeave;
import com.sammy.fufo.core.systems.magic.weaving.Weave;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;
import java.util.Map;


public abstract class AbstractWeaveEntity extends Entity {
    public Weave<?> weave = new StandardWeave(new ItemStackBindable(Items.AIR.getDefaultInstance()));

    public AbstractWeaveEntity(EntityType<?> entity, Level level){
        super(entity, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param pCompound
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.weave = Weave.deserialize(pCompound.getCompound("Weave"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.put("Weave", weave.serialize());
    }

    @Override
    public boolean skipAttackInteraction(Entity pEntity) {
        // only go through if player is severing the link?
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}