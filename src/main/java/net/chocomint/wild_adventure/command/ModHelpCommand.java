package net.chocomint.wild_adventure.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModHelpCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("wild").then(CommandManager.literal("help").executes(context -> {
			context.getSource().sendFeedback(Text.translatable("command.wild_adventure.help.start_title").formatted(Formatting.AQUA), false);
			context.getSource().sendFeedback(Text.translatable("command.wild_adventure.help.start_info1"), false);
			context.getSource().sendFeedback(Text.translatable("command.wild_adventure.help.start_info2", Text.translatable("item.minecraft.stick")
					.styled(style -> style.withColor(Formatting.GREEN).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.wild_help.tooltip.stick").formatted(Formatting.YELLOW))))), false);
			context.getSource().sendFeedback(Text.translatable("command.wild_adventure.help.start_info3"), false);
			return 1;
		})));
	}
}
