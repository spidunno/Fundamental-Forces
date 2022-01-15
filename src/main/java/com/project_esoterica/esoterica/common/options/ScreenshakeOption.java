package com.project_esoterica.esoterica.common.options;

import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ScreenshakeOption extends ProgressOption {

    private static final Component ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT = new TranslatableComponent("options.screenEffectScale.tooltip");
    public ScreenshakeOption() {
        super("options.screenEffectScale",
                0.0D,
                1.0D,
                0.0F,
                (options) -> (double) options.screenEffectScale, //getter
                (options, value) -> options.screenEffectScale = value.floatValue(),
                (options, progressOption) -> {
                    double value = progressOption.toPct(progressOption.get(options));
                    return new TextComponent("" + value);}, //option value text
                (minecraft) -> minecraft.font.split(ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT, 200)); //option tooltip
    }
}
