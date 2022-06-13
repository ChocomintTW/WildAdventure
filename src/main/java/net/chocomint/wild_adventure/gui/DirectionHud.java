package net.chocomint.wild_adventure.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chocomint.wild_adventure.WildAdventure;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DirectionHud extends DrawableHelper {
	private static final Identifier TEXTURE = new Identifier(WildAdventure.MOD_ID, "textures/gui/hud/direction.png");
	private static final int WIDTH = 140;
	private static final int HEIGHT = 20;
	private final int scaledWidth;
	private final MinecraftClient client;

	public DirectionHud(MinecraftClient client) {
		this.client = client;
		this.scaledWidth = client.getWindow().getScaledWidth();
	}

	public void render(MatrixStack matrices) {
		ClientPlayerEntity player = client.player;
		if (player != null && player.getMainHandStack().getItem() == Items.COMPASS)
		{
			float yaw = client.player.getYaw();
			int dir = Math.round((yaw + 180) / 360 * 256);

			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, TEXTURE);

			this.drawTexture(matrices, (scaledWidth - WIDTH) / 2, 5, dir - WIDTH / 2 + 7, 0, WIDTH, HEIGHT);
			this.drawTexture(matrices, scaledWidth / 2 - 1, 12, 0, 20, 3, 16);
			this.drawTexture(matrices, (scaledWidth - WIDTH) / 2 - 2, 20, 3, 20, 2, 4);
			this.drawTexture(matrices, (scaledWidth + WIDTH) / 2, 20, 5, 20, 2, 4);
		}
	}
}