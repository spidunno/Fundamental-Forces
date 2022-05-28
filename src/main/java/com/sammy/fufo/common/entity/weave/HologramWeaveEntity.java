package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.recipe.WeaveRecipe;
import com.sammy.fufo.core.setup.content.RecipeTypeRegistry;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.systems.magic.weaving.BindingType;
import com.sammy.fufo.core.systems.magic.weaving.recipe.IngredientBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.awt.*;

public class HologramWeaveEntity extends AbstractWeaveEntity {
    public HologramWeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
        this.noPhysics = true;
        this.mainColors = new Color[]{new Color(3, 165, 252), new Color(3, 252, 215)};
        this.secondaryColors = new Color[]{new Color(3, 86, 252), new Color(3, 211, 252)};
    }

    public HologramWeaveEntity(Level level) {
        super(EntityRegistry.HOLOGRAM_WEAVE.get(), level);
        this.noPhysics = true;
        this.weave.add(Vec3i.ZERO, new ItemStackBindable(Items.STONE.getDefaultInstance()));
        this.weave.add(this.weave.get(Vec3i.ZERO), new BindingType(FufoMod.fufoPath("boingo")), new Vec3i(0,1,0), new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE)));
        this.mainColors = new Color[]{new Color(0,254,254), new Color(169, 254, 254)};
        this.secondaryColors = new Color[]{new Color(0, 254, 254), new Color(169, 254, 254)};
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Weave")) {
            WeaveRecipe.Serializer serializer = (WeaveRecipe.Serializer) RecipeTypeRegistry.WEAVE.get();
            this.weave = serializer.fromNBT((CompoundTag) pCompound.get("Weave"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        WeaveRecipe recipe = (WeaveRecipe) weave;
        WeaveRecipe.Serializer serializer = (WeaveRecipe.Serializer) RecipeTypeRegistry.WEAVE.get();
        pCompound.put("Weave", serializer.toNBT(recipe));
    }


}