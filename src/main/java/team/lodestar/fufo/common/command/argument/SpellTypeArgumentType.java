package team.lodestar.fufo.common.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import team.lodestar.fufo.core.data.LangHelpers;
import team.lodestar.fufo.core.setup.content.magic.SpellRegistry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SpellTypeArgumentType implements ArgumentType<ResourceLocation> {

    public static final SimpleCommandExceptionType INCORRECT_RESULT = new SimpleCommandExceptionType(new TranslatableComponent(LangHelpers.getCommandOutput("error.spell.type.result")));

    public SpellTypeArgumentType() {
    }

    @Override
    public ResourceLocation parse(final StringReader reader) throws CommandSyntaxException {
        ResourceLocation read = ResourceLocation.read(reader);
        if (SpellRegistry.SPELL_TYPES.containsKey(read)) {
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
        return SpellRegistry.SPELL_TYPES.keySet().stream().map(ResourceLocation::toString).collect(Collectors.toList());
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