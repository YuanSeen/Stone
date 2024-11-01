package io.github.yuanseen.stone.block.magic_block;

import io.github.yuanseen.stone.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MagicItemIDToBe {
    public static int getMagicItemID(Item item){
        int id=0;
        if (item.equals(Items.GOLD_BLOCK)) {// 代码块
            id = 1;
        }else
        if (item.equals(ModItems.MAGIC_ITEM_FATHER.get())) {
            id = 64;
        }
        return id;
    }
    public static Item getMagicItem(int id){
        Item item = Items.AIR;
        switch (id){
            case 0:
                item = Items.AIR;
                break;
            case 1:
                item = Items.GOLD_BLOCK;
                break;
            case 64:
                item = ModItems.MAGIC_ITEM_FATHER.get();
                break;
        }
        return item;
    }
}
