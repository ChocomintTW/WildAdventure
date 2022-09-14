package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.util.interfaces.IGameOption;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionScreenMixin {
	@Inject(method = "getOptions", at = @At("RETURN"), cancellable = true)
	private static void appendOption(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
		cir.setReturnValue(ArrayUtils.addAll(cir.getReturnValue(),
				((IGameOption) gameOptions).getTemperatureScale(),
				((IGameOption) gameOptions).getWaterRenderType()
		));
	}
}
