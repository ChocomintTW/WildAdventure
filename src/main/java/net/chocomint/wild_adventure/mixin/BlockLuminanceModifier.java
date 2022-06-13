package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.util.interfaces.ICampfireStates;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.ToIntFunction;

@Mixin(Blocks.class)
public class BlockLuminanceModifier {
	@Inject(method = "createLightLevelFromLitBlockState", at = @At("HEAD"), cancellable = true)
	private static void luminanceModifier(int litLevel, CallbackInfoReturnable<ToIntFunction<BlockState>> cir) {
		cir.setReturnValue((state) -> {
			int lit = litLevel;
			if (state.getBlock() instanceof CampfireBlock campfire)
				lit = state.get(((ICampfireStates) campfire).getLight(state));

			return state.get(Properties.LIT) ? lit : 0;
		});
	}
}
