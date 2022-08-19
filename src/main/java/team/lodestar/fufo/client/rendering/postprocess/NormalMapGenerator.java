package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.FufoMod;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.blaze3d.platform.GlConst.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.*;

public class NormalMapGenerator extends PostProcessor {
    private RenderTarget normalMap;

    @Override
    public void init() {
        super.init();
        normalMap = null;

        if (postChain != null) {
            normalMap = postChain.getTempTarget("normalMap");
            if (normalMap == null) {
                postChain = null;
                FufoMod.LOGGER.error("Normal map generator loading failed: failed to get normalMap temp target");
            }
        }
    }

    public void copyNormalMap(RenderTarget target) {
        GlStateManager._glBindFramebuffer(GL_READ_FRAMEBUFFER, normalMap.frameBufferId);
        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, target.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, normalMap.width, normalMap.height, 0, 0, target.width, target.height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
        GlStateManager._glBindFramebuffer(GL_FRAMEBUFFER, 0);
        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, MC.getMainRenderTarget().frameBufferId);
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return FufoMod.fufoPath("generate_normal_map");
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {

    }

    @Override
    public void afterProcess() {

    }
}
