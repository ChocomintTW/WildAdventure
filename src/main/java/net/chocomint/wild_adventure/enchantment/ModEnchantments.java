package net.chocomint.wild_adventure.enchantment;

import net.chocomint.wild_adventure.WildAdventure;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {

	public static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

	public static final Enchantment THERMOSTATIC = register("thermostatic", new ThermostaticEnchantment());

	private static Enchantment register(String name, Enchantment enchantment) {
		return Registry.register(Registry.ENCHANTMENT, new Identifier(WildAdventure.MOD_ID, name), enchantment);
	}

	public static void registerModEnchantments() {
		WildAdventure.LOGGER.info("Register Mod Enchantments");
	}
}
