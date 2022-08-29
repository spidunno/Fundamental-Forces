package team.lodestar.fufo.common.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.network.chat.Component;
import team.lodestar.fufo.registry.common.worldevent.FufoStarfallActors;
import team.lodestar.fufo.unsorted.LangHelpers;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class StarfallResultArgumentType implements ArgumentType<String> {

    public static final SimpleCommandExceptionType INCORRECT_RESULT = new SimpleCommandExceptionType(Component.translatable(LangHelpers.getCommandOutput("error.starfall.result")));

    public StarfallResultArgumentType() {
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        String read = reader.readUnquotedString();
        if (FufoStarfallActors.ACTORS.containsKey(read)) {
            return read;
        }
        throw INCORRECT_RESULT.createWithContext(reader);
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
        return FufoStarfallActors.ACTORS.keySet();
    }

}