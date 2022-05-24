package com.sammy.fufo.core.setup.content.magic;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.magic.spell.tier1.SpellBolt;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldownData;
import com.sammy.fufo.core.systems.magic.spell.SpellHolder;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.attributes.SpellTags;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.ProjectileEffect;
import com.sammy.fufo.core.systems.magic.spell.attributes.modifiers.SpellModifier;

import java.util.HashMap;

public class SpellHolderRegistry {

    public static final HashMap<String, SpellHolder> SPELL_TYPES = new HashMap<>();

    public static final SpellHolder EMPTY = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("empty"), () -> new SpellInstance(SpellTags.CastModes.INSTANT)));

    /**
     * new SpellInstance(CastModes.Charge, new FireProjectileEffect(ProjectileToFire)).setElement(FORCE);
     * new SpellInstance(CastModes.Charge, new ConflagAuraEffect());
     */
    //public static final SpellHolder FORCE_BOLT = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("force_bolt"), () -> new SpellInstance(SpellTags.CastModes.INSTANT, SpellTags.EffectModes.BOLT, SpellTags.Elements.FORCE, new List<SpellModifier> = Arrays.asList(SpellModifier.SPEED, SpellModifier.DAMAGE))));
    //public static final SpellHolder FORCE_ORB = registerSpellHolder(new ForceOrb()););
    public static final SpellHolder FORCE_BOLT = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("force_bolt"), () -> new SpellInstance(SpellTags.CastModes.INSTANT, new ProjectileEffect(SpellBolt::new), () -> new SpellCooldownData(100))));
    public static final SpellHolder FORCE_WALL = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("force_wall"), () -> new SpellInstance(SpellTags.CastModes.INSTANT, SpellTags.EffectModes.WALL, )));
    public static final SpellHolder FORCE_WAVE = registerSpellHolder(new SpellHolder("force_wave"));
    public static final SpellHolder PERSONAL_SHIELD = registerSpellHolder(new SpellHolder("personal_shield"));
    public static final SpellHolder FORCE_SHRAPNEL = registerSpellHolder(new SpellHolder("force_shrapnel"));
   // public static final SpellHolder FORCE_MISSILE = registerSpellHolder(new ForceBolt());
    public static final SpellHolder REPULSION = registerSpellHolder(new SpellHolder("repulsion"));
    public static final SpellHolder REPULSION_AURA = registerSpellHolder(new SpellHolder("repulsion_aura"));
    public static final SpellHolder FORCE_BEAM = registerSpellHolder(new SpellHolder("force_beam"));
    public static final SpellHolder FORCE_SEEKERS = registerSpellHolder(new SpellHolder("force_seekers"));

    //public static final SpellHolder FIRE_BOLT = registerSpellHolder(new ProjectileSpell("fire_bolt", ElementTypeRegistry.FIRE, projectileSupplier));
    //public static final SpellHolder FIRE_ORB = registerSpellHolder(new BlockSpell("fire_orb", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellHolder FIRE_WALL = registerSpellHolder(new SpellHolder("fire_wall"));
    public static final SpellHolder FIRE_WAVE = registerSpellHolder(new SpellHolder("fire_wave"));
    public static final SpellHolder FIREBOMB = registerSpellHolder(new SpellHolder("firebomb"));
   // public static final SpellHolder FIREBALL = registerSpellHolder(new FireBolt());
    public static final SpellHolder CONFLAGRATION = registerSpellHolder(new SpellHolder("conflagration"));
    public static final SpellHolder CONFLAGRATION_AURA = registerSpellHolder(new SpellHolder("conflagration_aura"));
    public static final SpellHolder FLAMETHROWER = registerSpellHolder(new SpellHolder("flamethrower"));

    //public static final SpellHolder WATER_BOLT = registerSpellHolder(new ProjectileSpell("water_bolt", ElementTypeRegistry.WATER, projectileSupplier));
    //public static final SpellHolder WATER_ORB = registerSpellHolder(new BlockSpell("water_orb", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellHolder WATER_WALL = registerSpellHolder(new SpellHolder("water_wall"));
    public static final SpellHolder WATER_WAVE = registerSpellHolder(new SpellHolder("water_wave"));
    public static final SpellHolder WAVE_WALL = registerSpellHolder(new SpellHolder("wave_wall"));
    public static final SpellHolder WATER_CUTTER = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("water_cutter"), () -> new WaterCutter()));
    public static final SpellHolder SUFFOCATING_BOLT = registerSpellHolder(new SpellHolder("suffocating_bolt"));

    //public static final SpellHolder ROCK_BOLT = registerSpellHolder(new ProjectileSpell("rock_bolt", ElementTypeRegistry.EARTH, projectileSupplier));
    //public static final SpellHolder BIG_ROCK = registerSpellHolder(new BlockSpell("big_rock", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellHolder ROCK_WALL = registerSpellHolder(new SpellHolder("rock_wall"));
    public static final SpellHolder EARTH_WAVE = registerSpellHolder(new SpellHolder("earth_wave"));
    public static final SpellHolder ROCKBOMB = registerSpellHolder(new SpellHolder("rockbomb"));
    public static final SpellHolder STALAGMITE = registerSpellHolder(new SpellHolder("stalagmite"));
    public static final SpellHolder EARTHQUAKE = registerSpellHolder(new SpellHolder("earthquake"));

    //public static final SpellHolder AIR_BOLT = registerSpellHolder(new ProjectileSpell("air_bolt", ElementTypeRegistry.AIR, projectileSupplier));
    //public static final SpellHolder AIR_ORB = registerSpellHolder(new BlockSpell("air_orb", ()->Blocks.DIAMOND_BLOCK));
    public static final SpellHolder AIR_WALL = registerSpellHolder(new SpellHolder("air_wall"));
    public static final SpellHolder PRESSURE_WAVE = registerSpellHolder(new SpellHolder("pressure_wave"));

    private static SpellHolder registerSpellHolder(SpellHolder SpellHolder) {
        SPELL_TYPES.put(SpellHolder.id, SpellHolder);
        return SpellHolder;
    }
}
