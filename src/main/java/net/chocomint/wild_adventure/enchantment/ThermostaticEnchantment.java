package net.chocomint.wild_adventure.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;

public class ThermostaticEnchantment extends Enchantment {
	public ThermostaticEnchantment() {
		super(Rarity.RARE, EnchantmentTarget.ARMOR, ModEnchantments.ALL_ARMOR);
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	protected boolean canAccept(Enchantment other) {
		return super.canAccept(other) && other != Enchantments.FIRE_PROTECTION;
	}
}
