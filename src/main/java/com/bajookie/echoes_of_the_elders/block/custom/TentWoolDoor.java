package com.bajookie.echoes_of_the_elders.block.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class TentWoolDoor extends DoorBlock {
    private static final BlockSetType WOOL_DOOR = new BlockSetType("wool_door",true,BlockSoundGroup.WOOL, SoundEvents.BLOCK_WOOL_PLACE,SoundEvents.BLOCK_WOOL_PLACE,SoundEvents.BLOCK_WOOL_PLACE,
            SoundEvents.BLOCK_WOOL_PLACE,SoundEvents.BLOCK_WOOL_PLACE,SoundEvents.BLOCK_WOOL_PLACE,SoundEvents.BLOCK_WOOL_PLACE,SoundEvents.BLOCK_WOOL_PLACE);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    public TentWoolDoor() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).nonOpaque().sounds(BlockSoundGroup.WOOL).blockVision(Blocks::never).solidBlock(Blocks::never), WOOL_DOOR);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (direction) {
            case WEST: {
                return EAST_SHAPE;
            }
            case SOUTH: {
                return NORTH_SHAPE;
            }
            case EAST: {
                return WEST_SHAPE;
            }
            case NORTH:
                return SOUTH_SHAPE;
        }
        return EAST_SHAPE;
    }

    /*
        @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        EOTE.LOGGER.info("trans");
        return isOpen(state);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return world.getMaxLightLevel();
    }
     */

    public void setOpen(@Nullable Entity entity, World world, BlockState state, BlockPos pos, boolean open) {
        if (!state.isOf(this) || state.get(OPEN) == open) {
            return;
        }
        this.playOpenCloseSound(entity, world, pos, open);
        world.setBlockState(pos, (BlockState) state.with(OPEN, open), Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
        world.emitGameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.isOpen(state) ? VoxelShapes.empty() : state.getOutlineShape(world, pos);
    }
    private void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? WOOL_DOOR.doorOpen() : WOOL_DOOR.doorClose(), SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.1f + 0.9f);
    }
}
