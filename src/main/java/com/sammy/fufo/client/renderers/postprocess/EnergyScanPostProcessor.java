package com.sammy.fufo.client.renderers.postprocess;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.sammy.fufo.core.setup.client.FufoPostProcessorRegistry;
import com.sammy.ortus.systems.postprocess.MultiInstancePostProcessor;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.sammy.fufo.FufoMod.fufoPath;

@Mod.EventBusSubscriber(Dist.CLIENT)//FOR TESTING
public class EnergyScanPostProcessor extends MultiInstancePostProcessor<EnergyScanFx> {
    private EffectInstance effectEnergyScan;

    //FOR TESTING
    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            if (event.getKey() == GLFW.GLFW_KEY_L) {
                FufoPostProcessorRegistry.ENERGY_SCAN.init();
                FufoPostProcessorRegistry.ENERGY_SPHERE.init();
//                FufoPostProcessorRegistry.EDGE.init();
//                FufoPostProcessorRegistry.EDGE.setActive(true);
            }
            if (event.getKey() == GLFW.GLFW_KEY_U) {
//                FufoPostProcessorRegistry.EDGE.setActive(false);
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return fufoPath("energy_scan");
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

        Matrix4f invertedViewMatrix = new Matrix4f(viewModelStack.last().pose());
        invertedViewMatrix.invert();

        Matrix4f invertedProjectionMatrix = new Matrix4f(RenderSystem.getProjectionMatrix());
        invertedProjectionMatrix.invert();

        Camera camera = MC.gameRenderer.getMainCamera();

        setDataBufferUniform(effectEnergyScan, "Data", "instanceCount");
        effectEnergyScan.safeGetUniform("time").set((float) time);
        effectEnergyScan.safeGetUniform("invViewMat").set(invertedViewMatrix);
        effectEnergyScan.safeGetUniform("invProjMat").set(invertedProjectionMatrix);
        effectEnergyScan.safeGetUniform("cameraPos").set(new Vector3f(camera.getPosition()));
    }

    @Override
    public void afterProcess() {

    }
}
