package io.github.yuanseen.stone.block;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.block.magic_block.MagicCricle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.github.yuanseen.stone.item.ModItems.ITEMS;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Stone.MOD_ID);

//    public static final Supplier<Block> RUBY_BLOCK = registerBlock("ruby_block", RubyBlock::new);
//    public static final Supplier<Block> MAGIC_CIRCLE = registerBlock("magic_circle", MagicCricle::new);

    public static final Supplier<Block> MAGIC_CIRCLE = registerBlock("magic_circle",()->new MagicCricle(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops()
            ));

    public static Supplier<Block> registerBlock(String name, Supplier<Block> block){
        Supplier<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }
    public static void registerBlockItem(String name, Supplier<Block> block){
        registerBlockItem(name, block, new Item.Properties());
    }
    public static void registerBlockItem(String name, Supplier<Block> block, Item.Properties properties){
        ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }
//    public static final Supplier<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
