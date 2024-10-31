package io.github.yuanseen.stone.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.entity.CapybaraEntity;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class CapybaraModel<T extends CapybaraEntity> extends OcelotModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Stone.MOD_ID, "capybara_model"), "main");

    private static final String TAIL_1 = "tail1";
    private static final String TAIL_2 = "tail2";
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_hind_leg;
    private final ModelPart tail1;
    private final ModelPart tail2;
    protected int state = 1;



    public CapybaraModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_front_leg = root.getChild("left_front_leg");
        this.right_front_leg = root.getChild("right_front_leg");
        this.left_hind_leg = root.getChild("left_hind_leg");
        this.right_hind_leg = root.getChild("right_hind_leg");
        this.tail1 = root.getChild("tail1");
        this.tail2 = root.getChild("tail2");

    }


    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 17.7028F, 3.6815F));

        PartDefinition hind_head_r1 = head.addOrReplaceChild("hind_head_r1", CubeListBuilder.create().texOffs(9, 0).addBox(-1.0F, -0.9513F, -1.0194F, 2.0F, 2.0F, 3.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, 2.1014F, -5.555F, 2.8362F, 0.0F, 3.1416F));

        PartDefinition right_eye_r1 = head.addOrReplaceChild("right_eye_r1", CubeListBuilder.create().texOffs(0, 2).addBox(1.0F, -3.4704F, 0.8701F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(0, 0).addBox(-0.5F, -3.3557F, 3.1311F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.75F))
                .texOffs(0, 8).addBox(-2.0F, -3.4704F, 0.8701F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(1, 17).addBox(1.0F, -5.4599F, -1.3422F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(1, 17).addBox(-2.0F, -5.4599F, -1.3422F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 8).addBox(-1.0F, -3.6599F, -0.4422F, 2.0F, 3.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, 2.1014F, -5.555F, 2.6616F, 0.0F, 3.1416F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.4103F, -1.8477F));

        PartDefinition low_r1 = body.addOrReplaceChild("low_r1", CubeListBuilder.create().texOffs(8, 8).addBox(-1.0F, 0.5957F, -4.4265F, 2.0F, 2.0F, 2.0F, new CubeDeformation(1.0F))
                .texOffs(12, 12).addBox(-1.0F, -0.4043F, -5.4265F, 2.0F, 2.0F, 2.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, -0.6061F, -0.0259F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition big_r1 = body.addOrReplaceChild("big_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.4505F, -4.2225F, 2.0F, 3.0F, 5.0F, new CubeDeformation(1.2F)), PartPose.offsetAndRotation(0.0F, -0.6061F, -0.0259F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition tail1 = partdefinition.addOrReplaceChild("tail1", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -5.0F));

        PartDefinition tail1_r1 = tail1.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(19, 5).addBox(-1.0F, -2.4043F, -7.4265F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.8043F, 3.1265F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition tail2 = partdefinition.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -5.0F));

        PartDefinition tail2_r1 = tail2.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(19, 1).addBox(-1.0F, -2.4043F, -7.4265F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.8043F, 3.1265F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 21.4F, 2.5F));

        PartDefinition right_hind_leg_r1 = right_hind_leg.addOrReplaceChild("right_hind_leg_r1", CubeListBuilder.create().texOffs(0, 10).addBox(1.0F, 1.5957F, -4.4265F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(1.0F, 3.5957F, -4.4265F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.5957F, -4.3735F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 21.2F, 1.5F));

        PartDefinition left_hind_leg_r1 = left_hind_leg.addOrReplaceChild("left_hind_leg_r1", CubeListBuilder.create().texOffs(14, 5).addBox(0.0F, -0.4043F, -0.4265F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 5).addBox(0.0F, 1.5957F, -0.4265F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.6043F, 0.6265F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 21.7F, -0.5F));

        PartDefinition right_front_leg_r1 = right_front_leg.addOrReplaceChild("right_front_leg_r1", CubeListBuilder.create().texOffs(14, 7).addBox(1.0F, 1.5957F, -1.4265F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 7).addBox(1.0F, 3.5957F, -1.4265F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.8957F, -1.3735F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 21.6F, -0.5F));

        PartDefinition left_front_leg_r1 = left_front_leg.addOrReplaceChild("left_front_leg_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, 1.5957F, -1.4265F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-2.0F, 3.5957F, -1.4265F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.7957F, -1.3735F, 3.1416F, 0.0F, 3.1416F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.rightHindLeg.visible = true;
        this.leftHindLeg.visible = true;
        this.rightFrontLeg.visible = true;
        this.leftFrontLeg.visible = true;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_hind_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_hind_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
