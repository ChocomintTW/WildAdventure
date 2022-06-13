package net.chocomint.wild_adventure.item.custom;

import net.chocomint.wild_adventure.util.interfaces.IPlayerDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class HeatedWaterBottleItem extends Item {
	public HeatedWaterBottleItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity) {
			((IPlayerDataSaver) user).addWater(50);
		}
		return super.finishUsing(stack, world, user);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
}
