package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.recipe.WeaveRecipe;
import com.sammy.fufo.core.setup.content.RecipeTypeRegistry;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.systems.magic.weaving.Bindable;
import com.sammy.fufo.core.systems.magic.weaving.BindingType;
import com.sammy.fufo.core.systems.magic.weaving.recipe.IngredientBindable;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.awt.*;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class HologramWeaveEntity extends AbstractWeaveEntity {
    public HologramWeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
        this.noPhysics = true;
    }

    public HologramWeaveEntity(Level level) {
        super(EntityRegistry.HOLOGRAM_WEAVE.get(), level);
        this.noPhysics = true;
        this.weave = new WeaveRecipe(new IngredientBindable(Ingredient.EMPTY), FufoMod.fufoPath("idfk"), "amogus").setOutput(Items.DIAMOND_SWORD.getDefaultInstance());
        Bindable bindable = new IngredientBindable(Ingredient.of(Items.BEACON.getDefaultInstance()));
        Bindable cob1 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob2 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob3 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob4 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob5 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob6 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob7 = new IngredientBindable(Ingredient.of(Tags.Items.COBBLESTONE));
        Bindable cob8 = new IngredientBindable(Ingredient.of(Tags.Items.INGOTS));
        Bindable wool = new IngredientBindable(Ingredient.of(ItemTags.WOOL));
        Bindable sword = new IngredientBindable(Ingredient.of(Items.DIAMOND_SWORD.getDefaultInstance()));
        BindingType type = new BindingType(new ResourceLocation("ortus:bingus"));
        this.weave.add(new Vec3i(0, -1, 0), bindable);
        this.weave.add(bindable, type, new Vec3i(-1, 0, 0), cob1);
        this.weave.add(bindable, type, new Vec3i(1, 0, 0), cob2);
        this.weave.add(bindable, type, new Vec3i(0, 0, 1), cob3);
        this.weave.add(bindable, type, new Vec3i(0, 0, -1), cob4);
        this.weave.add(bindable, type, new Vec3i(-1, 0, 1), cob5);
        this.weave.add(bindable, type, new Vec3i(1, 0, -1), cob6);
        this.weave.add(bindable, type, new Vec3i(-1, 0, -1), cob7);
        this.weave.add(bindable, type, new Vec3i(1, 0, 1), cob8);
        this.weave.add(bindable, type, new Vec3i(0, 1, 0), wool);
        this.weave.add(wool, type, new Vec3i(0, 1, 0), sword);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if(pCompound.contains("Weave")) {
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

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (weave != null) {
                weave.getBindables().forEach(b -> {
                    Vec3i offset = b.getLocation();
                    Random rand = level.getRandom();
                    ParticleBuilders.create(OrtusParticleRegistry.STAR_PARTICLE)
                            .setAlpha(0.05f, 0.15f, 0f)
                            .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                            .setLifetime(15 + rand.nextInt(4))
                            .setSpin(nextFloat(rand, 0.01f, 0.05f), 0.02f)
                            .setScale(0.05f, 0.35f + rand.nextFloat() * 0.15f, 0.2f)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setColor(new Color(250, 215, 100), new Color(169, 14, 92))
                            .setColorCoefficient(0.5f)
                            .enableNoClip()
                            .repeat(level, position().x()+offset.getX(), position().y()+offset.getY()+0.45f, position().z()+offset.getZ(), 1);

                    ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                            .setAlpha(0.01f, 0.08f, 0f)
                            .setAlphaEasing(Easing.QUINTIC_IN, Easing.QUINTIC_OUT)
                            .setLifetime(15 + rand.nextInt(4))
                            .setSpin(nextFloat(rand, 0.05f, 0.1f), 0.2f)
                            .setScale(0.05f, 0.15f + rand.nextFloat() * 0.1f, 0)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setColor(new Color(255, 187, 132), new Color(84, 40, 215))
                            .setColorCoefficient(0.5f)
                            .randomOffset(0.04f)
                            .enableNoClip()
                            .randomMotion(0.005f, 0.005f)
                            .repeat(level, position().x()+offset.getX(), position().y()+offset.getY()+0.45f, position().z()+offset.getZ(), 1);
                });
                weave.getLinks().forEach((p, t) -> {

                });
            }
        }
        super.tick();
    }


}