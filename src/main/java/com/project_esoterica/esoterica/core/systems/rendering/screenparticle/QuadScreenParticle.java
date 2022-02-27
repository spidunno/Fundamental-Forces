package com.project_esoterica.esoterica.core.systems.rendering.screenparticle;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@OnlyIn(Dist.CLIENT)
public abstract class QuadScreenParticle extends ScreenParticle {
   protected float quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected QuadScreenParticle(ClientLevel pLevel, double pX, double pY) {
      super(pLevel, pX, pY);
   }

   protected QuadScreenParticle(ClientLevel pLevel, double pX, double pY, double pXSpeed, double pYSpeed) {
      super(pLevel, pX, pY, pXSpeed, pYSpeed);
   }

   @Override
   public void render(BufferBuilder bufferBuilder, RenderGameOverlayEvent.Post event) {
      PoseStack stack = event.getMatrixStack();
      float partialTicks = event.getPartialTicks();
      float size = getQuadSize(partialTicks)*10;
      float u0 = getU0();
      float u1 = getU1();
      float v0 = getV0();
      float v1 = getV1();
      float roll = Mth.lerp(partialTicks, this.oRoll, this.roll);
      stack.mulPose(Vector3f.ZP.rotation(roll));
      bufferBuilder.vertex(stack.last().pose(), (float) x, (float) y + size, 0).color(rCol, gCol, bCol, alpha).uv(u0, v1).endVertex();
      bufferBuilder.vertex(stack.last().pose(), (float) x + size, (float) y + size, 0).color(rCol, gCol, bCol, alpha).uv(u1, v1).endVertex();
      bufferBuilder.vertex(stack.last().pose(), (float) x + size, (float) y, 0).color(rCol, gCol, bCol, alpha).uv(u1, v0).endVertex();
      bufferBuilder.vertex(stack.last().pose(), (float) x, (float) y, 0).color(rCol, gCol, bCol, alpha).uv(u0, v0).endVertex();
   }

   public float getQuadSize(float partialTicks) {
      return this.quadSize;
   }

   protected abstract float getU0();

   protected abstract float getU1();

   protected abstract float getV0();

   protected abstract float getV1();
}