package io.github.yuanseen.stone.event.client;


import io.github.yuanseen.stone.client.model.entity.CapybaraModel;
import io.github.yuanseen.stone.client.model.entity.StoneMobModel;
import io.github.yuanseen.stone.client.model.entity.magic.FireBallMagicEntityModel;
import io.github.yuanseen.stone.client.model.entity.magic.NullMagicCricleEntityModel;
import io.github.yuanseen.stone.client.model.entity.magic.ReturnLockTargetEntityModel;
import io.github.yuanseen.stone.client.render.entity.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import io.github.yuanseen.stone.entity.ModEntityTypes;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event){
        event.enqueueWork(()->{

            EntityRenderers.register(ModEntityTypes.STONE_MOB_ENTITY.get(), StoneMobRender::new);
            EntityRenderers.register(ModEntityTypes.CAPYBARA_ENTITY.get(), CapybaraRender::new);
//            register(EntityType.EGG, ThrownItemRenderer::new);
            EntityRenderers.register(ModEntityTypes.CAPYBARAP_POKEBALL_ENTITY.get(),ThrownItemRenderer::new);
            EntityRenderers.register(ModEntityTypes.RETURN_LOCK_TARGET_ENTITY.get(), ReturnLockTargetEntityModelRender::new);
            EntityRenderers.register(ModEntityTypes.NULL_MAGIC_CRICLE_ENTITY.get(), NullMagicCricleEntityModelRender::new);
            EntityRenderers.register(ModEntityTypes.FIRE_BALL_MAGIC_ENTITY.get(), FireBallMagicEntityModelRender::new);
        });
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(StoneMobModel.LAYER_LOCATION, StoneMobModel::createBodyLayer);
        evt.registerLayerDefinition(CapybaraModel.LAYER_LOCATION, CapybaraModel::createBodyLayer);
        evt.registerLayerDefinition(ReturnLockTargetEntityModel.LAYER_LOCATION,ReturnLockTargetEntityModel::createBodyLayer);
        evt.registerLayerDefinition(NullMagicCricleEntityModel.LAYER_LOCATION,NullMagicCricleEntityModel::createBodyLayer);
        evt.registerLayerDefinition(FireBallMagicEntityModel.LAYER_LOCATION,FireBallMagicEntityModel::createBodyLayer);
    }
}
