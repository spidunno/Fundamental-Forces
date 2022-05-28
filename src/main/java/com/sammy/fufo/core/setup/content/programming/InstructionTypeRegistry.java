package com.sammy.fufo.core.setup.content.programming;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.magic.ProjectileSpell;
import com.sammy.fufo.core.setup.content.magic.ElementTypeRegistry;
import com.sammy.fufo.core.systems.magic.spell.SpellType;
import com.sammy.fufo.core.systems.programming.InstructionType;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstructionTypeRegistry {

    public static final HashMap<ResourceLocation, InstructionType> INSTRUCTION_TYPES = new HashMap<>();
    public static final List<InstructionType> ORDERED_INSTRUCTION_TYPES = new ArrayList<>();

    public static int id = 0;
    private static final ResourceLocation ARM_ATLAS_LOCATION = FufoMod.fufoPath("textures/ui/programming/instructions/arm_instructions.png");

    public static final InstructionType ROTATE_CCW = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_ccw"), "Rotate Anti-Clockwise", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_CW = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_cw"), "Rotate Clockwise", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_HIGHER = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_higher"), "Rotate Higher", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_LOWER = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_lower"), "Rotate Lower", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType MOVE_UP = registerInstruction(new InstructionType(FufoMod.fufoPath("move_up"), "Move Up", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType MOVE_DOWN = registerInstruction(new InstructionType(FufoMod.fufoPath("move_down"), "Move Down", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType RETRACT = registerInstruction(new InstructionType(FufoMod.fufoPath("retract"), "Retract", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType EXTEND = registerInstruction(new InstructionType(FufoMod.fufoPath("extend"), "Extend", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_HELD_CCW = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_held_ccw"), "Rotate Held Anti-Clockwise", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_HELD_CW = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_held_cw"), "Rotate Held Clockwise", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_HELD_UPWARD = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_held_upward"), "Rotate Held Upward", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType ROTATE_HELD_DOWNWARD = registerInstruction(new InstructionType(FufoMod.fufoPath("rotate_held_downward"), "Rotate Held Downward", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType GRAB_OR_RELEASE = registerInstruction(new InstructionType(FufoMod.fufoPath("grab_or_release"), "Grab/Release", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType RESET = registerInstruction(new InstructionType(FufoMod.fufoPath("reset"), "Reset", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType WAIT = registerInstruction(new InstructionType(FufoMod.fufoPath("wait"), "Wait", "", ARM_ATLAS_LOCATION, id++));
    public static final InstructionType REPEAT = registerInstruction(new InstructionType(FufoMod.fufoPath("repeat"), "Repeat", "", ARM_ATLAS_LOCATION, id++));

    private static InstructionType registerInstruction(InstructionType instructionType) {
        ORDERED_INSTRUCTION_TYPES.add(instructionType);
        INSTRUCTION_TYPES.put(instructionType.id, instructionType);
        return instructionType;
    }

}
