package net.chocomint.wild_adventure.mixin;

import com.mojang.serialization.Codec;
import net.chocomint.wild_adventure.util.TemperatureScale;
import net.chocomint.wild_adventure.util.interfaces.IGameOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ChatVisibility;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Arrays;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements IGameOption {
	private SimpleOption<TemperatureScale> temperatureScale;

	@Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;Ljava/io/File;)V", at = @At("RETURN"))
	public void init(MinecraftClient client, File optionsFile, CallbackInfo ci) {
		this.temperatureScale = new SimpleOption<TemperatureScale>(
				"options.temperature_scale",
				SimpleOption.emptyTooltip(),
				SimpleOption.enumValueText(),
				new SimpleOption.PotentialValuesBasedCallbacks(Arrays.asList(TemperatureScale.values()), Codec.INT.xmap(TemperatureScale::byId, TemperatureScale::getId)),
				TemperatureScale.CELSIUS,
				value -> {}
		);
	}

	@Override
	public SimpleOption<TemperatureScale> getTemperatureScale() {
		return temperatureScale;
	}
}
