package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Tier;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.UUID;

public class RaidObjectiveCapability extends Capability<LivingEntity> {
    private static class Keys {
        private static final String REMAINING_ENEMIES = "remainingEnemies";
        private static final String REMAINING_WAVES = "remainingWaves";
        private static final String LEVEL = "level";
        private static final String ITEM_STACKS = "itemstacks";
    }

    public int remainingWaves = -1;
    public ArrayList<UUID> remainingEnemies = new ArrayList<>();
    public int level = -1;
    public final ArrayList<ItemStack> items = new ArrayList<>();

    public RaidObjectiveCapability(LivingEntity self) {
        super(self);
    }

    public void onVictory() {
        items.forEach((stack) -> {
            Tier.raise(stack, 1);
            self.dropStack(stack);
        });

        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.victory"));
        self.discard();
    }

    public void onWaveVictory() {
        remainingWaves--;
        level++;

        if (remainingWaves < 1) {
            onVictory();
        } else {
            sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.wave_complete"));
            spawnWave();
        }
    }

    public void spawnWave() {
        var wave = RaidWaves.getWave(level);
        remainingEnemies = wave.spawnEntities(self);
        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.incoming_wave", new TextArgs().put("wave", wave.name())));
    }

    public void begin() {
        spawnWave();
    }

    public boolean tryAddKey(ItemStack itemStack, PlayerEntity user) {
        var owner = user.getUuid();

        if (self == null) return false;
        if (level == -1) level = Tier.get(itemStack);

        if (Tier.get(itemStack) != level) return false;
        if (items.stream().anyMatch(stack -> Soulbound.getUuid(stack) == owner)) return false;

        if (remainingWaves == -1) {
            remainingWaves = 5;
        }

        Soulbound.set(itemStack, user);
        items.add(itemStack);
        self.addStatusEffect(new StatusEffectInstance(ModEffects.RAID_OBJECTIVE_START_COOLDOWN, 20 * 10));
        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.charging"));
        return true;
    }

    public void onLose() {
        if (self == null) return;

        items.forEach((i) -> {
            var stack = new ItemStack(ModItems.CORRUPTED_KEY);
            Soulbound.setUuid(stack, Soulbound.getUuid(i));
            Soulbound.setName(stack, Soulbound.getName(i));
            Tier.set(stack, Tier.get(i));

            self.dropStack(stack);
        });

        remainingEnemies.forEach(eUuid -> {
            var ent = EntityUtil.getEntityByUUID(self.getWorld(), eUuid);
            if (ent != null) ent.discard();
        });

        remainingEnemies.clear();

        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.defeat"));
    }

    private void sendMessage(Text text) {
        if (!self.getWorld().isClient) {
            PlayerLookup.tracking(self).forEach(player -> {
                player.sendMessage(text, true);
            });
        }
    }

    public void onEnemyKilled(LivingEntity enemy) {
        remainingEnemies.removeIf(e -> e.equals(enemy.getUuid()));

        if (remainingEnemies.size() < 1) {
            onWaveVictory();
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt(Keys.REMAINING_WAVES, remainingWaves);
        nbt.putInt(Keys.LEVEL, level);

        NbtList nbtItemstackList = new NbtList();
        NbtList nbtUuidList = new NbtList();

        remainingEnemies.forEach(eUuid -> {
            nbtUuidList.add(NbtHelper.fromUuid(eUuid));
        });

        items.forEach(itemStack -> {
            var itemstackNbt = new NbtCompound();
            itemStack.writeNbt(itemstackNbt);
            nbtItemstackList.add(itemstackNbt);
        });

        nbt.put(Keys.REMAINING_ENEMIES, nbtUuidList);
        nbt.put(Keys.ITEM_STACKS, nbtItemstackList);
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        remainingWaves = nbt.getInt(Keys.REMAINING_WAVES);
        level = nbt.getInt(Keys.LEVEL);

        var nbtItemstackList = nbt.getList(Keys.ITEM_STACKS, NbtElement.COMPOUND_TYPE);
        var nbtUuidList = nbt.getList(Keys.REMAINING_ENEMIES, NbtElement.INT_ARRAY_TYPE);

        nbtItemstackList.forEach(c -> {
            var itemStack = ItemStack.fromNbt((NbtCompound) c);
            items.add(itemStack);
        });

        nbtUuidList.forEach(c -> {
            var uuid = NbtHelper.toUuid(c);
            remainingEnemies.add(uuid);
        });
    }
}
