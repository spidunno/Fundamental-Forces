package team.lodestar.fufo.common.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.registry.common.magic.FufoSpellTypes;
import team.lodestar.fufo.unsorted.LangHelpers;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SpellTypeArgumentType implements ArgumentType<ResourceLocation> {

    public static final SimpleCommandExceptionType INCORRECT_RESULT = new SimpleCommandExceptionType(Component.translatable(LangHelpers.getCommandOutput("error.spell.type.result")));

    public SpellTypeArgumentType() {
    }

    @Override
    public ResourceLocation parse(final StringReader reader) throws CommandSyntaxException {
        ResourceLocation read = ResourceLocation.read(reader);
        if (FufoSpellTypes.SPELL_TYPES.containsKey(read)) {
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
        return FufoSpellTypes.SPELL_TYPES.keySet().stream().map(ResourceLocation::toString).collect(Collectors.toList());
    }
}