package net.chocomint.wild_adventure.effect;

import net.chocomint.wild_adventure.WildAdventure;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {

	public static StatusEffect WATER_SHORTAGE = Registry.register(Registry.STATUS_EFFECT,
			new Identifier(WildAdventure.MOD_ID, "water_shortage"),
			new ModEffect(StatusEffectCategory.HARMFUL, 2998260).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
					"b5cf6356-269a-45e0-a390-b99bf72aa8f6", -0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

	public static void registerModEffect() {
		WildAdventure.LOGGER.info("Registering Mod Effects");
	}
}
