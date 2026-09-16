package com.mrbysco.fungalfooting.datagen.client;

import com.mrbysco.fungalfooting.FungalFootingMod;
import com.mrbysco.fungalfooting.registry.FungalRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FungalLanguageProvider extends LanguageProvider {
	public FungalLanguageProvider(PackOutput packOutput) {
		super(packOutput, FungalFootingMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addEffect(FungalRegistry.FUNGAL_FOOTING, "Athlete's Foot");
		addEffectDescription(FungalRegistry.FUNGAL_FOOTING, "You have a fungal infection");
	}

	public void addSubtitle(Supplier<SoundEvent> sound, String name) {
		this.addSubtitle(sound.get(), name);
	}

	public void addSubtitle(SoundEvent sound, String name) {
		String path = FungalFootingMod.MOD_ID + ".subtitle." + sound.location().getPath();
		this.add(path, name);
	}

	private void addEffectDescription(Supplier<? extends MobEffect> key, String description) {
		add(key.get().getDescriptionId() + ".description", description);
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(FungalFootingMod.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(FungalFootingMod.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
