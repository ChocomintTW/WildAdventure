package net.chocomint.wild_adventure.util;

import net.minecraft.util.TranslatableOption;
import net.minecraft.util.math.MathHelper;

public enum WaterRenderType implements TranslatableOption {
	ALWAYS_RENDER(0, "options.water_render_type.always_render"),
	KEY_PRESSED(1, "options.water_render_type.key_pressed");

	private final int id;
	private final String translationKey;

	WaterRenderType(int id, String translationKey) {
		this.id = id;
		this.translationKey = translationKey;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getTranslationKey() {
		return translationKey;
	}

	public static WaterRenderType byId(int id) {
		return values()[MathHelper.floorMod(id, values().length)];
	}
}
