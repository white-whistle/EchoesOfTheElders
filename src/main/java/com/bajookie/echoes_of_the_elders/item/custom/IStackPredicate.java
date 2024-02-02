package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ItemStack;

public interface IStackPredicate {
    default int getTextureIndex(int count) {
        return count / 16;
    }

    default int getTextureIndex(ItemStack itemStack) {
        return getTextureIndex(StackLevel.get(itemStack));
    }

    default Model getBaseModel() {
        return Models.GENERATED;
    }

    default float getProgress(ItemStack stack) {
        return getTextureIndex(stack) / (float) getTextureIndex(StackLevel.getMax(stack));
    }
}
