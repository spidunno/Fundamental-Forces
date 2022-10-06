package team.lodestar.fufo.common.starfall;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import team.lodestar.fufo.common.starfall.actors.AbstractStarfallActor;
import team.lodestar.fufo.registry.common.worldevent.FufoStarfallActors;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Function;

public class StarfallData {

    @NonNull
    protected final AbstractStarfallActor actor;
    @Nullable
    protected final UUID targetedUUID;
    protected final int startingCountdown;
    protected final boolean looping;
    protected final boolean determined;
    protected final boolean precise;

    public StarfallData(@NotNull AbstractStarfallActor actor, @Nullable UUID targetedUUID, int startingCountdown, boolean looping, boolean determined, boolean precise) {
        this.actor = actor;
        this.targetedUUID = targetedUUID;
        this.startingCountdown = startingCountdown;
        this.looping = looping;
        this.determined = determined;
        this.precise = precise;
    }

    public static StarfallData fromNBT(CompoundTag tag) {
        return new StarfallData(
                FufoStarfallActors.ACTORS.get(tag.getString("actor")),
                tag.getUUID("targetedUUID"),
                tag.getInt("startingCountdown"),
                tag.getBoolean("looping"),
                tag.getBoolean("determined"),
                tag.getBoolean("precise"));
    }

    public void serializeNBT(CompoundTag tag) {
        tag.putString("actor", actor.id);
        if (targetedUUID != null) {
            tag.putUUID("targetedUUID", targetedUUID);
        }
        tag.putInt("startingCountdown", startingCountdown);
        tag.putBoolean("looping", looping);
        tag.putBoolean("determined", determined);
        tag.putBoolean("precise", precise);
    }

    public static class StarfallDataBuilder {
        public final AbstractStarfallActor actor;
        @Nullable
        public final UUID targetedUUID;
        public final int startingCountdown;
        public boolean looping;
        public boolean determined;
        public boolean precise;

        public StarfallDataBuilder(AbstractStarfallActor actor, @Nullable UUID targetedUUID, Function<AbstractStarfallActor, Integer> countdownFunction) {
            this(actor,targetedUUID, countdownFunction.apply(actor));
        }
        public StarfallDataBuilder(AbstractStarfallActor actor, @Nullable UUID targetedUUID, int startingCountdown) {
            this.actor = actor;
            this.targetedUUID = targetedUUID;
            this.startingCountdown = startingCountdown;
        }

        public StarfallDataBuilder setLooping() {
            this.looping = true;
            return this;
        }

        public StarfallDataBuilder setDetermined() {
            this.determined = true;
            return this;
        }

        public StarfallDataBuilder setPrecise() {
            this.precise = true;
            return this;
        }

        public StarfallData build() {
            return new StarfallData(actor, targetedUUID, startingCountdown, looping, determined, precise);
        }
    }
}