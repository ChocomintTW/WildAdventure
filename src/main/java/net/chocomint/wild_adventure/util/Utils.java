package net.chocomint.wild_adventure.util;

import net.chocomint.wild_adventure.WildAdventure;
import net.chocomint.wild_adventure.util.annotations.Author;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.biome.Biome;

import java.util.Objects;

public class Utils {
	public static Biome biome(LivingEntity player) {
		return player.getWorld().getBiomeAccess().getBiome(player.getBlockPos()).value();
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

	public static double temperature(LivingEntity player) {
		float t = biome(player).getTemperature();
		double base = Math.log(t + 1.85) / Math.log(1.011) - 62;
		if (player.getY() >= 200) {
			base -= (player.getY() - 200) / 50;
		}
		return base;
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
