package com.bajookie.biotech.system;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModRaid extends Raid {
    private static final Text EVENT_TEXT = Text.literal("Defense");
    private static final Text VICTORY_TITLE = Text.literal("Stage Cleared!");
    private static final Text DEFEAT_TITLE = Text.literal("Failed");

    public static final int SQUARED_MAX_RAIDER_DISTANCE = 12544;

    private long ticksActive;
    private BlockPos center;
    private boolean started;
    private float totalHealth;
    private boolean active;
    private int wavesSpawned;
    private int postRaidTicks;
    private int preRaidTicks;
    private int finishCooldown;
    private Status status;
    private final Map<Integer, Set<RaiderEntity>> waveToRaiders = Maps.newHashMap();

    private final int waveCount;
    private final Random random = Random.create();
    private final int id;
    private final ServerWorld world;
    private final ServerBossBar bar = new ServerBossBar(EVENT_TEXT, BossBar.Color.RED, BossBar.Style.NOTCHED_10);

    public ModRaid(int id, ServerWorld world, BlockPos pos) {
        super(id, world, pos);
        this.id = id;
        this.world = world;
        this.active = true;
        this.preRaidTicks = 300;
        this.bar.setPercent(0.0f);
        this.center = pos;
        this.waveCount = this.getMaxWaves();
        this.status = Status.ONGOING;
    }

    public ModRaid(ServerWorld world, NbtCompound nbt) {
        super(world, nbt);
        this.world = world;
        this.id = nbt.getInt("Id");
        this.started = nbt.getBoolean("Started");
        this.active = nbt.getBoolean("Active");
        this.ticksActive = nbt.getLong("TicksActive");
        this.wavesSpawned = nbt.getInt("GroupsSpawned");
        this.preRaidTicks = nbt.getInt("PreRaidTicks");
        this.postRaidTicks = nbt.getInt("PostRaidTicks");
        this.totalHealth = nbt.getFloat("TotalHealth");
        this.center = new BlockPos(nbt.getInt("CX"), nbt.getInt("CY"), nbt.getInt("CZ"));
        this.waveCount = nbt.getInt("NumGroups");
        this.status = Status.fromName(nbt.getString("Status"));
    }



    public void start(PlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.BAD_OMEN)) {
            this.badOmenLevel += player.getStatusEffect(StatusEffects.BAD_OMEN).getAmplifier() + 1;
            this.badOmenLevel = MathHelper.clamp(this.badOmenLevel, 0, this.getMaxAcceptableBadOmenLevel());
        }
        player.removeStatusEffect(StatusEffects.BAD_OMEN);
    }

    private void playRaidHorn(BlockPos pos) {
        float f = 13.0f;
        int i = 64;
        Collection<ServerPlayerEntity> collection = this.bar.getPlayers();
        long l = this.random.nextLong();
        for (ServerPlayerEntity serverPlayerEntity : this.world.getPlayers()) {
            Vec3d vec3d = serverPlayerEntity.getPos();
            Vec3d vec3d2 = Vec3d.ofCenter(pos);
            double d = Math.sqrt((vec3d2.x - vec3d.x) * (vec3d2.x - vec3d.x) + (vec3d2.z - vec3d.z) * (vec3d2.z - vec3d.z));
            double e = vec3d.x + 13.0 / d * (vec3d2.x - vec3d.x);
            double g = vec3d.z + 13.0 / d * (vec3d2.z - vec3d.z);
            if (!(d <= 64.0) && !collection.contains(serverPlayerEntity)) continue;
            serverPlayerEntity.networkHandler.sendPacket(new PlaySoundS2CPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, e, serverPlayerEntity.getY(), g, 64.0f, 1.0f, l));
        }
    }

    private boolean hasExtraWave() {
        return false;
    }
    private boolean isSpawningExtraWave() {
        return false;
    }

    private void spawnNextWave(BlockPos pos) {
        boolean bl = false;
        int i = this.wavesSpawned + 1;
        this.totalHealth = 0.0f;
        LocalDifficulty localDifficulty = this.world.getLocalDifficulty(pos);
        boolean bl2 = this.isSpawningExtraWave();
        for (Member member : Member.VALUES) {
            RaiderEntity raiderEntity;
            int j = this.getCount(member, i, bl2) + this.getBonusCount(member, this.random, i, localDifficulty, bl2);
            int k = 0;
            for (int l = 0; l < j && (raiderEntity = member.type.create(this.world)) != null; ++l) {
                if (!bl && raiderEntity.canLead()) {
                    raiderEntity.setPatrolLeader(true);
                    this.setWaveCaptain(i, raiderEntity);
                    bl = true;
                }
                this.addRaider(i, raiderEntity, pos, false);
                if (member.type != EntityType.RAVAGER) continue;
                RaiderEntity raiderEntity2 = null;
                if (i == this.getMaxWaves(Difficulty.NORMAL)) {
                    raiderEntity2 = EntityType.PILLAGER.create(this.world);
                } else if (i >= this.getMaxWaves(Difficulty.HARD)) {
                    raiderEntity2 = k == 0 ? (RaiderEntity) EntityType.EVOKER.create(this.world) : (RaiderEntity) EntityType.VINDICATOR.create(this.world);
                }
                ++k;
                if (raiderEntity2 == null) continue;
                this.addRaider(i, raiderEntity2, pos, false);
                raiderEntity2.refreshPositionAndAngles(pos, 0.0f, 0.0f);
                raiderEntity2.startRiding(raiderEntity);
            }
        }
        ++this.wavesSpawned;
        this.updateBar();
        this.markDirty();
    }

    public void tick() {
        if (this.hasStopped()) {
            return;
        }
        if (this.status == Raid.Status.ONGOING) {
            boolean bl2;
            boolean bl = this.active;
            this.active = this.world.isChunkLoaded(this.center);
            if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                this.invalidate();
                return;
            }
            if (bl != this.active) {
                this.bar.setVisible(this.active);
            }
            if (!this.active) {
                return;
            }
            if (!this.world.isNearOccupiedPointOfInterest(this.center)) {
                this.moveRaidCenter();
            }
            if (!this.world.isNearOccupiedPointOfInterest(this.center)) {
                if (this.wavesSpawned > 0) {
                    this.status = Raid.Status.LOSS;
                } else {
                    this.invalidate();
                }
            }
            ++this.ticksActive;
            if (this.ticksActive >= 48000L) {
                this.invalidate();
                return;
            }
            int i = this.getRaiderCount();
            if (i == 0 && this.shouldSpawnMoreGroups()) {
                if (this.preRaidTicks > 0) {
                    boolean bl3;
                    bl2 = this.preCalculatedRavagerSpawnLocation.isPresent();
                    boolean bl4 = bl3 = !bl2 && this.preRaidTicks % 5 == 0;
                    if (bl2 && !this.world.shouldTickEntity(this.preCalculatedRavagerSpawnLocation.get())) {
                        bl3 = true;
                    }
                    if (bl3) {
                        int j = 0;
                        if (this.preRaidTicks < 100) {
                            j = 1;
                        } else if (this.preRaidTicks < 40) {
                            j = 2;
                        }
                        this.preCalculatedRavagerSpawnLocation = this.preCalculateRavagerSpawnLocation(j);
                    }
                    if (this.preRaidTicks == 300 || this.preRaidTicks % 20 == 0) {
                        this.updateBarToPlayers();
                    }
                    --this.preRaidTicks;
                    this.bar.setPercent(MathHelper.clamp((float)(300 - this.preRaidTicks) / 300.0f, 0.0f, 1.0f));
                } else if (this.preRaidTicks == 0 && this.wavesSpawned > 0) {
                    this.preRaidTicks = 300;
                    this.bar.setName(EVENT_TEXT);
                    return;
                }
            }
            if (this.ticksActive % 20L == 0L) {
                this.updateBarToPlayers();
                this.removeObsoleteRaiders();
                if (i > 0) {
                    if (i <= 2) {
                        this.bar.setName(EVENT_TEXT.copy().append(" - ").append(Text.translatable(RAIDERS_REMAINING_TRANSLATION_KEY, i)));
                    } else {
                        this.bar.setName(EVENT_TEXT);
                    }
                } else {
                    this.bar.setName(EVENT_TEXT);
                }
            }
            bl2 = false;
            int k = 0;
            while (this.canSpawnRaiders()) {
                BlockPos blockPos;
                BlockPos blockPos2 = blockPos = this.preCalculatedRavagerSpawnLocation.isPresent() ? this.preCalculatedRavagerSpawnLocation.get() : this.getRavagerSpawnLocation(k, 20);
                if (blockPos != null) {
                    this.started = true;
                    this.spawnNextWave(blockPos);
                    if (!bl2) {
                        this.playRaidHorn(blockPos);
                        bl2 = true;
                    }
                } else {
                    ++k;
                }
                if (k <= 3) continue;
                this.invalidate();
                break;
            }
            if (this.hasStarted() && !this.shouldSpawnMoreGroups() && i == 0) {
                if (this.postRaidTicks < 40) {
                    ++this.postRaidTicks;
                } else {
                    this.status = Status.VICTORY;
                    for (UUID uUID : this.heroesOfTheVillage) {
                        Entity entity = this.world.getEntity(uUID);
                        if (!(entity instanceof LivingEntity)) continue;
                        LivingEntity livingEntity = (LivingEntity)entity;
                        if (entity.isSpectator()) continue;
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true));
                        if (!(livingEntity instanceof ServerPlayerEntity)) continue;
                        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)livingEntity;
                        serverPlayerEntity.incrementStat(Stats.RAID_WIN);
                        Criteria.HERO_OF_THE_VILLAGE.trigger(serverPlayerEntity);
                    }
                }
            }
            this.markDirty();
        } else if (this.isFinished()) {
            ++this.finishCooldown;
            if (this.finishCooldown >= 600) {
                this.invalidate();
                return;
            }
            if (this.finishCooldown % 20 == 0) {
                this.updateBarToPlayers();
                this.bar.setVisible(true);
                if (this.hasWon()) {
                    this.bar.setPercent(0.0f);
                    this.bar.setName(VICTORY_TITLE);
                } else {
                    this.bar.setName(DEFEAT_TITLE);
                }
            }
        }
    }


    public void addRaider(int wave, RaiderEntity raider, @Nullable BlockPos pos, boolean existing) {
        boolean bl = this.addToWave(wave, raider);
        if (bl) {
            raider.setRaid(this);
            raider.setWave(wave);
            raider.setAbleToJoinRaid(true);
            raider.setOutOfRaidCounter(0);
            if (!existing && pos != null) {
                raider.setPosition((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                raider.initialize(this.world, this.world.getLocalDifficulty(pos), SpawnReason.EVENT, null, null);
                raider.addBonusForWave(wave, false);
                raider.setOnGround(true);
                this.world.spawnEntityAndPassengers(raider);
            }
        }
    }

    /**
     * adds the raider to the wave bar
     *
     * @param wave   the wave index
     * @param entity the entity that is part of the raid
     * @return the hp bar of the raid
     */
    public boolean addToWave(int wave, RaiderEntity entity) {
        boolean countHealth = true;
        this.waveToRaiders.computeIfAbsent(wave, wavex -> Sets.newHashSet());
        Set<RaiderEntity> set = this.waveToRaiders.get(wave);
        RaiderEntity raiderEntity = null;
        for (RaiderEntity raiderEntity2 : set) {
            if (!raiderEntity2.getUuid().equals(entity.getUuid())) continue;
            raiderEntity = raiderEntity2;
            break;
        }
        if (raiderEntity != null) {
            set.remove(raiderEntity);
            set.add(entity);
        }
        set.add(entity);
        if (countHealth) {
            this.totalHealth += entity.getHealth();
        }
        this.updateBar();
        this.markDirty();
        return true;
    }

    /**
     * may be good for mobile defense where the point moves
     * setter for the raider center
     *
     * @param center center of the raid
     */
    @Deprecated
    private void setCenter(BlockPos center) {
        this.center = center;
    }

    /**
     * asking for the number of raiders left
     *
     * @param member number of the member of the raid
     * @param wave   the wave index
     * @param extra  ???
     * @return the number of raiders ?total?
     */
    private int getCount(Member member, int wave, boolean extra) {
        return extra ? member.countInWave[this.waveCount] : member.countInWave[wave];
    }

    /**
     * this controls the max waves in each run
     *
     * @return the number of maximum waves that can be spawned
     */
    public int getMaxWaves() {
        return 10;
    }

    /**
     * this gives the player a relic after winning
     */
    public void giveRelic() {
    }

    private void markDirty() {
        this.world.getRaidManager().markDirty();
    }

    private int getBonusCount(Member member, Random random, int wave, LocalDifficulty localDifficulty, boolean extra) {
        int i;
        Difficulty difficulty = localDifficulty.getGlobalDifficulty();
        boolean bl = difficulty == Difficulty.EASY;
        boolean bl2 = difficulty == Difficulty.NORMAL;
        switch (member) {
            case WITCH: {
                if (!bl && wave > 2 && wave != 4) {
                    i = 1;
                    break;
                }
                return 0;
            }
            case PILLAGER:
            case VINDICATOR: {
                if (bl) {
                    i = random.nextInt(2);
                    break;
                }
                if (bl2) {
                    i = 1;
                    break;
                }
                i = 2;
                break;
            }
            case RAVAGER: {
                i = !bl && extra ? 1 : 0;
                break;
            }
            default: {
                return 0;
            }
        }
        return i > 0 ? random.nextInt(i + 1) : 0;
    }

    /**
     * this enum sets the status of the raid
     */
    static enum Status {
        ONGOING,
        VICTORY,
        LOSS,
        STOPPED;

        private static final Status[] VALUES;

        static Status fromName(String name) {
            for (Status status : VALUES) {
                if (!name.equalsIgnoreCase(status.name())) continue;
                return status;
            }
            return ONGOING;
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        static {
            VALUES = Status.values();
        }
    }

    /**
     * this enum sets the number of members each wave
     */
    static enum Member {
        VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
        EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
        PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
        WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
        RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

        static final Member[] VALUES;
        final EntityType<? extends RaiderEntity> type;
        final int[] countInWave;

        private Member(EntityType<? extends RaiderEntity> type, int[] countInWave) {
            this.type = type;
            this.countInWave = countInWave;
        }

        static {
            VALUES = Member.values();
        }
    }
}
