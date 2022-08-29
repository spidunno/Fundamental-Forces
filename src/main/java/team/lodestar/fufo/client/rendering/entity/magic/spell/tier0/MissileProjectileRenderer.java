package team.lodestar.fufo.client.rendering.entity.magic.spell.tier0;
import team.lodestar.fufo.common.entity.magic.spell.tier1.SpellBolt;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MissileProjectileRenderer extends EntityRenderer<SpellBolt> {


    public MissileProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    /**
     * Returns the location of an entity's texture.
     *
     * @param pEntity
     */
    @Override
    public ResourceLocation getTextureLocation(SpellBolt pEntity) {
        return null;
    }
}
