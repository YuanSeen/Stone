package io.github.yuanseen.stone.client.model.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.client.animation.FireBallMagicEntityAnimation;
import io.github.yuanseen.stone.entity.magic.FireBallMagicEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class FireBallMagicEntityModel extends HierarchicalModel<FireBallMagicEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Stone.MOD_ID, "fire_ball_magic_entity"), "main");
    private final ModelPart bone;
    private final ModelPart root;

    public FireBallMagicEntityModel(ModelPart root) {
//        super(RenderType::entityCutout);
        this.root = root;
        this.bone = root.getChild("bone");
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

//    public static LayerDefinition createBodyLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//
//        PartDefinition bb_main = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-25.0F, -2.0F, -25.0F, 50.0F, 2.0F, 50.0F, new CubeDeformation(-0.8F))
//                .texOffs(0, 53).addBox(-25.0F, -3.0F, -25.0F, 50.0F, 2.0F, 50.0F, new CubeDeformation(-0.8F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//        return LayerDefinition.create(meshdefinition, 256, 256);

//    }


    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(FireBallMagicEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(pEntity.getBiggerState, FireBallMagicEntityAnimation.getBigger, pAgeInTicks, 1.0F);
        this.animate(pEntity.rollTowardsTheTargetState, FireBallMagicEntityAnimation.rollTowardsTheTarget, pAgeInTicks, 1.0F);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 12).addBox(-9.5F, -1.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 12).addBox(-5.5F, -2.0F, -8.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 16).addBox(2.5F, -1.0F, -9.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 18).addBox(8.5F, -1.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 18).addBox(8.5F, -1.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-3.5F, -1.0F, 8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 20).addBox(-9.5F, -1.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 96).addBox(4.5F, -2.0F, 7.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 22).addBox(7.5F, -2.0F, 4.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 6).addBox(7.5F, -2.0F, -5.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 52).addBox(-5.5F, -2.0F, 7.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 14).addBox(-8.5F, -2.0F, 4.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 30).addBox(4.5F, -3.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 30).addBox(5.5F, -3.0F, 6.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 30).addBox(6.5F, -3.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 92).addBox(6.5F, -3.0F, 5.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 8).addBox(-7.5F, -3.0F, -6.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 10).addBox(-7.5F, -3.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 14).addBox(-5.5F, -3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 32).addBox(-5.5F, -3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 32).addBox(-2.5F, -3.0F, -9.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 34).addBox(4.5F, -3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 34).addBox(4.5F, -3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(6.5F, -3.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 54).addBox(8.5F, -3.0F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(36, 4).addBox(-5.5F, -3.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 6).addBox(-6.5F, -3.0F, 6.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 36).addBox(-7.5F, -3.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 66).addBox(-7.5F, -3.0F, 5.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 32).addBox(-9.5F, -3.0F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(36, 24).addBox(4.5F, -4.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 26).addBox(6.5F, -4.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 36).addBox(7.5F, -4.0F, 3.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 38).addBox(-7.5F, -4.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 38).addBox(-6.5F, -4.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 38).addBox(-5.5F, -4.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 38).addBox(-5.5F, -4.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 14).addBox(4.5F, -4.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 40).addBox(4.5F, -4.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 18).addBox(6.5F, -4.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 22).addBox(-5.5F, -4.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 40).addBox(-7.5F, -4.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 24).addBox(-7.5F, -4.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 40).addBox(8.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 30).addBox(6.5F, -5.0F, -5.5F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(22, 32).addBox(-7.5F, -5.0F, -5.5F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(36, 42).addBox(-6.5F, -5.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 44).addBox(-5.5F, -5.0F, -7.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 24).addBox(5.5F, -5.0F, -6.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 84).addBox(-3.5F, -5.0F, 7.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 76).addBox(-6.5F, -5.0F, 5.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 78).addBox(4.5F, -6.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 46).addBox(5.5F, -6.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 38).addBox(6.5F, -6.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(38, 44).addBox(7.5F, -6.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0).addBox(-6.5F, -6.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 2).addBox(-5.5F, -6.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 12).addBox(-1.5F, -6.0F, -8.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 48).addBox(-4.5F, -6.0F, -7.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 24).addBox(4.5F, -6.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 26).addBox(5.5F, -6.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 32).addBox(-4.5F, -6.0F, 6.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(47, 32).addBox(-5.5F, -5.0F, 6.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(91, 98).addBox(-5.5F, -6.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 34).addBox(-6.5F, -6.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 40).addBox(-7.5F, -6.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(4, 50).addBox(4.5F, -7.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 42).addBox(5.5F, -7.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(24, 46).addBox(6.5F, -7.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(84, 78).addBox(-6.5F, -7.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(44, 50).addBox(-5.5F, -7.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 50).addBox(-4.5F, -7.0F, -6.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 2).addBox(-2.5F, -7.0F, -7.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 14).addBox(4.5F, -7.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 18).addBox(-2.5F, -7.0F, 6.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 90).addBox(-4.5F, -7.0F, 5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 52).addBox(-5.5F, -7.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 48).addBox(-7.5F, -7.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(44, 28).addBox(4.5F, -8.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(40, 48).addBox(5.5F, -8.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(52, 52).addBox(-2.5F, -8.0F, -6.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 54).addBox(-4.5F, -8.0F, -5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 94).addBox(-2.5F, -8.0F, 5.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 54).addBox(-4.5F, -8.0F, 4.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 46).addBox(-5.5F, -8.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(-4, 52).addBox(-6.5F, -8.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(8, 56).addBox(-1.5F, -9.0F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 54).addBox(4.5F, -9.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 56).addBox(-1.5F, -9.0F, -5.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 94).addBox(-3.5F, -9.0F, -4.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 28).addBox(-3.5F, -9.0F, 3.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 34).addBox(-5.5F, -9.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 92).addBox(-0.5F, -10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 56).addBox(-0.5F, -10.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 48).addBox(2.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 56).addBox(-3.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 8).addBox(-0.5F, -10.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 26).addBox(-0.5F, -10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 14).addBox(-3.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 16).addBox(2.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 98).addBox(-1.5F, -6.0F, 7.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 20).addBox(-8.5F, -6.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(60, 26).addBox(-0.5F, -4.0F, 8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 28).addBox(-9.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(49, 92).addBox(-4.5F, -9.0F, -3.5F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(64, 32).addBox(-4.5F, -8.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 50).addBox(0.5F, -8.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 34).addBox(3.5F, -8.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 38).addBox(3.5F, -8.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 42).addBox(-4.5F, -8.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 44).addBox(-8.5F, -2.0F, -5.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 46).addBox(2.5F, -1.0F, 8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 58).addBox(7.5F, -5.0F, -3.5F, 1.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(12, 68).addBox(4.5F, -2.0F, -8.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 42).addBox(-2.5F, -3.0F, 8.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 68).addBox(4.5F, 1.0F, 6.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 46).addBox(6.5F, 1.0F, 4.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 60).addBox(-7.5F, 1.0F, -5.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 68).addBox(-6.5F, -3.0F, -7.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 66).addBox(-5.5F, 1.0F, -7.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 70).addBox(4.5F, 1.0F, -7.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 72).addBox(5.5F, -3.0F, -7.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 0).addBox(6.5F, -3.0F, -6.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 72).addBox(-5.5F, 1.0F, 6.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 10).addBox(-7.5F, 1.0F, 4.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 72).addBox(-0.5F, 2.0F, 8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(100, 22).addBox(3.5F, -4.0F, 7.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(100, 22).addBox(-4.5F, -4.0F, 7.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 94).addBox(-8.5F, -4.0F, -4.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(66, 36).addBox(-8.5F, -5.0F, -3.5F, 1.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(72, 44).addBox(-6.5F, 2.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 50).addBox(-4.5F, -4.0F, -8.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 72).addBox(3.5F, -5.0F, -8.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 94).addBox(7.5F, -4.0F, -4.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(100, 26).addBox(-2.5F, 2.0F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 74).addBox(-7.5F, 2.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 74).addBox(-8.5F, -4.0F, 3.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 74).addBox(-0.5F, 2.0F, -9.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 74).addBox(-0.5F, -4.0F, -9.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 74).addBox(8.5F, 2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-9.5F, 2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 74).addBox(3.5F, 3.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 0).addBox(5.5F, -5.0F, 5.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 74).addBox(6.5F, 3.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 2).addBox(-7.5F, 3.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 12).addBox(-6.5F, 3.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 16).addBox(-4.5F, 3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 18).addBox(-4.5F, 3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(70, 34).addBox(-3.5F, -5.0F, -8.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 36).addBox(3.5F, 3.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 42).addBox(6.5F, 1.0F, -5.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 96).addBox(6.5F, 3.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 46).addBox(-4.5F, 3.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 48).addBox(-7.5F, 3.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 100).addBox(-1.5F, 4.0F, 7.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 64).addBox(-4.5F, 4.0F, 6.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 6).addBox(4.5F, 4.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 66).addBox(5.5F, 4.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(74, 74).addBox(7.5F, 4.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 78).addBox(-8.5F, 4.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 78).addBox(-6.5F, 4.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 78).addBox(-5.5F, 4.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 78).addBox(-4.5F, 4.0F, -7.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 78).addBox(-1.5F, 4.0F, -8.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 80).addBox(4.5F, 4.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 0).addBox(5.5F, 4.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 0).addBox(6.5F, 4.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(96, 10).addBox(-5.5F, 4.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 8).addBox(-6.5F, 4.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 72).addBox(-7.5F, 4.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(80, 16).addBox(-8.5F, 4.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 18).addBox(-8.5F, 4.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 0).addBox(-8.5F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 22).addBox(-0.5F, 5.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 80).addBox(-1.5F, 5.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 24).addBox(0.5F, 5.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 26).addBox(1.5F, 5.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 80).addBox(4.5F, 5.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 38).addBox(-7.5F, 5.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(100, 48).addBox(-7.5F, 5.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 6).addBox(-6.5F, 5.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(80, 40).addBox(-5.5F, 5.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 44).addBox(-2.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 46).addBox(-2.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 80).addBox(-1.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 48).addBox(-1.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 50).addBox(0.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 80).addBox(0.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 52).addBox(1.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 54).addBox(1.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(49, 80).addBox(-4.5F, 5.0F, -6.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 58).addBox(4.5F, 5.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 88).addBox(5.5F, 5.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(96, 46).addBox(6.5F, 5.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(80, 62).addBox(-2.5F, 5.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 18).addBox(-4.5F, 5.0F, 5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 80).addBox(-5.5F, 5.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 68).addBox(-7.5F, 5.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 70).addBox(-7.5F, 5.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 80).addBox(-7.5F, 5.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 72).addBox(-7.5F, 5.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 74).addBox(-0.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 76).addBox(-7.5F, 5.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 96).addBox(-2.5F, 6.0F, 5.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 74).addBox(4.5F, 6.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(48, 96).addBox(-6.5F, 6.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(88, 16).addBox(-5.5F, 6.0F, -4.5F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(36, 82).addBox(-4.5F, 6.0F, -5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 82).addBox(-2.5F, 6.0F, -6.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 48).addBox(5.5F, 6.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(80, 82).addBox(-4.5F, 6.0F, 4.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(84, 14).addBox(-4.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 84).addBox(3.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 84).addBox(3.5F, 6.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 84).addBox(-4.5F, 6.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 84).addBox(-1.5F, 7.0F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 36).addBox(3.5F, 7.0F, -3.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(82, 44).addBox(4.5F, 7.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(78, 42).addBox(-4.5F, 7.0F, -3.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(50, 84).addBox(-1.5F, 7.0F, -5.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(82, 62).addBox(-5.5F, 7.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(-8, 80).addBox(-3.5F, 7.0F, -4.5F, 7.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(92, 42).addBox(-0.5F, -10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 34).addBox(-0.5F, -10.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 88).addBox(2.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 42).addBox(-3.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 76).addBox(-2.5F, -10.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(88, 76).addBox(-0.5F, -10.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 44).addBox(-0.5F, -10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 82).addBox(-3.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 84).addBox(2.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 92).addBox(-0.5F, 8.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 92).addBox(-0.5F, 8.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 90).addBox(2.5F, 8.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 90).addBox(-3.5F, 8.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 82).addBox(-2.5F, 8.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(92, 50).addBox(-0.5F, 8.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 18).addBox(-3.5F, 8.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 20).addBox(2.5F, 8.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 14.0F, 0.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings

// Paste this class into your mod and generate all required imports

