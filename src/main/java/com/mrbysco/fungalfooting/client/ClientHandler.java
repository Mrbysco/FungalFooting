package com.mrbysco.fungalfooting.client;

import com.google.common.reflect.TypeToken;
import com.mrbysco.fungalfooting.FungalFootingMod;
import com.mrbysco.fungalfooting.client.layer.FungalFootingLayer;
import com.mrbysco.fungalfooting.client.layer.VillagerFungalFootingLayer;
import com.mrbysco.fungalfooting.registry.FungalRegistry;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.VillagerDataHolderRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.PlayerHeartTypeEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	@SubscribeEvent
	public static void onPlayerHeartType(PlayerHeartTypeEvent event) {
		if (event.getEntity().getEffect(FungalRegistry.FUNGAL_FOOTING) != null) {
			event.setType(Gui.HeartType.POISIONED);
		}
	}

	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.AddLayers event) {
		for (EntityType<?> entityType : event.getEntityTypes()) {
			if (entityType != EntityType.PLAYER) {
				EntityRenderer<?, ?> renderer = event.getRenderer(entityType);

				if (renderer instanceof LivingEntityRenderer livingRenderer) {
					var testState = livingRenderer.createRenderState();
					if (testState instanceof HumanoidRenderState) {
						livingRenderer.addLayer(new FungalFootingLayer<>(livingRenderer));
					} else if (testState instanceof VillagerDataHolderRenderState) {
						livingRenderer.addLayer(new VillagerFungalFootingLayer(livingRenderer));
					}
				}
			}
		}

		for (PlayerModelType skin : event.getSkins()) {
			AvatarRenderer<?> avatarRenderer = event.getPlayerRenderer(skin);

			if (avatarRenderer != null) {
				avatarRenderer.addLayer(new FungalFootingLayer<>(avatarRenderer));
			}
		}
	}

	public static final ContextKey<Boolean> HAS_FUNGAL_INFECTION = new ContextKey<>(Identifier.fromNamespaceAndPath(FungalFootingMod.MOD_ID, "has_fungal_infection"));

	@SubscribeEvent
	public static void registerCustomRenderData(RegisterRenderStateModifiersEvent event) {
		event.registerEntityModifier(
				new TypeToken<LivingEntityRenderer<? extends LivingEntity, LivingEntityRenderState, ?>>() {
				},
				ClientHandler::extractRenderState);

	}

	private static void extractRenderState(LivingEntity livingEntity, LivingEntityRenderState livingEntityRenderState) {
		if (livingEntity.hasData(FungalRegistry.INFECTED) && livingEntity.getData(FungalRegistry.INFECTED)) {
			livingEntityRenderState.setRenderData(HAS_FUNGAL_INFECTION, true);
		}
	}
}
