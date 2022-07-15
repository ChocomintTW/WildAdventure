package net.chocomint.wild_adventure.util;

import net.minecraft.util.TranslatableOption;
import net.minecraft.util.math.MathHelper;

public enum TemperatureScale implements TranslatableOption {
	CELSIUS(0, "options.temperature.scale.celsius", "°C"),
	FAHRENHEIT(1, "options.temperature.scale.fahrenheit", "°F");

	private final int id;
	private final String translationKey;
	private final String unitSymbol;

	TemperatureScale(int id, String translationKey, String unitSymbol) {
		this.id = id;
		this.translationKey = translationKey;
		this.unitSymbol = unitSymbol;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getTranslationKey() {
		return translationKey;
	}

	public String getUnitSymbol() {
		return unitSymbol;
	}

	public static TemperatureScale byId(int id) {
		return values()[MathHelper.floorMod(id, values().length)];
	}
}
