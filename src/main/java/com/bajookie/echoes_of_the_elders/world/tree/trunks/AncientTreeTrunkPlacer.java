package com.bajookie.echoes_of_the_elders.world.tree.trunks;

import com.bajookie.echoes_of_the_elders.world.tree.ModTrunkPlacerTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AncientTreeTrunkPlacer extends TrunkPlacer {
    public static final Codec<AncientTreeTrunkPlacer> CODEC = RecordCodecBuilder.create(ancientTreeTrunkPlacerInstance ->
        fillTrunkPlacerFields(ancientTreeTrunkPlacerInstance).apply(ancientTreeTrunkPlacerInstance,AncientTreeTrunkPlacer::new)
    );
    public AncientTreeTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.ANCIENT_TREE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world,replacer,random,startPos.down(),config);
        List<FoliagePlacer.TreeNode> foliageList = new ArrayList<>();
        int branchLength = 6;
        int treeHeight = baseHeight + random.nextBetween(firstRandomHeight,secondRandomHeight);
        int rootDepth = 4;

        for (int heightStep = 0;heightStep<treeHeight;heightStep++){
            //set the main trunk
            if (heightStep < treeHeight-4){
                getAndSetState(world,replacer,random,startPos.up(heightStep).east(),config);
                getAndSetState(world,replacer,random,startPos.up(heightStep).west(),config);
                getAndSetState(world,replacer,random,startPos.up(heightStep).south(),config);
                getAndSetState(world,replacer,random,startPos.up(heightStep).north(),config);
            }
            getAndSetState(world,replacer,random,startPos.up(heightStep),config);
            //branch setup
            if (heightStep>10 && heightStep%4 == 0 && heightStep<treeHeight-4){
                foliageList.addAll(generateBranch(heightStep,branchLength,world,replacer,random,startPos.north(),config,Direction.NORTH));
                foliageList.addAll(generateBranch(heightStep,branchLength,world,replacer,random,startPos.east(),config,Direction.EAST));
                foliageList.addAll(generateBranch(heightStep,branchLength,world,replacer,random,startPos.west(),config,Direction.WEST));
                foliageList.addAll(generateBranch(heightStep,branchLength,world,replacer,random,startPos.south(),config,Direction.SOUTH));
            }
        }
        foliageList.add(new FoliagePlacer.TreeNode(startPos.up(treeHeight-3),3,true));
        //generateRoots(rootDepth,world,replacer,random,startPos,config);
        return foliageList;
    }

    private List<FoliagePlacer.TreeNode> generateBranch(int height, int length, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config, Direction direction){
        List<FoliagePlacer.TreeNode> foliageList = new ArrayList<>();
        Function<BlockState, BlockState> axisAlign = state -> (BlockState)state.withIfExists(PillarBlock.AXIS, direction.getAxis());
        for (int j =0; j<length;j++){
            if (j<2){ // ensure branch have a start
                getAndSetState(world,replacer,random,startPos.up(height).offset(direction,j),config,axisAlign);
            }else {
                if (random.nextBetween(0,10) > 1){ // small chance to skip a branch log
                    getAndSetState(world,replacer,random,startPos.up(height).offset(direction,j),config,axisAlign);
                }
            }
        }
        foliageList.add(new FoliagePlacer.TreeNode(startPos.up(height-1).offset(direction,length),1,false));
        return foliageList;
    }
    private void generateRoots(int depth,TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config){
        for (int i =1; i<depth; i ++){
            getAndSetState(world,replacer,random,startPos.down(i),config);
            getAndSetState(world,replacer,random,startPos.down(i).east(i),config);
            getAndSetState(world,replacer,random,startPos.down(i).west(i),config);
            getAndSetState(world,replacer,random,startPos.down(i).north(i),config);
            getAndSetState(world,replacer,random,startPos.down(i).south(i),config);
        }
    }
}
