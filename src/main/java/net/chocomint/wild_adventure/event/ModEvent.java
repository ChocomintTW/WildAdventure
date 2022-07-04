package net.chocomint.wild_adventure.event;

import net.chocomint.wild_adventure.gui.ModHud;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class ModEvent {
	public static void registerClientEvents() {
//		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> new DirectionHud(MinecraftClient.getInstance()).render(matrixStack));
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> new ModHud(MinecraftClient.getInstance()).render(matrixStack));
	}

	public static Set<BlockPos> CAMPFIRES = new HashSet<>();

	public static void registerServerEvents() {
		ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
			if (blockEntity instanceof CampfireBlockEntity campfire) {
				CAMPFIRES.add(campfire.getPos());
			}
		});
		ServerBlockEntityEvents.BLOCK_ENTITY_UNLOAD.register((blockEntity, world) -> {
			if (blockEntity instanceof CampfireBlockEntity campfire) {
				CAMPFIRES.remove(campfire.getPos());
			}
		});

		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity instanceof PlayerEntity player) {
				player.sendMessage(Text.translatable("command.wild_adventure.first_load_1", player.getName(), Text.literal("Chocomint")
						.styled(style -> style.withColor(Formatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chocomint.cf/")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.wild_help.tooltip.mywebsite"))))), false);
				player.sendMessage(Text.translatable("command.wild_adventure.first_load_2", Text.literal("/wild help")
						.styled(style -> style.withColor(Formatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wild help")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.wild_help.tooltip.help"))))), false);
			}
		});
	}
}
