package com.sammy.fundamental_forces.core.data;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import com.sammy.fundamental_forces.core.setup.content.item.ItemRegistry;
import com.sammy.fundamental_forces.core.setup.content.item.ItemTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


public class SpaceModItemTags extends ItemTagsProvider {
    public SpaceModItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, FundamentalForcesMod.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Space Mod Item Tags";
    }

    @Override
    protected void addTags() {
        tag(ItemTagRegistry.METEOR_FLAME_CATALYST).add(ItemRegistry.ORTUSITE_CHUNK.get());
    }
}