package com.project_esoterica.esoterica.common.entity.falling;

import com.project_esoterica.esoterica.EsotericaHelper;
import com.project_esoterica.esoterica.common.packets.ScreenshakePacket;
import com.project_esoterica.esoterica.core.eventhandlers.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public abstract class FallingEntity extends Entity {

    public FallingEntity(EntityType<?> p_19870, Level p_19871_) {
        super(p_19870, p_19871_);
        this.noPhysics = true;
    }
}