package net.chocomint.wild_adventure.util.interfaces;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IPlayerDataSaver {
	float getWater();
	void setWater(float value);
	void addWater(float value);

	float getVitality();
	void setVitality(float value);
	void addVitality(float value);

	List<BlockPos> getNearCampfires();
}
