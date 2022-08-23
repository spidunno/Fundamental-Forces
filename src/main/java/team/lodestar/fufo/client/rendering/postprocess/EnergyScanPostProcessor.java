package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.registry.client.FufoPostProcessingEffects;
import team.lodestar.lodestone.systems.postprocess.MultiInstancePostProcessor;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import team.lodestar.fufo.FufoMod;

@Mod.EventBusSubscriber(Dist.CLIENT)//FOR TESTING
public class EnergyScanPostProcessor extends MultiInstancePostProcessor<EnergyScanFx> {
    private EffectInstance effectEnergyScan;

    //FOR TESTING
    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            if (event.getKey() == GLFW.GLFW_KEY_L) {
                FufoPostProcessingEffects.NORMAL.init();
                FufoPostProcessingEffects.IMPACT_FRAME.init();
                FufoPostProcessingEffects.ENERGY_SCAN.init();
                FufoPostProcessingEffects.ENERGY_SPHERE.init();
                FufoPostProcessingEffects.WORLD_HIGHLIGHT.init();
                FufoPostProcessingEffects.WORLD_HIGHLIGHT.setActive(true);
                FufoPostProcessingEffects.NORMAL.setActive(true);
            }
            if (event.getKey() == GLFW.GLFW_KEY_U) {
                FufoPostProcessingEffects.WORLD_HIGHLIGHT.setActive(false);
                FufoPostProcessingEffects.NORMAL.setActive(false);
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return FufoMod.fufoPath("energy_scan");
    }

    @Override
    protected int getMaxInstances() {
        return 16;
    }

    @Override
    protected int getDataSizePerInstance() {
        return 19;
    }

    @Override
    public void init() {
        super.init();

        if (postChain != null)
            effectEnergyScan = effects[0];
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        super.beforeProcess(viewModelStack);

        setDataBufferUniform(effectEnergyScan, "Data", "instanceCount");
    }

    @Override
    public void afterProcess() {

    }
}
