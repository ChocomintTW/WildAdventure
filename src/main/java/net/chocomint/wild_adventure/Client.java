package net.chocomint.wild_adventure;

import net.chocomint.wild_adventure.event.ModEvent;
import net.chocomint.wild_adventure.util.annotations.Author;
import net.fabricmc.api.ClientModInitializer;

public class Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModEvent.registerClientEvents();
		WildAdventure.LOGGER.info("Client Initialized!");
	}
}
