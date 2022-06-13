package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.util.interfaces.ICampfireDataSaver;
import net.chocomint.wild_adventure.util.interfaces.ICampfireStates;
import net.chocomint.wild_adventure.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockOnPlacedMixin {
	@Inject(method = "onPlaced", at = @At("HEAD"), cancellable = true)
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		if (state.getBlock() instanceof CampfireBlock && world.getBlockEntity(pos) instanceof CampfireBlockEntity entity) {
			world.setBlockState(pos, state.with(((ICampfireStates) state.getBlock()).getLight(state), 15));
			((ICampfireDataSaver) entity).setBurnTime(Utils.m2t(5));
		}
	}
}
