package com.project_esoterica.esoterica.core.systems.option;

import net.minecraftforge.client.event.ScreenEvent;

public interface IEsotericaOption {

    public boolean canAdd(ScreenEvent.InitScreenEvent.Post event);
}
