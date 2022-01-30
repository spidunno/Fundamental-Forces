package com.project_esoterica.esoterica.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.esoterica.common.capability.PlayerDataCapability;
import com.project_esoterica.esoterica.common.command.argument.SpellTypeArgumentType;
import com.project_esoterica.esoterica.core.data.SpaceModLang;
import com.project_esoterica.esoterica.core.setup.magic.SpellTypeRegistry;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellInstance;
import com.project_esoterica.esoterica.core.systems.magic.spell.SpellType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class SetSpellCommand {
    public SetSpellCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("setspell")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("type", new SpellTypeArgumentType())
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("slot", IntegerArgumentType.integer(0, 8))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            Player target = EntityArgument.getPlayer(context, "target");
                                            PlayerDataCapability.getCapability(target).ifPresent(p -> {
                                                SpellType result = SpellTypeRegistry.SPELL_TYPES.get(context.getArgument("type", String.class));
                                                int slot = context.getArgument("slot", Integer.class);
                                                p.hotbarHandler.spellHotbar.spells.set(slot, new SpellInstance(result));
                                                PlayerDataCapability.sync(target);
                                                source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("set_spell_success")), true);
                                            });
                                            return 1;
                                        }))));
    }
}