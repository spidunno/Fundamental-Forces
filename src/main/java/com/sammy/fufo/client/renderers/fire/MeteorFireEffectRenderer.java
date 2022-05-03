package com.sammy.fufo.client.renderers.fire;

import com.sammy.fufo.FufoMod;
import com.sammy.ortus.systems.fireeffect.FireEffectInstance;
import com.sammy.ortus.systems.fireeffect.FireEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

public class MeteorFireEffectRenderer extends FireEffectRenderer<FireEffectInstance> {
    public static final Material FIRST_FLAME = new Material(TextureAtlas.LOCATION_BLOCKS, FufoMod.prefix("block/fire_0"));
    public static final Material SECOND_FLAME = new Material(TextureAtlas.LOCATION_BLOCKS, FufoMod.prefix("block/fire_1"));
    @Override
    public Material getFirstFlame() {
        return FIRST_FLAME;
    }

    @Override
    public Material getSecondFlame() {
        return SECOND_FLAME;
    }
}
