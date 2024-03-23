package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.util.Interator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IStackPredicate {
    default int getTextureIndex(int count) {
        return count / 16;
    }

    default int getTextureIndex(ItemStack itemStack) {
        return Math.min(getTextureIndex(StackLevel.get(itemStack)), getTextureIndex(StackLevel.getMax(itemStack)));
    }

    default Model getBaseModel() {
        return Models.GENERATED;
    }

    default float getProgress(ItemStack stack) {
        return getTextureIndex(stack) / (float) getTextureIndex(StackLevel.getMax(stack));
    }

    static String textureIndexToAppendix(int textureIndex) {
        if (textureIndex < 1) return "";

        return String.format("_%02d", textureIndex);
    }

    static String stackAppendix(ItemStack itemStack) {

        if (itemStack.getItem() instanceof IStackPredicate iStackPredicate) {
            var textureIndex = iStackPredicate.getTextureIndex(itemStack);

            return textureIndexToAppendix(textureIndex);
        }

        return "";

    }

    static Interator interator(Item item) {
        if (!(item instanceof IStackPredicate iStackPredicate)) return new Interator(0);
        if (!(item instanceof IArtifact iArtifact)) return new Interator(0);

        var maxIndex = iStackPredicate.getTextureIndex(iArtifact.getArtifactMaxStack());

        new Interator(5).forEach(System.out::println);

        return new Interator(maxIndex + 1);
    }
}
