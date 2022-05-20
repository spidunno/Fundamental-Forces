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
        Bindable floppe = new IngredientBindable(new Vec3i(1,1,1), Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable bingus = new ItemStackBindable(new Vec3i(1,1,1), Items.HORSE_SPAWN_EGG.getDefaultInstance());
        BindingType type = new BindingType(new ResourceLocation("ortus:bingus"));
        this.weave.add(new Vec3i(0,0,0), bindable);
        this.weave.add(bindable, type, new Vec3i(-1,0,-1), floppe);
        this.weave.add(bindable, type, new Vec3i(1,0,1), bingus);
    }
}
