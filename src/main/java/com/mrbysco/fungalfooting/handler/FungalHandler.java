package com.mrbysco.fungalfooting.handler;

import com.mrbysco.fungalfooting.FungalFootingMod;
import com.mrbysco.fungalfooting.registry.FungalRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class FungalHandler {
	@SubscribeEvent
	public static void onPlayerTick(EntityTickEvent.Pre event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide()) {
			MobEffectInstance effect = livingEntity.getEffect(FungalRegistry.FUNGAL_FOOTING);
			if (effect != null) {
				livingEntity.setData(FungalRegistry.INFECTED, true);
			} else {
				livingEntity.removeData(FungalRegistry.INFECTED);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Pre event) {
		Player player = event.getEntity();
		Level level = player.level();
		if (!level.isClientSide() && level.getGameTime() % level.tickRateManager().tickrate() == 0) {
			MobEffectInstance effect = player.getEffect(FungalRegistry.FUNGAL_FOOTING);
			BlockState blockState = player.getBlockStateOn();
			if (blockState.is(Blocks.MYCELIUM) && player.getRandom().nextInt(5) == 0) {
				if (effect == null) {
					player.addEffect(new MobEffectInstance(FungalRegistry.FUNGAL_FOOTING,
							60 * 20, 0, false, true, true));
				}
			}
			if (effect != null) {
				// Check if near humanoid entities to infect
				if (player.getRandom().nextInt(5) == 0) {
					level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5),
									livingEntity -> livingEntity.is(FungalFootingMod.HUMANOID))
							.forEach(livingEntity -> {
								if (livingEntity.getEffect(FungalRegistry.FUNGAL_FOOTING) == null) {
									livingEntity.addEffect(new MobEffectInstance(FungalRegistry.FUNGAL_FOOTING,
											20 * 20, 0, false, true, true));
								}
							});
				}
			}
		}
	}
}
