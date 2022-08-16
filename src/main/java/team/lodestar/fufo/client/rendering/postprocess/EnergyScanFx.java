package team.lodestar.fufo.client.rendering.postprocess;

import com.mojang.math.Vector3f;
import team.lodestar.lodestone.helpers.util.Color;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

/**
 * FadeMinDist: the distance from the center where the fading begins
 * FadeMaxDist: the distance from the center where the fading ends (becomes completely invisible)
 */
public class EnergyScanFx extends DynamicShaderFxInstance {
    public Vector3f center;
    public float virtualRadius; // only for the calculation, actual max radius depends on the fadeMaxDist

    public Color energyBaseColor;
    public float energyColorIntensity;
    public float energyMixIntensity;
    public float energyWidth; // not accurate
    public float energyFadeMaxDist;
    public float energyFadeMinDist;

    public Color magicBaseColor;
    public float magicIntensity;
    public float magicWidth;
    public float magicFadeMaxDist;
    public float magicFadeMinDist;

    public EnergyScanFx(Vector3f center, float virtualRadius, Color energyBaseColor, float energyColorIntensity, float energyMixIntensity, float energyWidth, float energyFadeMaxDist, float energyFadeMinDist, Color magicBaseColor, float magicIntensity, float magicWidth, float magicFadeMaxDist, float magicFadeMinDist) {
        this.center = center;
        this.virtualRadius = virtualRadius;
        this.energyBaseColor = energyBaseColor;
        this.energyColorIntensity = energyColorIntensity;
        this.energyMixIntensity = energyMixIntensity;
        this.energyWidth = energyWidth;
        this.energyFadeMaxDist = energyFadeMaxDist;
        this.energyFadeMinDist = energyFadeMinDist;
        this.magicBaseColor = magicBaseColor;
        this.magicIntensity = magicIntensity;
        this.magicWidth = magicWidth;
        this.magicFadeMaxDist = magicFadeMaxDist;
        this.magicFadeMinDist = magicFadeMinDist;
    }

    public EnergyScanFx(Vector3f center) {
        this(
                center,
                0F,
                new Color(.2F, .1F, .4F, 1F),
                .15F,
                .3F,
                300F,
                300F,
                150F,
                new Color(.5F, .25F, 1F, 1F),
                .5F,
                400F,
                300F,
                150F
        );
    }

    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, center.x());
        writer.accept(1, center.y());
        writer.accept(2, center.z());
        writer.accept(3, virtualRadius);

        writer.accept(4, magicBaseColor.getRedAsFloat());
        writer.accept(5, magicBaseColor.getGreenAsFloat());
        writer.accept(6, magicBaseColor.getBlueAsFloat());
        writer.accept(7, magicIntensity);
        writer.accept(8, magicWidth);
        writer.accept(9, magicFadeMaxDist);
        writer.accept(10, magicFadeMinDist);

        writer.accept(11, energyBaseColor.getRedAsFloat());
        writer.accept(12, energyBaseColor.getGreenAsFloat());
        writer.accept(13, energyBaseColor.getBlueAsFloat());
        writer.accept(14, energyColorIntensity);
        writer.accept(15, energyMixIntensity);
        writer.accept(16, energyWidth);
        writer.accept(17, energyFadeMaxDist);
        writer.accept(18, energyFadeMinDist);
    }
}
