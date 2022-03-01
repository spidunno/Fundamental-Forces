package com.sammy.fundamental_forces.core.systems.option;

import net.minecraftforge.client.event.ScreenEvent;

public interface FundamentalOption {

    public boolean canAdd(ScreenEvent.InitScreenEvent.Post event);
}
