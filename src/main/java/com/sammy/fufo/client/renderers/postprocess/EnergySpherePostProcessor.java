package com.sammy.fufo.client.renderers.postprocess;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.sammy.fufo.FufoMod;
import com.sammy.ortus.systems.postprocess.MultiInstancePostProcessor;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class EnergySpherePostProcessor extends MultiInstancePostProcessor<EnergySphereFx> {
    private EffectInstance effectEnergySphere;

    @Override
    public ResourceLocation getPostChainLocation() {
        return FufoMod.fufoPath("energy_sphere");
    }

    @Override
    protected int getMaxInstances() {
        return 16;
    }

    @Override
    protected int getDataSizePerInstance() {
        return 8;
    }

    @Override
    public void init() {
        super.init();

        if (postChain != null)
            effectEnergySphere = effects[0];
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        super.beforeProcess(viewModelStack);

        Camera camera = MC.gameRenderer.getMainCamera();

        setDataBufferUniform(effectEnergySphere, "Data", "instanceCount");
        effectEnergySphere.safeGetUniform("time").set((float) time);
        effectEnergySphere.safeGetUniform("nearPlaneDistance").set(GameRenderer.PROJECTION_Z_NEAR);
        effectEnergySphere.safeGetUniform("farPlaneDistance").set(MC.gameRenderer.getDepthFar());
        effectEnergySphere.safeGetUniform("fov").set((float) Math.toRadians(MC.gameRenderer.getFov(camera, MC.getFrameTime(), true) / 2D));
        effectEnergySphere.safeGetUniform("aspectRatio").set((float) MC.getWindow().getWidth() / (float) MC.getWindow().getHeight());
        effectEnergySphere.safeGetUniform("cameraPos").set(new Vector3f(camera.getPosition()));
        effectEnergySphere.safeGetUniform("lookVector").set(camera.getLookVector());
        effectEnergySphere.safeGetUniform("upVector").set(camera.getUpVector());
        effectEnergySphere.safeGetUniform("leftVector").set(camera.getLeftVector());




        Matrix4f invertedViewMatrix = new Matrix4f(viewModelStack.last().pose());
        invertedViewMatrix.invert();
        Matrix4f invertedProjectionMatrix = new Matrix4f(RenderSystem.getProjectionMatrix());
        invertedProjectionMatrix.invert();
        effectEnergySphere.safeGetUniform("invViewMat").set(invertedViewMatrix);
        effectEnergySphere.safeGetUniform("invProjMat").set(invertedProjectionMatrix);

    }

    @Override
    public void afterProcess() {

    }

    
}
