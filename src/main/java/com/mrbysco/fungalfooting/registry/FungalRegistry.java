package com.mrbysco.fungalfooting.registry;

import com.mojang.serialization.Codec;
import com.mrbysco.fungalfooting.FungalFootingMod;
import com.mrbysco.fungalfooting.effect.FungalFootingEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FungalRegistry {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, FungalFootingMod.MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, FungalFootingMod.MOD_ID);

	public static final Supplier<AttachmentType<Boolean>> INFECTED = ATTACHMENT_TYPES.register("infected", () -> AttachmentType.builder(() -> false)
			.sync(ByteBufCodecs.BOOL).serialize(Codec.BOOL.fieldOf("infected")).build());


	public static final DeferredHolder<MobEffect, MobEffect> FUNGAL_FOOTING = MOB_EFFECTS.register("fungal_footing", () ->
			new FungalFootingEffect(0x0FF6a5d62));
}
