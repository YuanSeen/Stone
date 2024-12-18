package io.github.yuanseen.stone.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.client.model.entity.magic.FireBallMagicEntityModel;
import io.github.yuanseen.stone.entity.magic.FireBallMagicEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FireBallMagicEntityModelRender extends EntityRenderer<FireBallMagicEntity> {
    private EntityModel<FireBallMagicEntity> model;

    public FireBallMagicEntityModelRender(EntityRendererProvider.Context pContext) {
        super(pContext);
        //这里使用pContext.bakeLayer(FlyingSwordModel.LAYER_LOCATION)来准备ModelPart，这里的获得ModelPart是等会我们需要去注册的，通过LAYER_LOCATION注册我们的ModelPart
        model = new FireBallMagicEntityModel(pContext.bakeLayer(FireBallMagicEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(FireBallMagicEntity pEntity) {
        return new ResourceLocation(Stone.MOD_ID, "textures/entity/fire_ball_magic_entity.png");
    }

//    @Override
//    public ResourceLocation getTextureLocation(net.minecraft.world.entity.Entity pEntity) {
//        return
//    }

    //重写了render方法，这个方法定义了实体在游戏中的渲染逻辑。
    @Override
    public void render(FireBallMagicEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        //你的渲染应该在posh和pop之间，避免污染其他的渲染。
        pPoseStack.pushPose();
        // 绕y轴旋转45°
//        pPoseStack.mulPose(Axis.YN.rotationDegrees(45));
        // 向下移动1格
        pPoseStack.translate(0,1,0);
        // 构建顶点
        VertexConsumer buffer = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        // 调用模型的render方法进行渲染，这里的OverlayTexture下有很多类型，自己选用。
        this.model.renderToBuffer(pPoseStack,buffer,pPackedLight, OverlayTexture.RED_OVERLAY_V,1f,1f,1f,1f);
        this.model.setupAnim( pEntity,0,0,pEntity.tickCount,0,0);
        pPoseStack.popPose();
    }
}
