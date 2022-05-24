package com.sammy.fufo.core.setup.content.magic;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.magic.spell.tier1.SpellBolt;
import com.sammy.fufo.core.systems.magic.element.MagicBinding;
import com.sammy.fufo.core.systems.magic.element.MagicElement;
import com.sammy.fufo.core.systems.magic.spell.SpellCooldown;
import com.sammy.fufo.core.systems.magic.spell.SpellHolder;
import com.sammy.fufo.core.systems.magic.spell.SpellInstance;
import com.sammy.fufo.core.systems.magic.spell.attributes.SpellTags;
import com.sammy.fufo.core.systems.magic.spell.attributes.effect.ProjectileEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class SpellRegistry {

    public static final HashMap<ResourceLocation, MagicElement> ELEMENTS = new HashMap<>();
    public static final HashMap<ResourceLocation, MagicBinding> BINDING_TYPES = new HashMap<>();
    public static final HashMap<ResourceLocation, SpellHolder> SPELL_TYPES = new HashMap<>();
    public static final HashMap<ResourceLocation, SpellHolder> SPELL_EFFECTS = new HashMap<>();

    public static final MagicElement FORCE = registerElement(new MagicElement(FufoMod.fufoPath("force")));
    public static final MagicElement FIRE = registerElement(new MagicElement(FufoMod.fufoPath("fire")));
    public static final MagicElement WATER = registerElement(new MagicElement(FufoMod.fufoPath("water")));
    public static final MagicElement EARTH = registerElement(new MagicElement(FufoMod.fufoPath("earth")));
    public static final MagicElement AIR = registerElement(new MagicElement(FufoMod.fufoPath("air")));

    public static final SpellHolder EMPTY = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("empty"), SpellInstance::new, (h) -> null));

    //public static final SpellHolder FORCE_BOLT = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("force_bolt"), () -> new SpellInstance(SpellTags.CastModes.INSTANT, SpellTags.EffectModes.BOLT, SpellTags.Elements.FORCE, new List<SpellModifier> = Arrays.asList(SpellModifier.SPEED, SpellModifier.DAMAGE))));
    //public static final SpellHolder FORCE_ORB = registerSpellHolder(new ForceOrb()););
    public static final SpellHolder FORCE_BOLT = registerSpellHolder(new SpellHolder(FufoMod.fufoPath("force_bolt"), (h) -> new SpellInstance(h, SpellTags.CastModes.INSTANT, FORCE), (h) -> new SpellCooldown(100), new ProjectileEffect(SpellBolt::new)));

    protected static MagicElement registerElement(MagicElement element) {
        ELEMENTS.put(element.id, element);
        registerBinding(new MagicBinding(element));
        return element;
    }

    protected static MagicBinding registerBinding(MagicBinding binding) {
        BINDING_TYPES.put(binding.id, binding);
        return binding;
    }

    private static SpellHolder registerSpellHolder(SpellHolder spellHolder) {
        SPELL_TYPES.put(spellHolder.id, spellHolder);
        return spellHolder;
    }
}