package net.chocomint.wild_adventure.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	private static final int TRANSLATION = 7;

	@ModifyVariable(method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("STORE"), index = 12)
	private int adjustH(int h) {
		return h - TRANSLATION;
	}

	@ModifyVariable(method = "renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V", at = @At("STORE"), index = 6)
	private int adjustExpY(int y) {
		return y - TRANSLATION;
	}

	@ModifyVariable(method = "renderHeldItemTooltip(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("STORE"), index = 5)
	private int adjustTooltipY(int y) {
		return y - TRANSLATION;
	}
}
