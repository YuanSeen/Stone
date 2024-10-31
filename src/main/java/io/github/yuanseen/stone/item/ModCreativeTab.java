package io.github.yuanseen.stone.item;

import io.github.yuanseen.stone.Stone;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Stone.MOD_ID);

    public static final String STONE_MOD_TAB_STRING = "creativetab.stone_tab";

//    public static final Supplier<CreativeModeTab> EXAMPLE_TAB  = CREATIVE_MODE_TABS.register("example_tab",() -> CreativeModeTab.builder()
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .title(Component.translatable(EXAMPLE_MOD_TAB_STRING))
//            .icon(()->ModItems.RUBY.get().getDefaultInstance())
//            .displayItems((pParameters, pOutput) -> {
//                pOutput.accept(ModItems.RUBY.get());
//                pOutput.accept(ModItems.RUBY_BLOCK.get());
//            })
//            .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
