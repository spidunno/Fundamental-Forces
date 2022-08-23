package team.lodestar.fufo.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.fufo.unsorted.util.DevToolResponse;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import team.lodestar.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;

import java.awt.*;

import static net.minecraft.util.Mth.nextFloat;

public class DevTool extends Item implements ItemParticleEmitter {

    public DevTool(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity te = level.getBlockEntity(context.getClickedPos());
        if (te instanceof DevToolResponse dev) {
        	dev.onDevTool(context);
        	return InteractionResult.SUCCESS;
        }
//        if (level instanceof ServerLevel serverLevel) {
//            BlockPos pos = context.getClickedPos();
//            MeteoriteFeature.generateMeteorite(serverLevel, serverLevel.getChunkSource().getGenerator(), pos, level.random);
//            return InteractionResult.SUCCESS;
//        }
        return super.useOn(context);
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        testFunnyTestFromMalum(Color.RED, Color.BLUE, stack, x, y, renderOrder);
    }
    public static void testFunnyTestFromMalum(Color color, Color endColor, ItemStack stack, float pXPosition, float pYPosition, ScreenParticle.RenderOrder renderOrder) {
        RandomSource rand = Minecraft.getInstance().level.getRandom();
        ParticleBuilders.create(LodestoneScreenParticleRegistry.TWINKLE)
                .setAlpha(0.07f, 0f)
                .setLifetime(10 + rand.nextInt(10))
                .setScale(0.4f + rand.nextFloat(), 0)
                .setColor(color, endColor)
                .setMotionCoefficient(2f)
                .randomOffset(0.05f)
                .randomMotion(0.05f, 0.05f)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack)
                .repeat(pXPosition, pYPosition, 1);

        ParticleBuilders.create(LodestoneScreenParticleRegistry.SMOKE)
                .setAlpha(0.01f, 0f)
                .setLifetime(20 + rand.nextInt(8))
                .setSpin(nextFloat(rand, 0.2f, 0.4f))
                .setScale(0.6f + rand.nextFloat() * 0.4f, 0)
                .setColor(color, endColor)
                .setMotionCoefficient(1.25f)
                .randomOffset(0.1f)
                .randomMotion(0.4f, 0.4f)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack)
                .repeat(pXPosition, pYPosition, 1)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.8f + rand.nextFloat() * 0.4f, 0f)
                .randomMotion(0.01f, 0.01f)
                .repeat(pXPosition, pYPosition, 1);
    }
}
