package com.mrbysco.fungalfooting.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

public class FungalFootingEffect extends MobEffect {
	public FungalFootingEffect(int color) {
		super(MobEffectCategory.HARMFUL, color);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		int i = 50 >> amplifier;
		return i <= 0 || duration % i == 0;
	}

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
		if (livingEntity.getHealth() > 1.0F) {
			Registry<DamageType> types = livingEntity.damageSources().damageTypes;
			Holder.Reference<DamageType> damageSource = types.getOrThrow(DamageTypes.MAGIC);
			livingEntity.hurtServer(serverLevel, new DamageSource(damageSource), 1.0F);
		}

		if (livingEntity.getBlockStateOn().is(Blocks.GRASS_BLOCK) && serverLevel.getRandom().nextInt(5) == 0) {
			serverLevel.setBlock(livingEntity.getOnPos(), Blocks.MYCELIUM.defaultBlockState(), 3);

			serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
					livingEntity.getOnPos().getX(), livingEntity.getOnPos().getY() + 1, livingEntity.getOnPos().getZ(),
					10, 0.3F, 0.3F, 0.3F, 0.15F);
		}

		return true;
	}
}
