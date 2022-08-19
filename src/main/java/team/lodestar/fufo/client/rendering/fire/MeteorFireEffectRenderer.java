package team.lodestar.fufo.client.rendering.fire;

import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.systems.fireeffect.FireEffectInstance;
import team.lodestar.lodestone.systems.fireeffect.FireEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

public class MeteorFireEffectRenderer extends FireEffectRenderer<FireEffectInstance> {
    public static final Material FIRST_FLAME = new Material(TextureAtlas.LOCATION_BLOCKS, FufoMod.fufoPath("block/fire_0"));
    public static final Material SECOND_FLAME = new Material(TextureAtlas.LOCATION_BLOCKS, FufoMod.fufoPath("block/fire_1"));
    @Override
    public Material getFirstFlame() {
        return FIRST_FLAME;
    }

    @Override
    public Material getSecondFlame() {
        return SECOND_FLAME;
    }
}
