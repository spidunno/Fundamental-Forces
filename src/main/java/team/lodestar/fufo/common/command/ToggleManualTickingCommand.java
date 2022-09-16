package team.lodestar.fufo.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.fluid.FluidPipeNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ToggleManualTickingCommand {
	public ToggleManualTickingCommand() {
	}
	
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("togglemanual").requires(cs -> cs.hasPermission(2)).executes(context -> {	
			FluidPipeNetwork.MANUAL_TICKING = !FluidPipeNetwork.MANUAL_TICKING;
			Minecraft.getInstance().player.displayClientMessage(Component.literal("Network ticking is now " + (FluidPipeNetwork.MANUAL_TICKING ? "MANUAL" : "AUTOMATIC")), false);
			return 1;
		});
	}
}