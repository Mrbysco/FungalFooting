package com.mrbysco.fungalfooting.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrbysco.fungalfooting.client.ClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerLikeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.npc.VillagerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.VillagerDataHolderRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class VillagerFungalFootingLayer<S extends LivingEntityRenderState & VillagerDataHolderRenderState, M extends EntityModel<S> & VillagerLikeModel> extends RenderLayer<S, M> {
	public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
	public final BlockModelRenderState leftRenderState = new BlockModelRenderState();
	public final BlockModelRenderState rightRenderState = new BlockModelRenderState();
	private final BlockState leftState;
	private final BlockState rightState;

	public VillagerFungalFootingLayer(RenderLayerParent<S, M> renderer) {
		super(renderer);
		this.leftState = Blocks.RED_MUSHROOM.defaultBlockState();
		this.rightState = Blocks.BROWN_MUSHROOM.defaultBlockState();
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight,
	                   S renderState, float yRot, float xRot) {
		if (renderState.getRenderDataOrDefault(ClientHandler.HAS_FUNGAL_INFECTION, false)) {
			if (!renderState.isInvisible) {
				M parentModel = getParentModel();
				if (parentModel instanceof VillagerModel villagerModel) {
					Minecraft mc = Minecraft.getInstance();
					mc.getBlockModelResolver().update(leftRenderState, leftState, BLOCK_DISPLAY_CONTEXT);
					mc.getBlockModelResolver().update(rightRenderState, rightState, BLOCK_DISPLAY_CONTEXT);

					final ModelPart leftLeg = villagerModel.leftLeg;
					final ModelPart rightLeg = villagerModel.rightLeg;

					renderMushroom(true, leftLeg, poseStack, renderState, nodeCollector, packedLight);
					renderMushroom(false, rightLeg, poseStack, renderState, nodeCollector, packedLight);
				}
			}
		}
	}

	private void renderMushroom(boolean left, ModelPart leg, PoseStack poseStack, S renderState, SubmitNodeCollector nodeCollector, int lightCoords) {
		int overlayCoords = LivingEntityRenderer.getOverlayCoords(renderState, 0.0F);
		BlockModelRenderState blockRenderState = left ? leftRenderState : rightRenderState;
		poseStack.pushPose();

		leg.translateAndRotate(poseStack);

		poseStack.scale(0.5F, 0.5F, 0.5F);

		poseStack.translate(0, 1, 0);
		poseStack.translate(left ? 0.5 : -0.5, 0, 0);

		// Rotate forwards / backwards a bit based on side
		poseStack.mulPose(Axis.XP.rotationDegrees(left ? 20F : -20F));

		// Rotate to stick out horizontally from the side
		poseStack.mulPose(Axis.ZP.rotationDegrees(left ? -75F : 75F));


		// Center the block
		poseStack.translate(-0.5, -0.25, -0.5);

		blockRenderState.submit(poseStack, nodeCollector, lightCoords, overlayCoords, renderState.outlineColor);

		poseStack.popPose();
	}
}
