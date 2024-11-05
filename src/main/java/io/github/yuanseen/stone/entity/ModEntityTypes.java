package io.github.yuanseen.stone.entity;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.entity.magic.ReturnLockTargetEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Stone.MOD_ID);
    public static final Supplier<EntityType<StoneMobEntity>> STONE_MOB_ENTITY = ENTITY_TYPES.register("stone_mob_entity", () -> EntityType.Builder.of(StoneMobEntity::new, MobCategory.MISC).sized(2, 0.5F).build("stone_mob_entity"));
    public static final Supplier<EntityType<CapybaraEntity>> CAPYBARA_ENTITY = ENTITY_TYPES.register("capybara_entity", () -> EntityType.Builder.of(CapybaraEntity::new, MobCategory.CREATURE).sized(0.7F, 0.5F).build("capybara_entity"));
    public static final Supplier<EntityType<ThrownCapybaraPokeBall>> CAPYBARAP_POKEBALL_ENTITY = ENTITY_TYPES.register("capybara_pokeball_entity",() -> EntityType.Builder.<ThrownCapybaraPokeBall>of(ThrownCapybaraPokeBall::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("capybara_pokeball_entity")
    );
    public static final Supplier<EntityType<ReturnLockTargetEntity>> RETURN_LOCK_TARGET_ENTITY = ENTITY_TYPES.register("return_lock_target_entity",() -> EntityType.Builder.<ReturnLockTargetEntity>of(ReturnLockTargetEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("return_lock_target_entity")
    );


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }


}
