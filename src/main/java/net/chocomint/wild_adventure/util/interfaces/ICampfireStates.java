package net.chocomint.wild_adventure.util.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;

public interface ICampfireStates {
	IntProperty getLight(BlockState state);
}
