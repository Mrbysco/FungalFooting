package com.mrbysco.fungalfooting;

import com.mojang.logging.LogUtils;
import com.mrbysco.fungalfooting.registry.FungalRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(FungalFootingMod.MOD_ID)
public class FungalFootingMod {
	public static final String MOD_ID = "fungalfooting";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<EntityType<?>> HUMANOID = TagKey.create(Registries.ENTITY_TYPE, modLoc("humanoid"));

	public FungalFootingMod(IEventBus eventBus) {
		FungalRegistry.MOB_EFFECTS.register(eventBus);
		FungalRegistry.ATTACHMENT_TYPES.register(eventBus);
	}

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
