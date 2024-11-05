package io.github.yuanseen.stone.client.model.entity.magic;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.entity.magic.ReturnLockTargetEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ReturnLockTargetEntityModel extends EntityModel<ReturnLockTargetEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Stone.MOD_ID, "return_lock_target_entity"), "main");
	private final ModelPart bb_main;

	public ReturnLockTargetEntityModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 0).addBox(-1.0F, -13.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.6F))
		.texOffs(8, 5).addBox(-2.0F, -14.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(ReturnLockTargetEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		//body的三维旋转角度，分别对应于X轴（pLimbSwing）、Y轴（pNetHeadYaw）和Z轴（pHeadPitch）的旋转。
		bb_main.xRot = pLimbSwing*pAgeInTicks;
		bb_main.yRot = pNetHeadYaw;
		bb_main.zRot = pHeadPitch;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}