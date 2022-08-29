package team.lodestar.fufo.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import team.lodestar.fufo.core.fluid.FluidPipeNetwork;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ToggleManualTickingCommand {
	public ToggleManualTickingCommand() {
	}
	
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("togglemanual").requires(cs -> cs.hasPermission(2)).executes(context -> {	
			FluidPipeNetwork.MANUAL_TICKING = !FluidPipeNetwork.MANUAL_TICKING;
			return 1;
		});
	}
}