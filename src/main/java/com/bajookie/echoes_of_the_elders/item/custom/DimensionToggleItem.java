package com.bajookie.echoes_of_the_elders.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class DimensionToggleItem extends Item {

    public DimensionToggleItem(Settings settings) {
        super(settings);
    }

    public int getMinUsageTicks() {
        return 20 * 2;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        user.setCurrentHand(hand);

        var pos = user.getPos();
        var random = user.getRandom();
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_TRIGGER, SoundCategory.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f, false);

        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }

        var pos = user.getPos();
        var random = user.getRandom();
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            PlayerEntity playerEntity = (PlayerEntity) user;

            MinecraftServer minecraftServer = serverWorld.getServer();

            var currentTicks = this.getMaxUseTime(stack) - remainingUseTicks;

            var destinationDimKey = this.getNextDimension(stack, world, user);
            var dimensionValid = destinationDimKey != null;
            var enoughUseTicks = currentTicks >= this.getMinUsageTicks();

            if (!dimensionValid || !enoughUseTicks) {
                world.playSound(playerEntity, playerEntity.getBlockPos(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.PLAYERS);
                return;
            }

            ServerWorld destinationWorld = minecraftServer.getWorld(destinationDimKey);

            if (destinationWorld != null && minecraftServer.isNetherAllowed() && !playerEntity.hasVehicle()) {

                if (playerEntity.isRemoved()) {
                    return;
                }

                double d = DimensionType.getCoordinateScaleFactor(playerEntity.getWorld().getDimension(), destinationWorld.getDimension());
                WorldBorder worldBorder = destinationWorld.getWorldBorder();
                BlockPos destinationPos = worldBorder.clamp(playerEntity.getX() * d, playerEntity.getY(), playerEntity.getZ() * d);
                playerEntity.teleport(destinationWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), Set.of(), playerEntity.getYaw(), playerEntity.getPitch());

            }

            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }

    @Nullable
    public abstract RegistryKey<World> getNextDimension(ItemStack stack, World world, LivingEntity user);

}
