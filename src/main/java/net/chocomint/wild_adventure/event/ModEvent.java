package net.chocomint.wild_adventure.event;

import net.chocomint.wild_adventure.gui.ModHud;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.client.MinecraftClient;
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
	}
}
