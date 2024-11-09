package assets.stone.textures.entity;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.client.model.entity.CapybaraModel;
import io.github.yuanseen.stone.client.render.entity.layers.CapybaraCarryingItemLayer;
import io.github.yuanseen.stone.entity.CapybaraEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class CapybaraRender extends MobRenderer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {
    private EntityModel<CapybaraEntity> capybaraModel;

    public CapybaraRender(EntityRendererProvider.Context pContext) {
//        super(pContext,new CapybaraModel((pContext.bakeLayer(CapybaraModel.LAYER_LOCATION))),0.3f);
////        super(pContext, new CapybaraModel((pContext.bakeLayer(CapybaraModel.LAYER_LOCATION))), 0.4F);
//        capybaraModel = new CapybaraModel(pContext.bakeLayer(CapybaraModel.LAYER_LOCATION));
//        this.addLayer(new CatCollarLayer(this, p_173943_.getModelSet()));
        super(pContext, new CapybaraModel<CapybaraEntity>(pContext.bakeLayer(CapybaraModel.LAYER_LOCATION)), 0.2F);
//        this.addLayer(new CatCollarLayer(this, pContext.getModelSet()));
        this.addLayer(new CapybaraCarryingItemLayer(this, pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(CapybaraEntity pEntity) {
        return new ResourceLocation(Stone.MOD_ID, "textures/entity/capybara_entity.png");    }

//    protected void scale(CapybaraEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
//        super.scale(pLivingEntity, pPoseStack, pPartialTickTime);
//        pPoseStack.scale(0.8F, 0.8F, 0.8F);
//    }
}
