package team.lodestar.fufo.registry.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.fufo.FufoMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.fufo.common.starfall.impact.CharredSoundType;

public class FufoSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FufoMod.FUFO);

    public static final RegistryObject<SoundEvent> CHARRED_BLOCK_MOTIF = register(new SoundEvent(FufoMod.fufoPath("charred_block_motif")));

    public static final SoundType CHARRED_WOOD = new CharredSoundType(1.0F, 0.95f, () -> SoundEvents.WOOD_BREAK, () -> SoundEvents.WOOD_STEP, () -> SoundEvents.WOOD_PLACE, () -> SoundEvents.WOOD_HIT, () -> SoundEvents.WOOD_FALL);
    public static final SoundType CHARRED_DIRT = new CharredSoundType(1.0F, 0.95f, () -> SoundEvents.GRAVEL_BREAK, () -> SoundEvents.GRAVEL_STEP, () -> SoundEvents.GRAVEL_PLACE, () -> SoundEvents.GRAVEL_HIT, () -> SoundEvents.GRAVEL_FALL);
    public static final SoundType CHARRED_SAND = new CharredSoundType(1.0F, 0.95f, () -> SoundEvents.SAND_BREAK, () -> SoundEvents.SAND_STEP, () -> SoundEvents.SAND_PLACE, () -> SoundEvents.SAND_HIT, () -> SoundEvents.SAND_FALL);
    public static final SoundType CHARRED_STONE = new CharredSoundType(1.0F, 0.95f, () -> SoundEvents.STONE_BREAK, () -> SoundEvents.STONE_STEP, () -> SoundEvents.STONE_PLACE, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);

    public static RegistryObject<SoundEvent> register(SoundEvent soundEvent) {
        return SOUNDS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }
}
