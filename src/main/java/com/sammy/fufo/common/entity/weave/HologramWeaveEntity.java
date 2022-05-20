package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.systems.magic.weaving.Bindable;
import com.sammy.fufo.core.systems.magic.weaving.BindingType;
import com.sammy.fufo.core.systems.magic.weaving.recipe.IngredientBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public class HologramWeaveEntity extends AbstractWeaveEntity{
    public HologramWeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
        this.noPhysics = true;
    }
    public HologramWeaveEntity(Level level){
        super(EntityRegistry.HOLOGRAM_WEAVE.get(), level);
        this.noPhysics = true;
        Bindable bindable = new ItemStackBindable(new Vec3i(1,1,1), Items.BEACON.getDefaultInstance());
        Bindable cob1 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob2 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob3 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob4 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob5 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob6 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob7 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable cob8 = new ItemStackBindable(new Vec3i(1,1,1), Items.COBBLESTONE.getDefaultInstance());
        Bindable wool = new ItemStackBindable(new Vec3i(1,1,1), Items.BROWN_WOOL.getDefaultInstance());
        Bindable sword = new ItemStackBindable(new Vec3i(1,1,1), Items.DIAMOND_SWORD.getDefaultInstance());
        BindingType type = new BindingType(new ResourceLocation("ortus:bingus"));
        this.weave.add(new Vec3i(0,-1,0), bindable);
        this.weave.add(bindable, type, new Vec3i(-1,0,0), cob1);
        this.weave.add(bindable, type, new Vec3i(1,0,0), cob2);
        this.weave.add(bindable, type, new Vec3i(0,0,1), cob3);
        this.weave.add(bindable, type, new Vec3i(0,0,-1), cob4);
        this.weave.add(bindable, type, new Vec3i(-1,0,1), cob5);
        this.weave.add(bindable, type, new Vec3i(1,0,-1), cob6);
        this.weave.add(bindable, type, new Vec3i(-1,0,-1), cob7);
        this.weave.add(bindable, type, new Vec3i(1,0,1), cob8);
        this.weave.add(bindable, type, new Vec3i(0,1,0), wool);
        this.weave.add(wool, type, new Vec3i(0,1,0), sword);
    }
}
