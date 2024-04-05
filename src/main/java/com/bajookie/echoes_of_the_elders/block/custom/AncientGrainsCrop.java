// package com.bajookie.echoes_of_the_elders.block.custom;
//
// import com.bajookie.echoes_of_the_elders.item.ModItems;
// import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
// import net.minecraft.block.Block;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.Blocks;
// import net.minecraft.block.CropBlock;
// import net.minecraft.item.ItemConvertible;
// import net.minecraft.state.StateManager;
// import net.minecraft.state.property.IntProperty;
// import net.minecraft.state.property.Properties;
//
// public class AncientGrainsCrop extends CropBlock {
//     public static final  int MAX_AGE=5;
//     public static final IntProperty AGE = Properties.AGE_5;
//     public AncientGrainsCrop() {
//         super(FabricBlockSettings.copyOf(Blocks.WHEAT));
//     }
//
//     @Override
//     protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//         builder.add(AGE);
//     }
//
//     @Override
//     public IntProperty getAgeProperty() {
//         return AGE;
//     }
//
//     @Override
//     protected ItemConvertible getSeedsItem() {
//         return ModItems.ANCIENT_GRAINS_SEEDS;
//     }
//
//     @Override
//     public int getMaxAge() {
//         return MAX_AGE;
//     }
// }
