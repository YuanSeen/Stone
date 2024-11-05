package io.github.yuanseen.stone.event.client;


import io.github.yuanseen.stone.client.model.entity.CapybaraModel;
import io.github.yuanseen.stone.client.model.entity.StoneMobModel;
import io.github.yuanseen.stone.client.model.entity.magic.ReturnLockTargetEntityModel;
import io.github.yuanseen.stone.client.render.entity.CapybaraRender;
import io.github.yuanseen.stone.client.render.entity.ReturnLockTargetEntityModelRender;
import io.github.yuanseen.stone.client.render.entity.StoneMobRender;
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
        });
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(StoneMobModel.LAYER_LOCATION, StoneMobModel::createBodyLayer);
        evt.registerLayerDefinition(CapybaraModel.LAYER_LOCATION, CapybaraModel::createBodyLayer);
        evt.registerLayerDefinition(ReturnLockTargetEntityModel.LAYER_LOCATION,ReturnLockTargetEntityModel::createBodyLayer);
    }
}
