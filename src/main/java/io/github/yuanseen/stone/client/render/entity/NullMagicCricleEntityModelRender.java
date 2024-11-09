package io.github.yuanseen.stone.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.client.model.entity.magic.NullMagicCricleEntityModel;
import io.github.yuanseen.stone.client.model.entity.magic.ReturnLockTargetEntityModel;
import io.github.yuanseen.stone.entity.magic.NullMagicCricleEntity;
import io.github.yuanseen.stone.entity.magic.ReturnLockTargetEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;


public class NullMagicCricleEntityModelRender extends EntityRenderer {

    private EntityModel<NullMagicCricleEntity> model;

    public NullMagicCricleEntityModelRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        //这里使用pContext.bakeLayer(FlyingSwordModel.LAYER_LOCATION)来准备ModelPart，这里的获得ModelPart是等会我们需要去注册的，通过LAYER_LOCATION注册我们的ModelPart
        model = new NullMagicCricleEntityModel(pContext.bakeLayer(NullMagicCricleEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.world.entity.Entity pEntity) {
        return new ResourceLocation(Stone.MOD_ID, "textures/entity/null_magic_cricle_entity.png");
    }

    protected int getBlockLightLevel(NullMagicCricleEntity p_174146_, BlockPos p_174147_) {
        return 15;
    }

    //重写了render方法，这个方法定义了实体在游戏中的渲染逻辑。
    @Override
    public void render(net.minecraft.world.entity.Entity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        //你的渲染应该在posh和pop之间，避免污染其他的渲染。
        pPoseStack.pushPose();
        // 绕y轴旋转45°
//        pPoseStack.mulPose(Axis.YN.rotationDegrees(45));
        // 向下移动1格
        pPoseStack.translate(0,-1,0);
        // 构建顶点
        VertexConsumer buffer = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        // 调用模型的render方法进行渲染，这里的OverlayTexture下有很多类型，自己选用。
        this.model.renderToBuffer(pPoseStack,buffer,15, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        pPoseStack.popPose();
    }
}
