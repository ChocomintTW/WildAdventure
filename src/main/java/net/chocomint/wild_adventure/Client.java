package net.chocomint.wild_adventure;

import net.chocomint.wild_adventure.command.ModHelpCommand;
import net.chocomint.wild_adventure.event.ModEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ModHelpCommand.register(dispatcher));

		ModEvent.registerClientEvents();
		WildAdventure.LOGGER.info("Client Initialized!");
	}
}
