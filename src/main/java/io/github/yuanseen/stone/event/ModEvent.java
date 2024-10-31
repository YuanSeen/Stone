package io.github.yuanseen.stone.event;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.entity.CapybaraEntity;
import io.github.yuanseen.stone.entity.StoneEntityTypes;
import io.github.yuanseen.stone.entity.StoneMobEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public class ModEvent {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBus {
        @SubscribeEvent
        public static void setupAttributes(EntityAttributeCreationEvent event) {
            event.put(StoneEntityTypes.STONE_MOB_ENTITY.get(), StoneMobEntity.createAttributes().build());
            event.put(StoneEntityTypes.CAPYBARA_ENTITY.get(), CapybaraEntity.createAttributes().build());
        }
    }


}
