package com.sammy.fundamental_forces.common.options;

import com.sammy.fundamental_forces.config.ClientConfig;
import com.sammy.fundamental_forces.core.data.SpaceModLang;
import com.sammy.fundamental_forces.core.systems.option.FundamentalOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.ScreenEvent;

public class ScreenshakeOption extends ProgressOption implements FundamentalOption {

    private static final Component TOOLTIP = new TranslatableComponent(SpaceModLang.getOptionTooltip("screenshake_intensity"));

    public ScreenshakeOption() {
        super(SpaceModLang.getOption("screenshake_intensity"),
                0.0D,
                1.0D,
                0F,
                (options) -> ClientConfig.SCREENSHAKE_INTENSITY.get(),
                (options, value) -> ClientConfig.SCREENSHAKE_INTENSITY.set(Math.round(value*100d)/100d),
                (options, progressOption) -> {
                    double value = progressOption.toPct(progressOption.get(options));
                    return value == 0.0D ? progressOption.genericValueLabel(CommonComponents.OPTION_OFF) : progressOption.percentValueLabel(value);
                },
                (minecraft) -> minecraft.font.split(TOOLTIP, 200));
    }

    @Override
    public boolean canAdd(ScreenEvent.InitScreenEvent.Post event) {
        return event.getScreen() instanceof AccessibilityOptionsScreen;
    }
}