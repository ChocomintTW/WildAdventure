package net.chocomint.wild_adventure.item.custom;

import net.chocomint.wild_adventure.util.interfaces.IPlayerDataSaver;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyDrinkItem extends Item {
	public EnergyDrinkItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("tooltip.wild_adventure.energy_drink"));
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		((IPlayerDataSaver) user).addVitality(20);
		return super.finishUsing(stack, world, user);
	}
}
