package com.project_esoterica.esoterica.core.setup.magic;

import com.project_esoterica.esoterica.common.magic.BlockSpell;
import com.project_esoterica.esoterica.common.magic.ProjectileSpell;
import com.project_esoterica.esoterica.common.magic.spell.tier1.ForceOrb;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;

public class SpellTypeRegistry {

    public static final HashMap<String, SpellType> SPELL_TYPES = new HashMap<>();

    public static final SpellType EMPTY = registerSpellType(new SpellType("empty"));

    public static final SpellType FORCE_BOLT = registerSpellType(new ProjectileSpell("force_bolt", ElementTypeRegistry.FORCE));
    public static final SpellType FORCE_ORB = registerSpellType(new ForceOrb());
    public static final SpellType FORCE_WALL = registerSpellType(new SpellType("force_wall"));
    public static final SpellType FORCE_WAVE = registerSpellType(new SpellType("force_wave"));
    public static final SpellType PERSONAL_SHIELD = registerSpellType(new SpellType("personal_shield"));
    public static final SpellType FORCE_SHRAPNEL = registerSpellType(new SpellType("force_shrapnel"));
    public static final SpellType FORCE_MISSILE = registerSpellType(new SpellType("force_missile"));
    public static final SpellType REPULSION = registerSpellType(new SpellType("repulsion"));
    public static final SpellType REPULSION_AURA = registerSpellType(new SpellType("repulsion_aura"));
    public static final SpellType FORCE_BEAM = registerSpellType(new SpellType("force_beam"));
    public static final SpellType FORCE_SEEKERS = registerSpellType(new SpellType("force_seekers"));

    public static final SpellType FIRE_BOLT = registerSpellType(new ProjectileSpell("fire_bolt", ElementTypeRegistry.FIRE));
    public static final SpellType FIRE_ORB = registerSpellType(new BlockSpell("fire_orb", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellType FIRE_WALL = registerSpellType(new SpellType("fire_wall"));
    public static final SpellType FIRE_WAVE = registerSpellType(new SpellType("fire_wave"));
    public static final SpellType FIREBOMB = registerSpellType(new SpellType("firebomb"));
    public static final SpellType FIREBALL = registerSpellType(new SpellType("fireball"));
    public static final SpellType CONFLAGRATION = registerSpellType(new SpellType("conflagration"));
    public static final SpellType CONFLAGRATION_AURA = registerSpellType(new SpellType("conflagration_aura"));
    public static final SpellType FLAMETHROWER = registerSpellType(new SpellType("flamethrower"));

    public static final SpellType WATER_BOLT = registerSpellType(new ProjectileSpell("water_bolt", ElementTypeRegistry.WATER));
    public static final SpellType WATER_ORB = registerSpellType(new BlockSpell("water_orb", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellType WATER_WALL = registerSpellType(new SpellType("water_wall"));
    public static final SpellType WATER_WAVE = registerSpellType(new SpellType("water_wave"));
    public static final SpellType WAVE_WALL = registerSpellType(new SpellType("wave_wall"));
    public static final SpellType SUFFOCATING_BOLT = registerSpellType(new SpellType("suffocating_bolt"));

    public static final SpellType ROCK_BOLT = registerSpellType(new ProjectileSpell("rock_bolt", ElementTypeRegistry.EARTH));
    public static final SpellType BIG_ROCK = registerSpellType(new BlockSpell("big_rock", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellType ROCK_WALL = registerSpellType(new SpellType("rock_wall"));
    public static final SpellType EARTH_WAVE = registerSpellType(new SpellType("earth_wave"));
    public static final SpellType ROCKBOMB = registerSpellType(new SpellType("rockbomb"));
    public static final SpellType STALAGMITE = registerSpellType(new SpellType("stalagmite"));
    public static final SpellType EARTHQUAKE = registerSpellType(new SpellType("earthquake"));

    public static final SpellType AIR_BOLT = registerSpellType(new ProjectileSpell("air_bolt", ElementTypeRegistry.AIR));
    public static final SpellType AIR_ORB = registerSpellType(new BlockSpell("air_orb", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellType AIR_WALL = registerSpellType(new SpellType("air_wall"));
    public static final SpellType PRESSURE_WAVE = registerSpellType(new SpellType("pressure_wave"));

    private static SpellType registerSpellType(SpellType spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }
}
