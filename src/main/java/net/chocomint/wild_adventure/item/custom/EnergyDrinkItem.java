package net.chocomint.wild_adventure.item.custom;

import net.chocomint.wild_adventure.util.interfaces.IPlayerDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnergyDrinkItem extends Item {
	public EnergyDrinkItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		((IPlayerDataSaver) user).addVitality(20);
		return super.finishUsing(stack, world, user);
	}
}
