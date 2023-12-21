package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class GodslayerItem extends Item implements IArtifact, IHasUpscaledModel {

    public GodslayerItem() {
        super(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public String getUpscaledModel() {
        return "godslayer_32";
    }
}
