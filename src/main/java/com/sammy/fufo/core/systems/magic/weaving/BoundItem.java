package com.sammy.fufo.core.systems.magic.weaving;

import com.sammy.fufo.core.systems.magic.element.MagicBinding;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class BoundItem {
    public Vec3i location;
    public ItemStack stack;
    public HashMap<Direction, MagicBinding> bindings = new HashMap<>();

    public ItemStack getItem() {
        return stack;
    }

    public void setItem(ItemStack input) {
        stack = input;
    }

    public void setPos(Vec3i pos) {
        location = pos;
    }

    public Vec3i getPos() {
        return location;
    }

    public void offset(Direction dir) {
        switch (dir) {
            case UP -> location.offset(0, 1, 0);
            case DOWN -> location.offset(0, -1, 0);
            case EAST -> location.offset(1, 0, 0);
            case WEST -> location.offset(-1, 0, 0);
            case SOUTH -> location.offset(0, 0, 1);
            case NORTH -> location.offset(0, 0, -1);
        }
    }

    public void offset(Vec3i offset) {
        location.offset(offset);
    }

    public void offset(int x, int y, int z) {
        location.offset(x, y, z);
    }

    public HashMap<Direction, MagicBinding> getBindings() {
        return bindings;
    }

    public void addBinding(Direction dir, MagicBinding type) {
        bindings.put(dir, type);
    }

    public ArrayList<MagicBinding> getTypes() {
        return new ArrayList<>(bindings.values());
    }
}