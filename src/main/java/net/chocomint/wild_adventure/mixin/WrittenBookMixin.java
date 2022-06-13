package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WrittenBookItem.class)
public class WrittenBookMixin {
	@Inject(method = "getName", at = @At("HEAD"), cancellable = true)
	public void name(ItemStack stack, CallbackInfoReturnable<Text> cir) {
		NbtCompound nbt = stack.getNbt();
		if (nbt != null && nbt.contains("titleKey")) {
			cir.setReturnValue(Utils.translationKey2Text(nbt.getString("titleKey")));
		}
	}
}
