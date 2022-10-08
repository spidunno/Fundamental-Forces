package team.lodestar.fufo.registry.common.magic;

import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.entity.magic.spell.tier1.SpellBolt;
import team.lodestar.fufo.common.magic.spell.effects.PlaceSpellEffect;
import team.lodestar.fufo.common.magic.spell.effects.ProjectileEffect;
import team.lodestar.fufo.core.spell.*;
import team.lodestar.fufo.registry.common.FufoBlocks;

import java.util.HashMap;
import java.util.function.Function;

public class FufoSpellTypes {
    public static final HashMap<ResourceLocation, SpellType> SPELL_TYPES = new HashMap<>();

    public static final SpellType EMPTY = registerSpellHolder(new EmptySpellType());

    public static final SpellType FORCE_BOLT = registerSpellHolder(new SpellType(FufoMod.fufoPath("force_bolt"),
            basicInstance(FufoSpellCastModes.INSTANT), basicCooldown(20),
            new ProjectileEffect(SpellBolt::new, FufoMagicElements.FORCE).duration(100)));

    public static final SpellType FORCE_ORB = registerSpellHolder(new SpellType(FufoMod.fufoPath("force_orb"),
            basicInstance(FufoSpellCastModes.INSTANT), basicCooldown(20),
            new PlaceSpellEffect(FufoBlocks.FORCE_ORB, FufoMagicElements.FORCE)));

    public static SpellType registerSpellHolder(SpellType spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }

    public static Function<SpellType, SpellInstance> basicInstance(SpellCastMode mode) {
        return (type) -> new SpellInstance(type, mode);
    }

    public static Function<SpellInstance, SpellCooldown> basicCooldown(int duration) {
        return (h) -> new SpellCooldown(duration);
    }
}