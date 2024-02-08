package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.util.AlgebraHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import oshi.util.tuples.Quartet;
import oshi.util.tuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class ResourceRewardBags extends Item {
    private Type type;
    public ResourceRewardBags(Type type) {
        super(new FabricItemSettings().maxCount(16));
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var rewards = AlgebraHelper.rollRewards(this.type.getBagMap());
        rewards.forEach(itemIntegerPair -> {
            user.dropItem(new ItemStack(itemIntegerPair.getLeft(),itemIntegerPair.getRight()),true);
        });
        return super.use(world, user, hand);
    }

    public static class RewardBuilder {
        private Map<Item, Triplet<Integer, Integer, Integer>> map;

        public RewardBuilder() {
            this.map = new HashMap<>();
        }

        public RewardBuilder addEntry(Item item, int min, int max, int weight) {
            map.put(item, new Triplet<>(min, max, weight));
            return this;
        }

        public Map<Item, Triplet<Integer, Integer, Integer>> build() {
            return this.map;
        }

    }

    public static enum Type {
        S_TIER(new RewardBuilder().addEntry(Items.NETHERITE_INGOT, 0, 3, 4).addEntry(Items.NETHER_STAR, 0, 1, 1)
                .addEntry(Items.DIAMOND,2,4,10).addEntry(Items.EMERALD,2,4,10).addEntry(Items.ENCHANTED_GOLDEN_APPLE,0,2,3).build()),
        A_TIER(new RewardBuilder().addEntry(Items.NETHERITE_INGOT,0,1,1).addEntry(Items.EMERALD,1,3,5)
                .addEntry(Items.DIAMOND,1,3,5).addEntry(Items.GOLD_INGOT,2,6,10).addEntry(Items.IRON_INGOT,0,3,7)
                .addEntry(Items.REDSTONE,0,20,3).addEntry(Items.LAPIS_LAZULI,0,20,3).addEntry(Items.GOLDEN_APPLE,0,2,3).build()),
        B_TIER(new RewardBuilder().addEntry(Items.IRON_INGOT,3,6,14).addEntry(Items.GOLD_INGOT,0,3,6).addEntry(Items.REDSTONE,10,20,6).addEntry(Items.LAPIS_LAZULI,10,20,6)
                .addEntry(Items.GHAST_TEAR,0,2,4).addEntry(ModItems.EXPLORER_FRUIT,0,3,2).addEntry(ModBlocks.MINERS_FRUIT_BLOCK.asItem(), 0,3,2)
                .addEntry(ModBlocks.NETHER_FRUIT_BLOCK.asItem(), 0,3,2).build()),
        C_TIER(new RewardBuilder().addEntry(Items.IRON_INGOT,1,4,7).addEntry(Items.COAL,2,5,15).addEntry(Items.APPLE,1,4,10).build()),
        D_TIER(new RewardBuilder().addEntry(Items.COAL,1,4,10).build()),
        FOOD_TIER(new RewardBuilder().addEntry(Items.CAKE,0,1,1).addEntry(Items.MUSHROOM_STEW,0,1,2).addEntry(Items.COOKED_BEEF,0,8,2).addEntry(Items.COOKED_PORKCHOP,0,8,2)
                .addEntry(Items.APPLE,1,2,2).addEntry(Items.COOKED_CHICKEN,0,8,2).addEntry(Items.COOKED_CHICKEN,0,8,2)
                .addEntry(Items.COOKED_SALMON,0,8,2).addEntry(Items.GOLDEN_CARROT,0,3,1).build());
        private Map<Item, Triplet<Integer, Integer, Integer>> bagMap;

        Type(Map<Item, Triplet<Integer, Integer, Integer>> map) {
            this.bagMap = map;
        }

        public Map<Item, Triplet<Integer, Integer, Integer>> getBagMap() {
            return bagMap;
        }
    }
}
