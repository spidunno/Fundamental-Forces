package team.lodestar.fufo.core.setup.content.magic;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.entity.magic.spell.tier1.SpellBolt;
import team.lodestar.fufo.core.registratation.BlockRegistrate;
import team.lodestar.fufo.core.systems.magic.element.MagicBinding;
import team.lodestar.fufo.core.systems.magic.element.MagicElement;
import team.lodestar.fufo.core.systems.magic.spell.SpellCooldown;
import team.lodestar.fufo.core.systems.magic.spell.SpellInstance;
import team.lodestar.fufo.core.systems.magic.spell.SpellType;
import team.lodestar.fufo.core.systems.magic.spell.attributes.cast.InstantCastMode;
import team.lodestar.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import team.lodestar.fufo.core.systems.magic.spell.attributes.effect.PlaceSpellEffect;
import team.lodestar.fufo.core.systems.magic.spell.attributes.effect.ProjectileEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.function.Function;

public class SpellRegistry {

    public static final HashMap<ResourceLocation, MagicElement> ELEMENTS = new HashMap<>(); //TODO: create inner classes for all of these, containing registry entries for each registry
    public static final HashMap<ResourceLocation, MagicBinding> BINDING_TYPES = new HashMap<>();
    public static final HashMap<ResourceLocation, SpellType> SPELL_TYPES = new HashMap<>();
    public static final HashMap<ResourceLocation, SpellType> SPELL_EFFECTS = new HashMap<>();
    public static final HashMap<ResourceLocation, SpellCastMode> CAST_MODES = new HashMap<>();

    public static final MagicElement FORCE = registerElement(new MagicElement(FufoMod.fufoPath("force")));
    public static final MagicElement FIRE = registerElement(new MagicElement(FufoMod.fufoPath("fire")));
    public static final MagicElement WATER = registerElement(new MagicElement(FufoMod.fufoPath("water")));
    public static final MagicElement EARTH = registerElement(new MagicElement(FufoMod.fufoPath("earth")));
    public static final MagicElement AIR = registerElement(new MagicElement(FufoMod.fufoPath("air")));

    public static final SpellType EMPTY = registerSpellHolder(new SpellType(FufoMod.fufoPath("empty"), SpellInstance::new, (h) -> null));

    public static final SpellCastMode INSTANT = registerCastMode(FufoMod.fufoPath("instant"), InstantCastMode::new);

    public static final SpellType FORCE_BOLT = registerSpellHolder(new SpellType(FufoMod.fufoPath("force_bolt"), basicInstance(INSTANT,FORCE),basicCooldown(50), new ProjectileEffect(SpellBolt::new).duration(100)));
    public static final SpellType FORCE_ORB  = registerSpellHolder(new SpellType(FufoMod.fufoPath("force_orb") , basicInstance(INSTANT,FORCE),basicCooldown(50), new PlaceSpellEffect(BlockRegistrate.FORCE_ORB)));
    protected static MagicElement registerElement(MagicElement element) {
        ELEMENTS.put(element.id, element);
        registerBinding(new MagicBinding(element));
        return element;
    }

    protected static MagicBinding registerBinding(MagicBinding binding) {
        BINDING_TYPES.put(binding.id, binding);
        return binding;
    }

    private static SpellType registerSpellHolder(SpellType spellType) {
        SPELL_TYPES.put(spellType.id, spellType);
        return spellType;
    }

    public static SpellCastMode registerCastMode(ResourceLocation location, Function<ResourceLocation, SpellCastMode> function) {
        SpellCastMode castMode = function.apply(location);
        CAST_MODES.put(location, castMode);
        return castMode;
    }

    public static Function<SpellType, SpellInstance> basicInstance(SpellCastMode mode, MagicElement element){
        return (h) -> new SpellInstance(h, mode, element);
    }
    public static Function<SpellInstance, SpellCooldown> basicCooldown(int duration){
        return (h) -> new SpellCooldown(duration);
    }
}