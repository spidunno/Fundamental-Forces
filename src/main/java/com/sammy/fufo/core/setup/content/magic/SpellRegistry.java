package com.sammy.fufo.core.setup.content.magic;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.magic.spell.tier1.SpellBolt;
import com.sammy.fufo.core.registratation.BlockRegistrate;
import com.sammy.fufo.core.systems.magic.element.MagicBinding;
import com.sammy.fufo.core.systems.magic.element.MagicElement;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldown;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.SpellType;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.InstantCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.cast.SpellCastMode;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.PlaceSpellEffect;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.ProjectileEffect;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

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