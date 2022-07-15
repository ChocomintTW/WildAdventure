package net.chocomint.wild_adventure.item.custom;

import net.chocomint.wild_adventure.util.interfaces.IPlayerDataSaver;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyDrinkItem extends Item {
	public EnergyDrinkItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("tooltip.wild_adventure.energy_drink.info1"));
		tooltip.add(Text.translatable("tooltip.wild_adventure.energy_drink.info2"));
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity playerEntity) {
			((IPlayerDataSaver) user).addVitality(20);
			((IPlayerDataSaver) user).addWater(20);
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

			if (!playerEntity.isCreative())
				stack.decrement(1);

			if (!playerEntity.isCreative()) {
				if (stack.isEmpty()) {
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		user.emitGameEvent(GameEvent.DRINK);
		return stack;
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
