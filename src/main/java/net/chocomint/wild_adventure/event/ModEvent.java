package net.chocomint.wild_adventure.event;

import net.chocomint.wild_adventure.gui.ModHud;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class ModEvent {
	public static void registerClientEvents() {
//		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> new DirectionHud(MinecraftClient.getInstance()).render(matrixStack));
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> new ModHud(MinecraftClient.getInstance()).render(matrixStack));
	}

	public static void registerServerEvents() {
	}
}
