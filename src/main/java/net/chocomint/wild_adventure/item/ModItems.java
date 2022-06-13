package net.chocomint.wild_adventure.item;

import net.chocomint.wild_adventure.WildAdventure;
import net.chocomint.wild_adventure.item.custom.HeatedWaterBottleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

	public static final Item HEATED_WATER = registerItem("heated_water",
			new HeatedWaterBottleItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE)
					.food(new FoodComponent.Builder().alwaysEdible().build()).maxCount(8).group(ItemGroup.FOOD)));

	public static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(WildAdventure.MOD_ID, name), item);
	}

	public static void registerModItems() {
		WildAdventure.LOGGER.info("Registering Mod Items");
	}
}
