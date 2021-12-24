package com.project_esoterica.esoterica.core.systems.worldgen;

import net.minecraft.world.level.WorldGenLevel;

import java.util.ArrayList;

public class WorldFiller
{
    public ArrayList<FillerEntry> entries = new ArrayList<>();
    public final boolean careful;
    public WorldFiller(boolean careful)
    {
        this.careful = careful;
    }
    public void fill(WorldGenLevel level)
    {
        for (FillerEntry entry : entries)
        {
            if (careful && !entry.canPlace(level))
            {
                continue;
            }
            entry.place(level);
        }
    }
    public void replaceAt(int index, FillerEntry entry)
    {
        entries.set(index, entry);
    }
}