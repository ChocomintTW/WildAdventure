package net.chocomint.wild_adventure.mixin;

import com.mojang.serialization.Codec;
import net.chocomint.wild_adventure.util.ModKeyBindings;
import net.chocomint.wild_adventure.util.TemperatureScale;
import net.chocomint.wild_adventure.util.Utils;
import net.chocomint.wild_adventure.util.WaterRenderType;
import net.chocomint.wild_adventure.util.interfaces.IGameOption;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ChatVisibility;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TranslatableOption;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Arrays;

import static net.chocomint.wild_adventure.util.WaterRenderType.ALWAYS_RENDER;
import static net.chocomint.wild_adventure.util.WaterRenderType.KEY_PRESSED;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements IGameOption {
	private SimpleOption<TemperatureScale> temperatureScale;
	private SimpleOption<WaterRenderType> waterRenderType;

	@Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;Ljava/io/File;)V", at = @At("RETURN"))
	public void init(MinecraftClient client, File optionsFile, CallbackInfo ci) {
		this.temperatureScale = new SimpleOption<>(
				"options.temperature_scale",
				SimpleOption.emptyTooltip(),
				SimpleOption.enumValueText(),
				new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(TemperatureScale.values()), Codec.INT.xmap(TemperatureScale::byId, TemperatureScale::getId)),
				TemperatureScale.CELSIUS,
				value -> {}
		);
		this.waterRenderType = new SimpleOption<>(
				"options.water_render_type",
				SimpleOption.emptyTooltip(),
				(optionText, value) -> switch (value) {
					case ALWAYS_RENDER -> ALWAYS_RENDER.getText();
					case KEY_PRESSED -> KEY_PRESSED.getText().copy()
							.append(" (").append(Text.literal(Utils.getKeyName(KeyBindingHelper.getBoundKeyOf(ModKeyBindings.RENDER_WATER))).formatted(Formatting.YELLOW)).append(")");
				},
				new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(WaterRenderType.values()), Codec.INT.xmap(WaterRenderType::byId, WaterRenderType::getId)),
				ALWAYS_RENDER,
				value -> {}
		);
	}

	@Override
	public SimpleOption<TemperatureScale> getTemperatureScale() {
		return temperatureScale;
	}

	@Override
	public SimpleOption<WaterRenderType> getWaterRenderType() {
		return waterRenderType;
	}
}
