package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.registry.client.FufoPostProcessingEffects;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class ImpactFramePostProcessor extends PostProcessor {
    public boolean shouldCutout = true;

    private EffectInstance effectRadialBlur;
    private EffectInstance effectWorldRing;
    private EffectInstance effectFastBlur;
    private EffectInstance effectRadialNoise;
    private EffectInstance effectStep;
    private EffectInstance effectInvert;
    private EffectInstance effectMix;

    private Vector3f centerWorldPos = new Vector3f(0.5F, 70F, 0.5F);
    private float duration = 1F;
    private Runnable onComplete;

    @Override
    public void init() {
        super.init();

        if (postChain != null) {
            effectRadialBlur = effects[4];
            effectWorldRing = effects[5];
            effectFastBlur = effects[6];
            effectRadialNoise = effects[7];
            effectStep = effects[8];
            effectInvert = effects[9];
            effectMix = effects[10];
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return FufoMod.fufoPath("impact");
    }

    public boolean playEffect(Vector3f center, float duration, Runnable onComplete) {
        if (isActive()) return false;

        Vector3f vecToCenter = center.copy();
        vecToCenter.sub(new Vector3f(MC.gameRenderer.getMainCamera().getPosition()));
        vecToCenter.normalize();
        if (MC.gameRenderer.getMainCamera().getLookVector().dot(vecToCenter) < 0.2)
            return false;

        setActive(true);
        this.centerWorldPos = center;
        this.duration = duration;
        this.onComplete = onComplete;
        shouldCutout = false;
        return true;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        float progress = (float) (time / duration);

        if (progress > .5F) {
            if (onComplete != null) {
                onComplete.run();
                onComplete = null;
            }
        }

        if (progress > 3F/4F) shouldCutout = true;

        if (progress > 1F) {
            setActive(false);
            FufoPostProcessingEffects.NORMAL.setActive(false);//TODO: a better way to control normal map generation to that multiple things can use it at the same time
            return;
        }

        Vec2 centerTexCoord = RenderHelper.worldPosToTexCoord(centerWorldPos, viewModelStack);

        effectWorldRing.safeGetUniform("center").set(centerWorldPos);
        effectRadialBlur.safeGetUniform("center").set(centerTexCoord.x, centerTexCoord.y);
        effectRadialNoise.safeGetUniform("center").set(centerTexCoord.x, centerTexCoord.y);

        effectWorldRing.safeGetUniform("radius").set(progress*300F);
        effectWorldRing.safeGetUniform("width").set(10F);

        effectRadialBlur.safeGetUniform("radius").set(0.001F);
        effectFastBlur.safeGetUniform("radius").set(progress*8F);
//        effectRadialBlur.safeGetUniform("intensity").set(-3F*Mth.square(progress-1F));
        float intensity = progress < .07F
                ? .01F/Mth.abs(progress-.07F)+.3F
                : .03F/Mth.abs(progress-.07F)+.3F;
        effectRadialBlur.safeGetUniform("intensity").set(intensity);
//        effectRadialBlur.safeGetUniform("intensity").set(.01F/Mth.abs(progress-.15F)+.15F);
        effectStep.safeGetUniform("threshold").set(Math.max((progress-.5F)*2F+.15F, 0F) + .01F);
//        effectStep.safeGetUniform("threshold").set(.01F);

        effectInvert.safeGetUniform("invert").set((progress>.05F && progress<.25F) ? 1 : 0);

        effectMix.safeGetUniform("m").set(Math.max(progress*4F - 3F, 0F));
    }

    @Override
    public void afterProcess() {

    }
}
