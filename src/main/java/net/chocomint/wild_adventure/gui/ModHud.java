package net.chocomint.wild_adventure.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chocomint.wild_adventure.WildAdventure;
import net.chocomint.wild_adventure.util.TemperatureScale;
import net.chocomint.wild_adventure.util.interfaces.IGameOption;
import net.chocomint.wild_adventure.util.interfaces.IPlayerDataSaver;
import net.chocomint.wild_adventure.util.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModHud extends DrawableHelper {
	private static final Identifier ICONS = new Identifier(WildAdventure.MOD_ID, "textures/gui/hud/mod_icons.png");
	private final int screenWidth;
	private final int screenHeight;
	private final MinecraftClient client;

	public ModHud(MinecraftClient client) {
		this.client = client;
		this.screenWidth = client.getWindow().getScaledWidth();
		this.screenHeight = client.getWindow().getScaledHeight();
	}

	public void render(MatrixStack matrices) {
		ClientPlayerEntity player = client.player;
		if (player != null && client.interactionManager != null
				&& client.interactionManager.getCurrentGameMode().isSurvivalLike() && player.getOffHandStack().isEmpty()) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, ICONS);

			// water
			int x = screenWidth / 2 - 91 - 6 - 18;
			int y = screenHeight - 38;
			this.drawTexture(matrices, x, y, 0, 0, 18, 26);

			float water = ((IPlayerDataSaver) player).getWater();
			int waterH = Math.round(20 * (water / 100));
			this.drawTexture(matrices, x + 2, y + 4 + (20 - waterH), 18, 20 - waterH, 14, waterH);

			// vitality
			float v = ((IPlayerDataSaver) player).getVitality();
			int vw = Math.round(182 * (v / 100));
			this.drawTexture(matrices, screenWidth / 2 - 91, screenHeight - 32 + 2, 0, 26, 182, 7);
			this.drawTexture(matrices, screenWidth / 2 - 91, screenHeight - 32 + 2, 0, 33, vw, 7);

			// text
			TextRenderer tr = MinecraftClient.getInstance().textRenderer;
			TemperatureScale scale = ((IGameOption) MinecraftClient.getInstance().options).getTemperatureScale().getValue();
			Text t = MutableText.of(new LiteralTextContent(Math.round(Utils.temperature(player)) + scale.getUnitSymbol()));
			tr.draw(matrices, t, x + 9.5f - (float) tr.getWidth(t) / 2, y + 28f, 0xffffff);
		}
	}
}
