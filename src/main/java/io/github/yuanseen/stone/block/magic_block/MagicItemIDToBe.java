package io.github.yuanseen.stone.block.magic_block;

import io.github.yuanseen.stone.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MagicItemIDToBe {
    public static int getMagicItemID(Item item){
        int id=0;
        if (item.equals(Items.GOLD_BLOCK)) {//金
            id = 1;
        }
        else if (item.equals(Items.BAMBOO)) {//木
            id = 2;
        }
        else if (item.equals(ModItems.MAGIC_ITEM_FATHER.get())) {//水
            id = 3;
        }
        else if (item.equals(Items.BLAZE_POWDER)) {//火
            id = 4;
        }
        else if (item.equals(Items.CLAY_BALL)) {//土
            id = 5;
        }
//        else if (item.equals(ModItems.MAGIC_ITEM_FATHER.get())) {
//            id = 64;
//        }
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
            case 2:
                item = Items.BAMBOO;
                break;
            case 3:
                item = ModItems.MAGIC_ITEM_FATHER.get();
                break;
            case 4:
                item = Items.BLAZE_POWDER;
                break;
            case 5:
                item = Items.CLAY_BALL;
                break;
        }
        return item;
    }
}
