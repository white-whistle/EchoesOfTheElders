package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;

public class RaidObjectiveCapability extends Capability<LivingEntity> {
    private static class Keys {
        private static final String REMAINING_ENEMIES = "remainingEnemies";
        private static final String REMAINING_WAVES = "remainingWaves";
        private static final String LEVEL = "level";
        private static final String ITEM_STACKS = "itemstacks";
    }

    public int remainingWaves;
    public int remainingEnemies;
    public int level;
    public final ArrayList<ItemStack> items = new ArrayList<>();

    public RaidObjectiveCapability(LivingEntity self) {
        super(self);
    }

    public void onVictory() {
        items.forEach(self::dropStack);

        self.discard();
    }

    public void onWaveVictory() {
        remainingWaves--;
        level++;

        if (remainingWaves < 1) {
            onVictory();
        } else {
            spawnWave();
        }
    }

    public void spawnWave() {
        remainingEnemies = RaidWaves.getWave(level).spawnEntities(self);
    }

    public void begin() {
        spawnWave();
    }

    public void onLose() {
        if (self == null) return;
        
        items.forEach((i) -> {
            self.dropStack(new ItemStack(Blocks.DIRT));
        });
    }

    public void onEnemyKilled(LivingEntity enemy) {
        remainingEnemies--;

        if (remainingEnemies < 1) {
            onWaveVictory();
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt(Keys.REMAINING_WAVES, remainingWaves);
        nbt.putInt(Keys.REMAINING_ENEMIES, remainingEnemies);
        nbt.putInt(Keys.LEVEL, level);

        NbtList nbtItemstackList = new NbtList();

        items.forEach(itemStack -> {
            var itemstackNbt = new NbtCompound();
            itemStack.writeNbt(itemstackNbt);
            nbtItemstackList.add(itemstackNbt);
        });

        nbt.put(Keys.ITEM_STACKS, nbtItemstackList);
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        remainingEnemies = nbt.getInt(Keys.REMAINING_ENEMIES);
        remainingWaves = nbt.getInt(Keys.REMAINING_WAVES);
        level = nbt.getInt(Keys.LEVEL);

        var nbtItemstackList = nbt.getList(Keys.ITEM_STACKS, NbtElement.COMPOUND_TYPE);

        nbtItemstackList.forEach(c -> {
            var itemStack = ItemStack.fromNbt((NbtCompound) c);
            items.add(itemStack);
        });
    }
}
