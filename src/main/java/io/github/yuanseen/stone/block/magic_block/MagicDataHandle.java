package io.github.yuanseen.stone.block.magic_block;

import io.github.yuanseen.stone.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.lang.reflect.Array;
import java.util.Arrays;


public class MagicDataHandle {

    private final static String SI_MAIN_ATTRIBUTE[] = {
            "空间",//0
            "金",//1
            "木",//2
            "水",//3
            "火",//4
            "土",//5
    };

    private final static int SI_MAIN_ATTRIBUTE_NUMBER[] = {
                0,//0
                9,//1
                1,//2
                9,//3
                1,//4
                1,//5
    };

    private final static Item MAGICDATA[] = {
            Items.AIR,//0
            Items.GOLD_BLOCK,//1
            Items.BAMBOO,//2
            ModItems.MAGIC_ITEM_FATHER.get(),//3
            Items.BLAZE_POWDER,//4
            Items.CLAY_BALL,//5
    };

    public static String getItemAttributeName(Item item){
        int id = getMagicItemID(item);
        return SI_MAIN_ATTRIBUTE[id];
    }
    public static String getItemAttributeName(int id){
        return SI_MAIN_ATTRIBUTE[id];
    }
    public static int getItemAttributeNumber(Item item){
        int id = getMagicItemID(item);
        return SI_MAIN_ATTRIBUTE_NUMBER[id];
    }
    public static int getItemAttributeNumber(int id){
        return SI_MAIN_ATTRIBUTE_NUMBER[id];
    }
    public static int getMagicItemID(Item item){
        return Arrays.binarySearch(MAGICDATA,item);
    }
    public static Item getMagicItem(int id){
        return MagicDataHandle.MAGICDATA[id];
    }


}
