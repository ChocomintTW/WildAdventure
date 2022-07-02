package net.chocomint.wild_adventure.util;

import net.chocomint.wild_adventure.WildAdventure;
import net.chocomint.wild_adventure.event.ModEvent;
import net.chocomint.wild_adventure.util.annotations.Author;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.world.RaycastContext.FluidHandling;
import static net.minecraft.world.RaycastContext.ShapeType;

public class Utils {
	public static RegistryEntry<Biome> biomeKey(LivingEntity player) {
		return player.getWorld().getBiomeAccess().getBiome(player.getBlockPos());
	}

	public static Biome biome(LivingEntity player) {
		return biomeKey(player).value();
	}

	public static boolean onlyZ(Vec3d v, double err) {
		return Math.abs(v.getX()) < err && Math.abs(v.getY()) < err && Math.abs(v.getZ()) > err;
	}

	public static String t2ms(int tick) {
		double sec = (double) tick / 20;
		int min = (int) Math.floor(sec / 60);
		int seconds = (int) Math.floor(sec - min * 60);
		return min + ":" + (seconds < 10 ? "0" : "") + seconds;
	}

	public static int s2t(int sec) {
		return sec * 20;
	}

	public static int m2t(int min) {
		return min * 60 * 20;
	}

	public static int ms2t(int min, int sec) {
		return m2t(min) + s2t(sec);
	}

	public static <E extends LivingEntity> GameMode getGameMode(E player) {
		return player instanceof ServerPlayerEntity sp ? sp.interactionManager.getGameMode() : null;
	}

	public static final Map<RegistryKey<Biome>, Double> TIME_FACTOR_MAP = new HashMap<>(Map.ofEntries(
			new AbstractMap.SimpleEntry<>(BiomeKeys.DESERT, 6.0),
			new AbstractMap.SimpleEntry<>(BiomeKeys.JUNGLE, 2.0)
	));

	public static double temperature(LivingEntity player) {
		// biome
		final RegistryEntry<Biome> biomeKey = biomeKey(player);
		final float t = biomeKey.value().getTemperature();
		double base = Math.log(t + 1.85) / Math.log(1.011) - 62;
		final double heatField = getHeatField(player, base);

		// height
		if (player.getY() >= 200) {
			base -= (player.getY() - 200) / 50;
		}

		// time
		long time = player.getWorld().getTimeOfDay();
		double timeFactor = 4;
		if (biomeKey.getKey().isPresent())
			timeFactor = TIME_FACTOR_MAP.getOrDefault(biomeKey.getKey().get(), 4.0);

		base += timeFactor * Math.sin(2 * Math.PI / 24000 * (time - 2000));

		// near campfire
		base += heatField;

		return base;
	}

	private static final double CAMPFIRE_TEMPERATURE = 80;
	private static final double DECREASE_FACTOR = 0.8;

	public static double getHeatField(LivingEntity player, double temperature) {
		AtomicReference<Double> max = new AtomicReference<>(0.0);
		Vec3d playerPos = player.getPos();
		ModEvent.CAMPFIRES.stream().filter(pos -> Vec3d.ofCenter(pos).distanceTo(playerPos) < 30)
				.filter(pos -> player.getWorld().raycast(new RaycastContext(Vec3d.ofCenter(pos).add(playerPos.subtract(Vec3d.ofCenter(pos)).normalize()), player.getPos(), ShapeType.OUTLINE, FluidHandling.ANY, player)).getType() != HitResult.Type.BLOCK)
				.forEach(pos -> {
					double t = (CAMPFIRE_TEMPERATURE - temperature) * Math.exp(-DECREASE_FACTOR * Vec3d.ofCenter(pos).distanceTo(player.getPos()));
					if (t > max.get()) max.set(t);
				});
		return max.get();
	}

	public static int hunger(ItemStack stack) {
		return Objects.requireNonNull(stack.getItem().getFoodComponent()).getHunger();
	}

	public static MutableText string2Text(String s) {
		return MutableText.of(new LiteralTextContent(s));
	}

	public static MutableText translationKey2Text(String key) {
		return MutableText.of(new TranslatableTextContent(key));
	}

	public static String getAuthor() {
		return WildAdventure.class.getAnnotation(Author.class).value();
	}
}
