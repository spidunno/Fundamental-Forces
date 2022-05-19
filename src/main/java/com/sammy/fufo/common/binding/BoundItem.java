package com.sammy.fufo.common.binding;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class BoundItem {
    public static Vec3i location;
    public static ItemStack stack;
    public static HashMap<Direction, String> bindings = new HashMap<>();

    public static ItemStack getItem() {
        return stack;
    }

    public void setItem(ItemStack input){
        stack = input;
    }

    public void setPos(Vec3i pos){
        location = pos;
    }

    public static Vec3i getPos(){
        return location;
    }
    public void offset(String axis, int i){
        switch (axis.toUpperCase()) {
            case "X" -> location.offset(i, 0, 0);
            case "Y" -> location.offset(0, i, 0);
            case "Z" -> location.offset(0, 0, i);
            default -> {
            }
        }
    }
    public void offset(Vec3i offset){
        location.offset(offset);
    }
    public void offset(int x, int y, int z){
        location.offset(x,y,z);
    }
    public HashMap<Direction, String> getBindings(){
        return bindings;
    }
    public void addBinding(Direction dir, String type){
        bindings.put(dir, type);
    }
    public ArrayList<String> getTypes(){
        ArrayList<String> types = new ArrayList<>();
        bindings.forEach((key, value) -> {
            types.add(value);
        });
        return types;
    }
    public ArrayList<Direction> getDirections() {
        ArrayList<Direction> dirs = new ArrayList<>();
        bindings.forEach((key, value) -> {
            dirs.add(key);
        });
        return dirs;
    }

}
