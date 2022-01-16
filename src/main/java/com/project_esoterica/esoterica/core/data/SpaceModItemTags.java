package com.project_esoterica.esoterica.core.data;

import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.setup.item.ItemRegistry;
import com.project_esoterica.esoterica.core.setup.item.ItemTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


public class SpaceModItemTags extends ItemTagsProvider {
    public SpaceModItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, EsotericaMod.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Space Mod Item Tags";
    }

    @Override
    protected void addTags() {
        tag(ItemTagRegistry.METEOR_FLAME_CATALYST).add(ItemRegistry.ASTEROID_CHUNK.get());
    }
}