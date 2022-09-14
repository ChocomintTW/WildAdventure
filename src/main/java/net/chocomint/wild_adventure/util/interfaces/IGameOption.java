package net.chocomint.wild_adventure.util.interfaces;

import net.chocomint.wild_adventure.util.TemperatureScale;
import net.chocomint.wild_adventure.util.WaterRenderType;
import net.minecraft.client.option.SimpleOption;

public interface IGameOption {
	SimpleOption<TemperatureScale> getTemperatureScale();
	SimpleOption<WaterRenderType> getWaterRenderType();
}
