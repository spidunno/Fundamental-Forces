package com.sammy.fufo.common.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.sammy.fufo.core.data.LangHelpers;
import com.sammy.fufo.core.setup.content.magic.SpellHolderRegistry;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class SpellTypeArgumentType implements ArgumentType<String> {

    public static final SimpleCommandExceptionType INCORRECT_RESULT = new SimpleCommandExceptionType(new TranslatableComponent(LangHelpers.getCommandOutput("error.spell.type.result")));

    public SpellTypeArgumentType() {
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        String read = reader.readUnquotedString();
        if (SpellHolderRegistry.SPELL_TYPES.containsKey(read)) {
            return read;
        }
        throw INCORRECT_RESULT.createWithContext(reader);
    }

    @Override
    public String toString() {
        return "string()";
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (String suggestion : getExamples()) {
            builder = builder.suggest(suggestion);
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return SpellHolderRegistry.SPELL_TYPES.keySet();
    }

    private static String escape(final String input) {
        final StringBuilder result = new StringBuilder("\"");

        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c == '\\' || c == '"') {
                result.append('\\');
            }
            result.append(c);
        }

        result.append("\"");
        return result.toString();
    }
}