package com.bajookie.biotech.block.custom;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class NetherFruitBlock extends PlantBlock {
    public NetherFruitBlock() {
        super(FabricBlockSettings.copyOf(Blocks.NETHER_WART));
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos oneUnder = new BlockPos(pos.getX(),pos.getY()-1,pos.getZ());
        BlockPos oneUp = new BlockPos(pos.getX(),pos.getY()+1,pos.getZ());
        if (world.getBlockState(oneUnder).getBlock() == Blocks.AIR && world.getBlockState(oneUp).getBlock() == Blocks.NETHERRACK ){
            return true;
        }
        else return false;
    }
}
