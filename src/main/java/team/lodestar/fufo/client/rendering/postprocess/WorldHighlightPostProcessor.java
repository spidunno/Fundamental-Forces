package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.registry.client.FufoPostProcessingEffects;
import team.lodestar.lodestone.systems.postprocess.MultiInstancePostProcessor;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;

public class WorldHighlightPostProcessor extends MultiInstancePostProcessor<WorldHighlightFx> {
    private RenderTarget normalMap;
    private EffectInstance effectWorldHighlight;

    @Override
    public ResourceLocation getPostChainLocation() {
        return FufoMod.fufoPath("world_highlight");
    }

    @Override
    protected int getMaxInstances() {
        return 32;
    }

    @Override
    protected int getDataSizePerInstance() {
        return 7;
    }

    @Override
    public void init() {
        super.init();

        if (postChain != null) {
            normalMap = postChain.getTempTarget("normalMap");
            effectWorldHighlight = effects[0];
            if (normalMap == null) {
                postChain = null;
                FufoMod.LOGGER.error("Can not get normalMap sampler, disabling world highlight!");
            }
        }
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        super.beforeProcess(viewModelStack);

        FufoPostProcessingEffects.NORMAL.setActive(isActive());
        if (!isActive()) return;

        FufoPostProcessingEffects.NORMAL.copyNormalMap(normalMap);

        setDataBufferUniform(effectWorldHighlight, "Data", "instanceCount");
    }

    @Override
    public void afterProcess() {

    }
}
