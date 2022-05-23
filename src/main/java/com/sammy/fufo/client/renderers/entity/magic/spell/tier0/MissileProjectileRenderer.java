package com.sammy.fufo.client.renderers.entity.magic.spell.tier0;
import com.sammy.fufo.common.entity.magic.spell.tier0.SpellMissile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class MissileProjectileRenderer extends EntityRenderer<SpellMissile> {
    public MissileProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(SpellMissile pEntity) {
        return null;
    }
}
