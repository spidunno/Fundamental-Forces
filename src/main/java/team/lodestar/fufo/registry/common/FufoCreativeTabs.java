package team.lodestar.fufo.registry.common;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import team.lodestar.fufo.FufoMod;

import static team.lodestar.fufo.FufoMod.FUFO;


public class FufoCreativeTabs {
    public static class FufoContentTab extends CreativeModeTab {
        public static final FufoContentTab INSTANCE = new FufoContentTab();

        public static @NotNull FufoContentTab get() {
            return INSTANCE;
        }

        public FufoContentTab() {
            super(FUFO);
        }

        @Override
        public @NotNull ItemStack makeIcon() {
            return FufoMod.registrate().get("crack", ForgeRegistries.ITEMS.getRegistryKey()).get().getDefaultInstance();
        }
    }
}