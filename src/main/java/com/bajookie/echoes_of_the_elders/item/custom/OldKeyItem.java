package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import java.util.Random;

public class OldKeyItem extends Item {
    public OldKeyItem() {
        super(new FabricItemSettings().maxCount(4));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
        PlayerEntity player = context.getPlayer();
        if(block == ModBlocks.RELIC_CONTAINER_BLOCK){
            if (!player.getAbilities().creativeMode){
                context.getStack().decrement(1);
            }
            context.getWorld().breakBlock(context.getBlockPos(),false);
            dropFromTable(context.getPlayer());
            context.getWorld().playSound(context.getBlockPos().getX(),context.getBlockPos().getY(),context.getBlockPos().getZ(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT,4,4,true);
        }
        return ActionResult.success(context.getWorld().isClient);
    }

    private void dropFromTable(PlayerEntity player){
        Random r = new Random();
        int num = r.nextInt(8);
        if (num>=0 && num<=0){
            player.giveItemStack(new ItemStack(ModItems.DAGON));
            return;
        }
        if (num>=1 && num<=1){
            player.giveItemStack(new ItemStack(ModItems.GALE_CORE));
            return;
        }
        if (num>=2 && num<=2){
            player.giveItemStack(new ItemStack(ModItems.POTION_MIRAGE));
            return;
        }
        if (num>=3 && num<=3){
            player.giveItemStack(new ItemStack(ModItems.ANCIENT_STONE_SWORD));
            return;
        }
        if (num>=4 && num<=4){
            player.giveItemStack(new ItemStack(ModItems.FIRE_SNAP));
            return;
        }
        if (num>=5 && num<=5){
            player.giveItemStack(new ItemStack(ModItems.MIDAS_HAMMER));
            return;
        }
        if (num>=6 && num<=6){
            player.giveItemStack(new ItemStack(ModItems.PORTAL_RING));
            return;
        }
        if (num>=7 && num<=7){
            player.giveItemStack(new ItemStack(ModItems.RADIANT_LOTUS));
            return;
        }
        //LootPool.Builder builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
        //                .with(Arrays.asList(ItemEntry.builder(ModItems.DAGON).weight(1).build(),
        //                        ItemEntry.builder(ModItems.GALE_CORE).weight(1).build(),
        //                        ItemEntry.builder(ModItems.POTION_MIRAGE).weight(1).build(),
        //                        ItemEntry.builder(ModItems.ANCIENT_STONE_SWORD).weight(1).build(),
        //                        ItemEntry.builder(ModItems.FIRE_SNAP).weight(1).build(),
        //                        ItemEntry.builder(ModItems.MIDAS_HAMMER).weight(1).build(),
        //                        ItemEntry.builder(ModItems.PORTAL_RING).weight(1).build(),
        //                        ItemEntry.builder(ModItems.RADIANT_LOTUS).weight(1).build()
        //                        ));
    }
}
