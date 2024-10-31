package io.github.yuanseen.stone.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {
    //查看物品是否含有名为keyName的nbt
    public static boolean hasNbt(ItemStack itemStack, String keyName) {
        return !itemStack.isEmpty() && itemStack.getTag() != null && itemStack.getTag().contains(keyName);
    }

    public static boolean hasNbt(Entity entity, String keyName) {
        return !entity.isAlive() && entity.getTags() != null && entity.getTags().contains(keyName);
    }

    private static void initCompoundNBT(ItemStack itemStack) {
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }
    }

    //得到物品一个名为keyName的nbt
    public static CompoundTag getNbt(ItemStack stack, String keyName) {
        initCompoundNBT(stack);

        if (!stack.getTag().contains(keyName)) {
            putNbt(stack, keyName, new CompoundTag());
        }

        return stack.getTag().getCompound(keyName);
    }

    public static void putNbt(ItemStack stack, String keyName, CompoundTag compound) {
        initCompoundNBT(stack);

        stack.getTag().put(keyName, compound);
    }

    public static void removeNbt(ItemStack stack, String keyName) {
        if (stack.getTag() != null) {
            stack.getTag().remove(keyName);
        }
    }

}
