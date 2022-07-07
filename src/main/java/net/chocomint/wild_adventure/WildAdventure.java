package net.chocomint.wild_adventure;

import net.chocomint.wild_adventure.effect.ModEffects;
import net.chocomint.wild_adventure.event.ModEvent;
import net.chocomint.wild_adventure.item.ModItems;
import net.chocomint.wild_adventure.util.ModLootTableModifier;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WildAdventure implements ModInitializer {
	public static final String MOD_ID = "wild_adventure";
	public static final Logger LOGGER = LogManager.getLogger("Wild Adventure");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEffects.registerModEffect();
		ModEvent.registerServerEvents();
		ModLootTableModifier.modifyLootTables();

		WildAdventure.LOGGER.info("Welcome to Wild Adventure!");
	}
}
