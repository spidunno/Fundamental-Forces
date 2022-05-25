package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.systems.magic.weaving.Weave;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.awt.*;

public class WeaveEntity extends AbstractWeaveEntity{
    public WeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
    }

    public WeaveEntity(Level level) {
        super(EntityRegistry.BASIC_WEAVE.get(),level);
        this.mainColors = new Color[]{new Color(250,226,0), new Color(223, 219, 120)};
        this.secondaryColors = new Color[]{new Color(252, 175, 50), new Color(247, 210, 151)};
    }

    public void setBaseItemBindable(ItemStackBindable item){
        this.weave.add(Vec3i.ZERO, item);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.weave = Weave.deserialize(pCompound.getCompound("Weave"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.put("Weave", weave.serialize());
    }

}
