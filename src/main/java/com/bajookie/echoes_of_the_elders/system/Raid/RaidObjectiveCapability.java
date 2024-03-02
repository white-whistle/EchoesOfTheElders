package com.bajookie.echoes_of_the_elders.system.Raid;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.screen.RaidContinueScreenHandler;
import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.ItemStack.RaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Tier;
import com.bajookie.echoes_of_the_elders.system.Raid.waves.WaveFeatures;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.EntityUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static com.bajookie.echoes_of_the_elders.effects.ModEffects.RAID_OBJECTIVE_CONTINUE_PHASE;

public class RaidObjectiveCapability extends Capability<LivingEntity> {
    private static class Keys {
        private static final String REMAINING_ENEMIES = "remainingEnemies";
        private static final String REMAINING_WAVES = "remainingWaves";
        private static final String INITIAL_WAVES = "initialWaves";
        private static final String LEVEL = "level";
        private static final String ACTIVE = "active";
        private static final String ITEM_STACKS = "itemstacks";
        private static final String RAID_ANSWERS = "raidAnswers";
    }

    public int initialWaves = -1;
    public int remainingWaves = -1;
    public int initialEnemyCount = -1;
    public ArrayList<UUID> remainingEnemies = new ArrayList<>();
    public int level = -1;
    public boolean active = false;
    public final ArrayList<ItemStack> items = new ArrayList<>();

    public ServerBossBar raidWaveBar = (ServerBossBar) new ServerBossBar(Text.empty(), BossBar.Color.RED, BossBar.Style.PROGRESS).setDarkenSky(true);
    public ServerBossBar raidHealthBar = new ServerBossBar(Text.empty(), BossBar.Color.PURPLE, BossBar.Style.NOTCHED_20);

    public ArrayList<Pair<UUID, RaidAnswer>> raidAnswers = new ArrayList<>();

    public RaidObjectiveCapability(LivingEntity self) {
        super(self);
    }

    public int getWavesPerRaid() {
        return 5;
    }

    public boolean isInContinuePhase() {
        if (self == null) return false;
        return self.hasStatusEffect(RAID_OBJECTIVE_CONTINUE_PHASE);
    }

    private ItemStack getRandomRelicDropStack() {
        Random r = new Random();
        var artifacts = ModItems.registeredModItems.stream().filter(item -> item instanceof IArtifact iArtifact && iArtifact.shouldDrop()).toList();

        var randomArtifactItem = artifacts.get(r.nextInt(artifacts.size()));

        return new ItemStack(randomArtifactItem, 1);
    }

