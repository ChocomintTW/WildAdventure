package net.chocomint.wild_adventure.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;

import java.util.ArrayList;
import java.util.List;

public class CampfiresInWorld extends PersistentState {
	private final List<BlockPos> campfires;

	public CampfiresInWorld(List<BlockPos> list) {
		campfires = list;
	}

	public List<BlockPos> getCampfires() {
		return campfires;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		NbtList list = new NbtList();
		campfires.forEach(blockPos -> {
			NbtCompound pos = new NbtCompound();
			pos.putInt("x", blockPos.getX());
			pos.putInt("y", blockPos.getY());
			pos.putInt("z", blockPos.getZ());
			list.add(pos);
		});
		nbt.put("Campfires", list);
		return nbt;
	}
}
