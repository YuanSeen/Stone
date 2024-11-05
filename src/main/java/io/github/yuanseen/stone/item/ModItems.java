package io.github.yuanseen.stone.item;

import io.github.yuanseen.stone.Stone;
import io.github.yuanseen.stone.item.custom.CapybaraPokeBall;
import io.github.yuanseen.stone.item.custom.MagicItemFather;
import io.github.yuanseen.stone.item.magic_stick.MagicStick1;
import io.github.yuanseen.stone.world.food.ModFoods;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.minecraft.world.item.Rarity.EPIC;
import static net.minecraft.world.item.Rarity.UNCOMMON;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Stone.MOD_ID);

    public static final Supplier<Item> CAPYBARA_POKEBALL = ITEMS.register("capybara_pokeball",() -> new CapybaraPokeBall(new Item.Properties().stacksTo(1)));
//    public static final Item EGG = registerItem("egg", new EggItem(new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> TANGERINE =  ITEMS.register("tangerine",() -> new Item(new Item.Properties().food(ModFoods.TANGERINE)));
    public static final Supplier<Item> GOLDEN_TANGERINE = ITEMS.register("golden_tangerine",() -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.GOLDEN_TANGERINE)));
    public static final Supplier<Item> ENCHANTED_GOLDEN_TANGERINE = ITEMS.register(
            "enchanted_golden_tangerine",() -> new EnchantedGoldenAppleItem(new Item.Properties().rarity(EPIC).food(ModFoods.ENCHANTED_GOLDEN_TANGERINE))
    );
    public static final Supplier<Item> MAGIC_ITEM_FATHER = ITEMS.register("magic_item_father",()->new MagicItemFather(new Item.Properties().stacksTo(64)));
//    public static final Supplier<Item> MAGIC_CIRCLE_ITEM = ITEMS.register("magic_circle_item",()->new BlockItem(ModBlocks.MAGIC_CIRCLE.get(), new Item.Properties()));

    public static final Supplier<Item> MAGIC_STICK1 = ITEMS.register("magic_stick1",()-> new MagicStick1(new Item.Properties().durability(64).rarity(UNCOMMON)));
//    public static final Item TRIDENT = registerItem("trident", new TridentItem(new Item.Properties().durability(250)));

//    public static final Supplier<Item> MAGIC_STICK3 = ITEMS.register("magic_stick3",()-> new MagicStick3(new Item.Properties().durability(64).rarity(EPIC).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
