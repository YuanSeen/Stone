package io.github.yuanseen.stone.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.client.model.entity.StoneMobModel;
import io.github.yuanseen.stone.entity.StoneMobEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;


public class StoneMobRender extends MobRenderer<StoneMobEntity,StoneMobModel<StoneMobEntity>> {
    private EntityModel<StoneMobEntity> stoneMobModel;

    public StoneMobRender(EntityRendererProvider.Context pContext) {
        super(pContext,new StoneMobModel((pContext.bakeLayer(StoneMobModel.LAYER_LOCATION))),0.3f);
        //这里使用pContext.bakeLayer(FlyingSwordModel.LAYER_LOCATION)来准备ModelPart，这里的获得ModelPart是等会我们需要去注册的，通过LAYER_LOCATION注册我们的ModelPart
        stoneMobModel = new StoneMobModel(pContext.bakeLayer(StoneMobModel.LAYER_LOCATION));
    }
    //这个方法返回一个ResourceLocation对象，指明了飞行剑实体的纹理文件位置。
    @Override
    public ResourceLocation getTextureLocation(StoneMobEntity pEntity) {
        return new ResourceLocation(Stone.MOD_ID, "textures/entity/capybara_entity.png");
    }


}
