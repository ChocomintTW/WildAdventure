package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.util.interfaces.ICampfireDataSaver;
import net.chocomint.wild_adventure.util.interfaces.ICampfireStates;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin extends BlockEntity implements ICampfireDataSaver {
	int burnTime = 0;

	public CampfireBlockEntityMixin(BlockPos pos, BlockState state) {
		super(BlockEntityType.CAMPFIRE, pos, state);
	}

	@Inject(method = "litServerTick", at = @At("HEAD"), cancellable = true)
	private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfireBlockEntity, CallbackInfo ci) {
		ICampfireDataSaver campfire = (ICampfireDataSaver) campfireBlockEntity;
		int time = campfire.getBurnTime();

		if (time < 150)
			world.setBlockState(pos, world.getBlockState(pos).with(((ICampfireStates) state.getBlock()).getLight(state), (int) Math.ceil((double) time / 10)));

		if (time > 0)
			campfire.addBurnTime(-1);
		else
			world.setBlockState(pos, world.getBlockState(pos).with(CampfireBlock.LIT, false));
	}

	@Inject(method = "readNbt", at = @At("HEAD"), cancellable = true)
	public void read(NbtCompound nbt, CallbackInfo ci) {
		this.burnTime = nbt.getInt("burnTime");
	}

	@Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true)
	public void write(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt("burnTime", burnTime);
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public void addBurnTime(int time) {
		burnTime += time;
	}
}
