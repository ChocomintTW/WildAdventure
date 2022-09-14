package net.chocomint.wild_adventure.util;

import net.chocomint.wild_adventure.WildAdventure;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ModKeyBindings {
	public static final String WILD_ADVENTURE_CATEGORY = "key.categories.wild_adventure";

	public static final KeyBinding RENDER_WATER = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.wild_adventure.render_water", InputUtil.GLFW_KEY_V, WILD_ADVENTURE_CATEGORY));

	public static void register() {
		WildAdventure.LOGGER.info("Registering Mod Key Bindings...");
	}
}
