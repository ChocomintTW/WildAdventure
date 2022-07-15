package net.chocomint.wild_adventure.mixin;

import net.chocomint.wild_adventure.effect.ModEffects;
import net.chocomint.wild_adventure.util.Utils;
import net.chocomint.wild_adventure.util.interfaces.IPlayerDataSaver;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerDataSaver {
	int ticker1 = 0, ticker2 = 0;
	boolean notFirstLoad;
	private static final TrackedData<Float> WATER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> VITALITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

//	@Shadow public abstract boolean giveItemStack(ItemStack stack);

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker", at = @At("HEAD"), cancellable = true)
	public void init(CallbackInfo ci) {
		this.dataTracker.startTracking(WATER, 100f);
		this.dataTracker.startTracking(VITALITY, 100f);
	}

	@Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
	public void eat(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		if (stack.isFood()) {
			this.addVitality(Utils.hunger(stack) * 3);

			if (stack.isOf(Items.APPLE) || stack.isOf(Items.CARROT) || stack.isOf(Items.MELON_SLICE)
					|| stack.isOf(Items.GLOW_BERRIES) || stack.isOf(Items.SWEET_BERRIES)) this.addWater(5);
			if (stack.isOf(Items.BEETROOT_SOUP)|| stack.isOf(Items.MUSHROOM_STEW)
					|| stack.isOf(Items.RABBIT_STEW) || stack.isOf(Items.SUSPICIOUS_STEW)) this.addWater(10);
		}
	}

	@Inject(method = "wakeUp(ZZ)V", at = @At("HEAD"), cancellable = true)
	public void wake(boolean skipSleepTimer, boolean updateSleepingPlayers, CallbackInfo ci) {
		this.setVitality(100f);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"), cancellable = true)
	public void read(NbtCompound nbt, CallbackInfo ci) {
		this.setWater(nbt.getFloat("water"));
		this.setVitality(nbt.getFloat("vitality"));
		notFirstLoad = false;
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"), cancellable = true)
	public void write(NbtCompound nbt, CallbackInfo ci) {
		nbt.putFloat("water", this.getWater());
		nbt.putFloat("vitality", this.getVitality());
		nbt.putBoolean("firstLoad", notFirstLoad);
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(CallbackInfo ci) {
		if (!this.getWorld().isClient() && this.isPlayer()) {
			if (!this.notFirstLoad && this.getServer() != null) {
				this.notFirstLoad = true;
			}

			if (Utils.getGameMode(this).isSurvivalLike()) {
				if (!this.isSubmergedInWater() && !this.getWorld().isRaining()) {
					this.addWater(-waterDecrement());
				}

				// 水太少的損血
				if (this.getWater() <= 5) {
					this.addStatusEffect(new StatusEffectInstance(ModEffects.WATER_SHORTAGE, 10, 1));
					ticker1++;
					if (ticker1 > 60) {
						this.heal(-1);
						ticker1 = 0;
					}
				} else this.removeStatusEffect(ModEffects.WATER_SHORTAGE);

				// 溫度太低凍傷

				// ====================================================================================== //

				this.addVitality(-vitalityDecrement());

				float v = this.getVitality();
				if (v <= 10) {
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 3 - (int) v / 3));
				}
				if (v == 0) {
					ticker2++;
					if (ticker2 > 60) {
						this.heal(-1);
						ticker2 = 0;
					}
				}
			}
		}
	}

	private float waterDecrement() {
		Vec3d velocity = this.getVelocity();
		float res = (float) Math.pow(velocity.length() * 10, 2) / 450;

		if (Utils.onlyZ(velocity, 0.1)) return 0;

		// 在除了船的交通工具上不須消耗水量
		if (this.getVehicle() != null) {
			double multiplier = 0;
			if (this.getVehicle() instanceof BoatEntity) multiplier = 0.6;
			if (this.getVehicle() instanceof HorseEntity) multiplier = 0.3;
			res *= multiplier;
		}

		// 鞘翅上可減少消耗
		if (this.isFallFlying()) res *= 0.15;

		// 在溫度高的地方也會額外消耗水量
		double temperature = Utils.temperature(this);
		// 恆溫附魔可以降低溫度消耗水的加成
		double factor = ((double) Utils.thermostaticFactor(this)) / 80;
		if (temperature >= 25) {
			res += 0.03 - factor * 0.02;
			res *= 1 + (temperature - 25) / (60 + factor * 40);
		} else if (temperature <= -10) res *= 0.8;

		// 燒傷加成
		if (this.isOnFire() && !this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE))
			res *= (2 - factor * 0.7);

		return res;
	}

	private float vitalityDecrement() {
		float water = ((IPlayerDataSaver) this).getWater();
		double temperature = Utils.temperature(this);
		float res = (float) Math.pow(this.getVelocity().length() * 10, 2) / 500;

		if (temperature <= -10) {
			res *= 1 - (10 + temperature) / 60;
		}

		// 在除了船的交通工具上不須消耗體力
		if (this.getVehicle() != null) {
			double multiplier = 0;
			if (this.getVehicle() instanceof BoatEntity) multiplier = 0.8;
			if (this.getVehicle() instanceof HorseEntity) multiplier = 0.2;
			res *= multiplier;
		}

		if (this.hasStatusEffect(StatusEffects.HUNGER)) res *= 1.2;

		if (this.isSwimming()) res *= 1.1;

		if (this.isFallFlying()) res *= 1.2;

		return res;
	}

	@Override
	public float getWater() {
		return this.getDataTracker().get(WATER);
	}

	@Override
	public void setWater(float value) {
		this.getDataTracker().set(WATER, value > 100 ? 100 : (value < 0 ? 0 : value));
	}

	@Override
	public void addWater(float value) {
		float w = this.getWater() + value;
		this.setWater(w > 100 ? 100 : (w < 0 ? 0 : w));
	}

	@Override
	public float getVitality() {
		return this.getDataTracker().get(VITALITY);
	}

	@Override
	public void setVitality(float value) {
		this.getDataTracker().set(VITALITY, value > 100 ? 100 : (value < 0 ? 0 : value));
	}

	@Override
	public void addVitality(float value) {
		float v = this.getVitality() + value;
		this.setVitality(v > 100 ? 100 : (v < 0 ? 0 : v));
	}
}
