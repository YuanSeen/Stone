package io.github.yuanseen.stone.block;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.block.entity.MagicCricleBlockEntity;
import io.github.yuanseen.stone.block.magic_block.MagicCricle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Stone.MOD_ID);

    public static final Supplier<BlockEntityType<MagicCricleBlockEntity>> MAGIC_CRICLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("magic_cricle_block_entity", () ->
                    BlockEntityType.Builder.of(MagicCricleBlockEntity::new, ModBlocks.MAGIC_CIRCLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}