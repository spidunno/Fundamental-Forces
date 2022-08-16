package team.lodestar.fufo.registry.common.magic;

import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.entity.magic.spell.tier1.SpellBolt;
import team.lodestar.fufo.core.element.MagicElement;
import team.lodestar.fufo.core.spell.SpellCooldown;
import team.lodestar.fufo.core.spell.SpellInstance;
import team.lodestar.fufo.core.spell.SpellType;
import team.lodestar.fufo.core.spell.attributes.cast.SpellCastMode;
import team.lodestar.fufo.core.spell.attributes.effect.PlaceSpellEffect;
import team.lodestar.fufo.core.spell.attributes.effect.ProjectileEffect;
import team.lodestar.fufo.registry.common.FufoBlocks;

import java.util.HashMap;
import java.util.function.Function;

public class FufoSpellTypes {
    public static final HashMap<ResourceLocation, SpellType> SPELL_TYPES = new HashMap<>();

    public static final SpellType EMPTY = registerSpellHolder(new SpellType(FufoMod.fufoPath("empty"), SpellInstance::new, (h) -> null));

    public static final SpellType FORCE_BOLT = registerSpellHolder(new SpellType(FufoMod.fufoPath("force_bolt"), createBasicType(FufoSpellCastModes.INSTANT, FufoMagicElements.FORCE), createBasicCooldownProvider(50), new ProjectileEffect(SpellBolt::new).duration(100)));
    public static final SpellType FORCE_ORB = registerSpellHolder(new SpellType(FufoMod.fufoPath("force_orb"), createBasicType(FufoSpellCastModes.INSTANT, FufoMagicElements.FORCE), createBasicCooldownProvider(50), new PlaceSpellEffect(FufoBlocks.FORCE_ORB)));

    public static SpellType registerSpellHolder(SpellType spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }

    public static Function<SpellType, SpellInstance> createBasicType(SpellCastMode mode, MagicElement element) {
        return (h) -> new SpellInstance(h, mode, element);
    }

    public static Function<SpellInstance, SpellCooldown> createBasicCooldownProvider(int duration) {
        return (h) -> new SpellCooldown(duration);
    }
}