    // called when a bundle of waves is won
    public void onVictory() {
        if (self.getWorld().isClient) return;

        level++;

        items.forEach((stack) -> {
            Tier.raise(stack, 1);
            RaidReward.queueItem(stack, getRandomRelicDropStack());
        });

        self.addStatusEffect(new StatusEffectInstance(RAID_OBJECTIVE_CONTINUE_PHASE, 20 * 30));
        setContinueBar();

        PlayerLookup.tracking(self).forEach(this::tryOpenPickerScreen);

        PlayerLookup.tracking(self).forEach(player -> {
            player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.AMBIENT, 1, 1);
        });
    }

    // called when a wave has been won
    public void onWaveVictory() {
        if (self.getWorld().isClient) return;

        remainingWaves--;

        if (remainingWaves < 1) {
            sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.level_complete"));
            self.addStatusEffect(new StatusEffectInstance(ModEffects.RAID_OBJECTIVE_VICTORY_PHASE, 3 * 20));
        } else {
            sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.wave_complete"));
            spawnWave();
        }
    }

    public void spawnWave() {
        if (self == null || self.getWorld().isClient) return;

        var wave = WaveFeatures.getEntities(self.getWorld(), level);
        ArrayList<UUID> uu = new ArrayList<>();

        for (LivingEntity entity : wave) {
            var pos = RaidPositioner.random(50, 100).next(entity.getWorld(), self, entity);

            entity.setPosition(pos.getX() + RaidPositioner.r.nextFloat(), pos.getY(), pos.getZ() + RaidPositioner.r.nextFloat());
            entity.getWorld().spawnEntity(entity);

            if (entity instanceof MobEntity mobEntity) {
                mobEntity.setTarget(self);
                mobEntity.setPersistent();
            }

            ModCapabilities.RAID_ENEMY.attach(entity, e -> {
                e.setRaidTarget(self);
            });
            uu.add(entity.getUuid());
        }

        remainingEnemies = uu;
        initialEnemyCount = remainingEnemies.size();
        raidWaveBar.setPercent(getWaveProgress());
        setWaveBar();

        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.incoming_wave")); // was wave.name()

        PlayerLookup.tracking(self).forEach(player -> {
            player.playSound(SoundEvents.GOAT_HORN_SOUNDS.get(2).value(), SoundCategory.AMBIENT, 1, 1);
        });
    }

    private Text getRaidName() {
        return Text.translatable("raid_bar.echoes_of_the_elders.raid_health.title", level + 1);
    }

    private Text getWaveName() {
        var current = initialWaves - remainingWaves + 1;
        return Text.translatable("raid_bar.echoes_of_the_elders.raid_wave.title", current, initialWaves);
    }

    private float getWaveProgress() {
        return remainingEnemies.size() / (float) initialEnemyCount;
    }

    // called after the key insertion grace period is over
    public void begin() {
        this.active = true;
        ModCapabilities.RAID_OBJECTIVE.syncEntityCapability(self);

        raidHealthBar.setName(getRaidName());
        raidWaveBar.setName(getWaveName());
        PlayerLookup.tracking(self).forEach(this::addRaidBars);

        spawnWave();
    }

    public boolean canAcceptKeyFromPlayer(PlayerEntity player) {
        if (active) return false;

        var owner = player.getUuid();
        return items.stream().noneMatch(stack -> {
            var soulbound = Soulbound.getUuid(stack);
            if (soulbound == null) return false;

            return soulbound.equals(owner);
        });
    }

    public void addKey(ItemStack itemStack, PlayerEntity user) {
        // add item
        Soulbound.set(itemStack, user);
        items.add(itemStack);
        self.addStatusEffect(new StatusEffectInstance(ModEffects.RAID_OBJECTIVE_START_COOLDOWN, 20 * 10));

        if (remainingWaves == -1) {
            // init vars
            var amt = getWavesPerRaid();
            remainingWaves = amt;
            initialWaves = amt;

            // announce
            if (!self.getWorld().isClient) {
                PlayerLookup.tracking(self).forEach(player -> player.sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.charging"), true));
            }
        }

        ModCapabilities.RAID_OBJECTIVE.syncEntityCapability(self);
    }

    public boolean tryAddKey(ItemStack itemStack, PlayerEntity user) {
        if (self == null) return false;
        if (level == -1) level = Tier.get(itemStack);

        if (Tier.get(itemStack) != level) return false;
        if (!canAcceptKeyFromPlayer(user)) return false;

        addKey(itemStack, user);

        return true;
    }

    public void cleanupEnemies() {
        if (self == null || self.getWorld().isClient) return;
        
        remainingEnemies.forEach(eUuid -> {
            var ent = EntityUtil.getEntityByUUID(self.getWorld(), eUuid);
            if (ent != null) ent.discard();
        });

        remainingEnemies.clear();
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

        cleanupEnemies();

        // just in case
        closeOpenScreens();

        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.defeat"));

        PlayerLookup.tracking(self).forEach(player -> {
            player.playSound(SoundEvents.GOAT_HORN_SOUNDS.get(4).value(), SoundCategory.AMBIENT, 1, 1);
        });
    }

    private void sendMessage(Text text) {
        if (!self.getWorld().isClient) {
            PlayerLookup.tracking(self).forEach(player -> player.sendMessage(text, true));
        }
    }

    public void onEnemyKilled(LivingEntity enemy) {
        if (enemy.getWorld().isClient) return;

        remainingEnemies.removeIf(e -> e.equals(enemy.getUuid()));
        raidWaveBar.setPercent(getWaveProgress());

        if (remainingEnemies.size() < 1) {
            onWaveVictory();
        }
    }

    public void addEnemy(LivingEntity entity) {
        remainingEnemies.add(entity.getUuid());
        initialEnemyCount++;
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt(Keys.REMAINING_WAVES, remainingWaves);
        nbt.putInt(Keys.INITIAL_WAVES, initialWaves);
        nbt.putInt(Keys.LEVEL, level);
        nbt.putBoolean(Keys.ACTIVE, active);

        NbtList nbtItemstackList = new NbtList();
        NbtList nbtUuidList = new NbtList();
        NbtList nbtAnswerList = new NbtList();

        remainingEnemies.forEach(eUuid -> nbtUuidList.add(NbtHelper.fromUuid(eUuid)));

        items.forEach(itemStack -> {
            var itemstackNbt = new NbtCompound();
            itemStack.writeNbt(itemstackNbt);
            nbtItemstackList.add(itemstackNbt);
        });

        raidAnswers.forEach(p -> {
            var c = new NbtCompound();
            c.putUuid("uuid", p.getLeft());
            c.putInt("answer", p.getRight().value);
        });

        nbt.put(Keys.REMAINING_ENEMIES, nbtUuidList);
        nbt.put(Keys.ITEM_STACKS, nbtItemstackList);
        nbt.put(Keys.RAID_ANSWERS, nbtAnswerList);

    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        remainingWaves = nbt.getInt(Keys.REMAINING_WAVES);
        initialWaves = nbt.getInt(Keys.INITIAL_WAVES);
        level = nbt.getInt(Keys.LEVEL);
        active = nbt.getBoolean(Keys.ACTIVE);

        var nbtItemstackList = nbt.getList(Keys.ITEM_STACKS, NbtElement.COMPOUND_TYPE);
        var nbtAnswerList = nbt.getList(Keys.RAID_ANSWERS, NbtElement.COMPOUND_TYPE);
        var nbtUuidList = nbt.getList(Keys.REMAINING_ENEMIES, NbtElement.INT_ARRAY_TYPE);

        nbtItemstackList.forEach(c -> {
            var itemStack = ItemStack.fromNbt((NbtCompound) c);
            items.add(itemStack);
        });

        nbtUuidList.forEach(c -> {
            var uuid = NbtHelper.toUuid(c);
            remainingEnemies.add(uuid);
        });

        nbtAnswerList.forEach(c -> {
            var compound = (NbtCompound) c;
            var uuid = compound.getUuid("uuid");
            var answer = RaidAnswer.values()[compound.getInt("answer")];
            raidAnswers.add(new Pair<>(uuid, answer));
        });

        raidWaveBar.setName(getWaveName());
        raidWaveBar.setPercent(getWaveProgress());

        if (isInContinuePhase()) {
            setContinueBar();
        }
    }

    public void setWaveBar() {
        raidWaveBar.setName(getWaveName());
        raidWaveBar.setColor(BossBar.Color.RED);
    }

    public void setContinueBar() {
        raidWaveBar.setName(Text.translatable("screen.echoes_of_the_elders.raid_continue.continue"));
        raidWaveBar.setColor(BossBar.Color.BLUE);
    }

    public void dropLeaverLoot() {
        var newItems = new ArrayList<ItemStack>();
        items.forEach(stack -> {
            var sb = Soulbound.getUuid(stack);
            var isContinueUser = raidAnswers.stream().anyMatch(p -> p.getRight() == RaidAnswer.CONTINUE && p.getLeft().equals(sb));
            if (isContinueUser) {
                newItems.add(stack);
                return;
            }

            var bag = new ItemStack(ModItems.PANDORAS_BAG);
            RaidReward.set(bag, RaidReward.get(stack));
            Soulbound.setUuid(bag, sb);
            Soulbound.setName(bag, Soulbound.getName(stack));

            // PandorasBag.setBagInventory(bag, RaidReward.get(stack));

            self.dropStack(bag);
        });

        this.items.clear();
        this.items.addAll(newItems);
    }

    public void closeOpenScreens() {
        PlayerLookup.tracking(self).forEach(player -> {
            if (player.currentScreenHandler instanceof RaidContinueScreenHandler handler) {
                var id = handler.inventory.getStack(2);
                var uuid = Soulbound.getUuid(id);
                if (self.getUuid().equals(uuid)) {
                    player.closeHandledScreen();
                }
            }
        });
    }

    // called once the raid continue grace period is over
    // evaluates the current choices made by all players
    public void advance() {
        var shouldContinue = raidAnswers.stream().anyMatch(p -> p.getRight() == RaidAnswer.CONTINUE);

        closeOpenScreens();

        dropLeaverLoot();


        if (shouldContinue) {
            onContinue();
        } else {
            onComplete();
        }
    }

    // called once all players choose to extract, this is the absolute end of this entity
    public void onComplete() {
        sendMessage(TextUtil.translatable("message.echoes_of_the_elders.raid.victory"));

        PlayerLookup.tracking(self).forEach(player -> {
            player.playSound(SoundEvents.ITEM_TOTEM_USE, SoundCategory.AMBIENT, 1, 1);
        });


        var nbt = new NbtCompound();
        var fireworks = new NbtCompound();
        var explosions = new NbtList();
        var e1 = new NbtCompound();
        nbt.put("Fireworks", fireworks);
        fireworks.put("Explosions", explosions);
        explosions.add(e1);
        fireworks.putInt("Flight", 2);

        e1.putInt("Type", 1);
        e1.putIntArray("Colors", new int[]{14602026, 12801229});
        e1.putIntArray("FadeColors", new int[]{8073150, 4312372});

        var fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
        fireworkStack.setNbt(nbt);

        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(self.getWorld(), null, self.getX(), self.getY(), self.getZ(), fireworkStack);
        self.getWorld().spawnEntity(fireworkRocketEntity);

        self.discard();
    }

    // called once continue grace period is over and at least one player picked continue
    public void onContinue() {
        raidAnswers = new ArrayList<>();

        var amt = getWavesPerRaid();
        remainingWaves = amt;
        initialWaves = amt;

        raidHealthBar.setName(getRaidName());
        raidWaveBar.setName(getWaveName());

        ModCapabilities.RAID_OBJECTIVE.syncEntityCapability(self);

        spawnWave();
    }

    public void answer(PlayerEntity player, RaidAnswer answer) {
        var owner = player.getUuid();
        raidAnswers.removeIf(p -> p.getLeft().equals(owner));
        raidAnswers.add(new Pair<>(owner, answer));

        if (raidAnswers.size() == items.size()) {
            self.removeStatusEffect(RAID_OBJECTIVE_CONTINUE_PHASE);
        }
    }

    @Nullable
    public ItemStack getPlayerStack(PlayerEntity player) {
        var owner = player.getUuid();
        return items.stream()
                .filter(stack -> {
                    var sb = Soulbound.getUuid(stack);
                    return owner.equals(sb);
                })
                .findFirst().orElse(null);
    }

    public void tryOpenPickerScreen(ServerPlayerEntity player) {
        if (!self.hasStatusEffect(RAID_OBJECTIVE_CONTINUE_PHASE)) return;

        var playerStack = getPlayerStack(player);
        if (playerStack == null) return;

        var inv = RaidReward.get(playerStack);
        var lastGottenItem = inv.getStack(0);
        var bag = new ItemStack(ModItems.PANDORAS_BAG);
        RaidReward.set(bag, inv);
        var id = new ItemStack(ModItems.CORRUPTED_KEY);
        Soulbound.setUuid(id, self.getUuid());

        var screenInv = new SimpleInventory(3);
        screenInv.setStack(0, lastGottenItem);
        screenInv.setStack(1, bag);
        screenInv.setStack(2, id);

        player.openHandledScreen(new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.translatable("screen.echoes_of_the_elders.raid_continue.title", level);
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                return new RaidContinueScreenHandler(syncId, playerInventory, screenInv);
            }
        });
    }

    public void addRaidBars(ServerPlayerEntity player) {
        raidHealthBar.addPlayer(player);
        raidWaveBar.addPlayer(player);

        tryOpenPickerScreen(player);
    }

    public void removeRaidBars(ServerPlayerEntity player) {
        raidHealthBar.removePlayer(player);
        raidWaveBar.removePlayer(player);
    }

    public void tick() {
        if (self == null) return;

        if (!self.getWorld().isClient) {
            raidHealthBar.setPercent(self.getHealth() / self.getMaxHealth());


            if (isInContinuePhase()) {
                var status = self.getStatusEffect(RAID_OBJECTIVE_CONTINUE_PHASE);
                if (status != null) {
                    var progress = status.getDuration() / (float) (30 * 20);
                    raidWaveBar.setPercent(progress);
                }
            }
        }
    }

    public enum RaidAnswer {
        NONE(0),
        CONTINUE(1),
        LEAVE(2);

        public final int value;

        RaidAnswer(int value) {
            this.value = value;
        }
    }
}
