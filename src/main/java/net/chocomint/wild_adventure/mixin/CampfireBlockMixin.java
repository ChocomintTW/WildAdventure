package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.util.interfaces.ICampfireDataSaver;
import net.chocomint.wild_adventure.util.interfaces.ICampfireStates;
import net.chocomint.wild_adventure.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin implements ICampfireStates {
	private static final IntProperty LIGHT = IntProperty.of("light", 0, 15);

	@Inject(method = "appendProperties", at = @At("HEAD"), cancellable = true)
	public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
		builder.add(LIGHT);
	}

	@Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
	                  Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		if (!world.isClient()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);

			if (blockEntity instanceof CampfireBlockEntity campfireBlockEntity) {

				ItemStack stack = player.getStackInHand(hand);
				if (stack.isOf(Items.STICK)) {
					((ICampfireDataSaver) campfireBlockEntity).addBurnTime(stack.getCount() * Utils.s2t(10));
					player.setStackInHand(hand, ItemStack.EMPTY);
					player.sendMessage(Utils.string2Text(Utils.t2ms(((ICampfireDataSaver) campfireBlockEntity).getBurnTime())).formatted(Formatting.AQUA), true);

				} else if (stack.isOf(Items.POTION) && stack.getNbt() != null && stack.getNbt().getString("Potion").equals("minecraft:water")) {
					campfireBlockEntity.addItem(player, stack, 200);
					player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
				}
			}
		}
	}

	@Override
	public IntProperty getLight(BlockState state) {
		return LIGHT;
	}
}
