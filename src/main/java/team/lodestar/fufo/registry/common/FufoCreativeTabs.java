package team.lodestar.fufo.registry.common;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static team.lodestar.fufo.FufoMod.FUFO;
import static team.lodestar.fufo.FufoMod.REGISTRATE;


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
            return REGISTRATE.get().get("crack", Item.class).get().getDefaultInstance();
        }
    }
}