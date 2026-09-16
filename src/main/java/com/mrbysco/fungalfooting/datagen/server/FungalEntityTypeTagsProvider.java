package com.mrbysco.fungalfooting.datagen.server;

import com.mrbysco.fungalfooting.FungalFootingMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class FungalEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public FungalEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
		super(output, provider, FungalFootingMod.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(FungalFootingMod.HUMANOID).add(
				EntityType.PLAYER,
				EntityType.ENDERMAN,

				EntityType.PIGLIN,
				EntityType.PIGLIN_BRUTE,
				EntityType.ZOMBIFIED_PIGLIN,

				EntityType.GIANT,
				EntityType.ZOMBIE,
				EntityType.HUSK,
				EntityType.DROWNED,

				EntityType.SKELETON,
				EntityType.BOGGED,
				EntityType.WITHER_SKELETON,

				EntityType.VILLAGER,
				EntityType.ZOMBIE_VILLAGER
		);
	}
}
