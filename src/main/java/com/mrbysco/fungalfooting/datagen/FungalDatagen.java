package com.mrbysco.fungalfooting.datagen;

import com.mrbysco.fungalfooting.datagen.client.FungalLanguageProvider;
import com.mrbysco.fungalfooting.datagen.server.FungalEntityTypeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class FungalDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new FungalLanguageProvider(packOutput));
		generator.addProvider(true, new FungalEntityTypeTagsProvider(packOutput, lookupProvider));
	}
}
