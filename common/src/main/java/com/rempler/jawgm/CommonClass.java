package com.rempler.jawgm;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CommonClass {
    public static void gainWater(NonNullList<ItemStack> items, int count, Item item) {
        if (count == 1) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).is(item)) {
                    items.set(i, ItemStack.EMPTY);
                    break;
                }
            }
        } else if (count > 1) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).is(item)) {
                    ItemStack itemStack = item.getDefaultInstance();
                    itemStack.setCount(count -1);
                    items.set(i, itemStack.copy());
                    break;
                }
            }
        }
    }
}