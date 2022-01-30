package com.project_esoterica.esoterica.core.setup.magic;

import com.project_esoterica.esoterica.common.magic.spell.BlockSpell;
import com.project_esoterica.esoterica.common.magic.spell.ProjectileSpell;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;

public class SpellTypeRegistry {

    public static final HashMap<String, SpellType> SPELL_TYPES = new HashMap<>();

    public static final SpellType EMPTY = registerSpellType(new SpellType("empty").disableRendering());

    public static final SpellType FORCE_BOLT = registerSpellType(new ProjectileSpell("force_bolt", ElementTypeRegistry.FORCE));
    public static final SpellType FORCE_ORB = registerSpellType(new BlockSpell("force_orb", ()->Blocks.DIAMOND_BLOCK));

    public static final SpellType FIRE_BOLT = registerSpellType(new ProjectileSpell("fire_bolt", ElementTypeRegistry.FIRE));
    public static final SpellType FIRE_ORB = registerSpellType(new BlockSpell("fire_orb", ()->Blocks.DIAMOND_BLOCK));

    public static final SpellType WATER_BOLT = registerSpellType(new ProjectileSpell("water_bolt", ElementTypeRegistry.WATER));
    public static final SpellType WATER_ORB = registerSpellType(new BlockSpell("water_orb", ()->Blocks.DIAMOND_BLOCK));

    public static final SpellType EARTH_BOLT = registerSpellType(new ProjectileSpell("earth_bolt", ElementTypeRegistry.EARTH));
    public static final SpellType EARTH_ORB = registerSpellType(new BlockSpell("boulder", ()->Blocks.DIAMOND_BLOCK));

    public static final SpellType AIR_BOLT = registerSpellType(new ProjectileSpell("air_bolt", ElementTypeRegistry.AIR));
    public static final SpellType AIR_ORB = registerSpellType(new BlockSpell("air_orb", ()->Blocks.DIAMOND_BLOCK));

    private static SpellType registerSpellType(SpellType spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }
}
