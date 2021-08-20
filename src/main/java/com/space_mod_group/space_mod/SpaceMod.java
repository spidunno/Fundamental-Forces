package com.space_mod_group.space_mod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.space_mod_group.space_mod.SpaceMod.MOD_ID;

@Mod(MOD_ID)
public class SpaceMod
{
    public static final String MOD_ID = "space_mod";
    public static final Logger LOGGER = LogManager.getLogger();

    public SpaceMod() {
    }
}
