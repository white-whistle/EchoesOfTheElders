package com.bajookie.biotech.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class RelicContainerBlock extends ExperienceDroppingBlock {

    public RelicContainerBlock(Settings settings) {
        super(settings, UniformIntProvider.create(2,5));
    }
}
