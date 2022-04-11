package com.sammy.fundamental_forces.core.helper;

import net.minecraft.util.Mth;

import java.util.Random;

/**
 * Contains various math-related helper functions. Often faster than conventional implementations.
 * Forked from https://github.com/CoFH/CoFHCore/blob/dev/src/main/java/cofh/lib/util/helpers/MathHelper.java
 * @author King Lemming
 */
public final class MathHelper {

    public static final Random RANDOM = new Random();
    public static final double PI = Math.PI;
    public static final double PI_2 = Math.PI * 2.0D;
    public static final float F_PI = (float) Math.PI;
    public static final float F_TAU = (float) Math.PI * 2.0F;
    public static final double PHI = 1.618033988749894;
    public static final double TO_DEG = 57.29577951308232;
    public static final double TO_RAD = 0.017453292519943;
    public static final double SQRT_2 = 1.414213562373095;

    public static int binomialDist(int trials, double success) {

        int ret = 0;
        for (int i = 0; i < trials; ++i) {
            if (RANDOM.nextDouble() < success) {
                ++ret;
            }
        }
        return ret;
    }

    public static float approachLinear(float a, float b, float max) {

        return a > b ? a - b < max ? b : a - max : b - a < max ? b : a + max;
    }

    public static double approachLinear(double a, double b, double max) {

        return a > b ? a - b < max ? b : a - max : b - a < max ? b : a + max;
    }

    public static double approachExp(double a, double b, double ratio) {

        return a + (b - a) * ratio;
    }

    public static double approachExp(double a, double b, double ratio, double cap) {

        double d = (b - a) * ratio;

        if (Math.abs(d) > cap) {
            d = Math.signum(d) * cap;
        }
        return a + d;
    }

    public static double retreatExp(double a, double b, double c, double ratio, double kick) {

        double d = (Math.abs(c - a) + kick) * ratio;

        if (d > Math.abs(b - a)) {
            return b;
        }
        return a + Math.signum(b - a) * d;
    }

    public static boolean between(double a, double x, double b) {

        return a <= x && x <= b;
    }

    public static int approachExpI(int a, int b, double ratio) {

        int r = (int) Math.round(approachExp(a, b, ratio));
        return r == a ? b : r;
    }

    public static int retreatExpI(int a, int b, int c, double ratio, int kick) {

        int r = (int) Math.round(retreatExp(a, b, c, ratio, kick));
        return r == a ? b : r;
    }

    public static float sqrt(float a) {

        return (float) Math.sqrt(a);
    }

    public static float invSqrt(float a) {

        return 1 / sqrt(a);
    }

    public static float distSqr(float... a) {
        float d = 0.0F;
        for (float f : a) {
            d += f * f;
        }
        return d;
    }

    public static float dist(float... a) {
        return sqrt(distSqr(a));
    }

    public static float invDist(float... a) {
        return 1 / dist(a);
    }

    public static float bevel(float f) {
        int floor = Mth.floor(f);
        if (f - floor < 0.66667F && (floor & 1) == 0) {
            return (float) -Math.cos(F_PI * 1.5F * f);
        }
        return ((floor >> 1) & 1) == 0 ? 1 : -1;
    }


    public static int setBit(int mask, int bit, boolean value) {
        mask |= (value ? 1 : 0) << bit;
        return mask;
    }

    public static boolean isBitSet(int mask, int bit) {
        return (mask & 1 << bit) != 0;
    }
}