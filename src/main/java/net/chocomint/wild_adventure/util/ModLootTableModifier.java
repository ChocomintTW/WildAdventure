package net.chocomint.wild_adventure.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {
	private static final Identifier BONUS_CHEST = new Identifier("minecraft", "chests/spawn_bonus_chest");

	public static void modifyLootTables() {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (id.equals(BONUS_CHEST)) {
				LootPool pool = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1))
					.with(ItemEntry.builder(Items.COAL)).build();
				tableBuilder.pool(pool);
			}
		});
	}
}
