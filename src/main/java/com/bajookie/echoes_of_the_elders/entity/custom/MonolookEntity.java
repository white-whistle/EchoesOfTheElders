package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.particles.LineParticleEffect;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class MonolookEntity extends TameableEntity {
    public static final TrackedData<Integer> SHOOT_PROGRESS = DataTracker.registerData(MonolookEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(MonolookEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private final ServerBossBar HP = new ServerBossBar(Text.literal("companion hp"), BossBar.Color.RED, BossBar.Style.NOTCHED_6);

    public MonolookEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public MonolookEntity(World world) {
        super(ModEntities.MONOLOOK_ENTITY_TYPE, world);
    }

    public static DefaultAttributeContainer.Builder createMonolookEntityAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ARMOR, 0f);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(SHOOT_PROGRESS,0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.goalSelector.add(5,new LookAtMoveDirection(this));
        this.goalSelector.add(6, new ShootFireballGoal(this));
        this.goalSelector.add(6, new LookAtTargetGoal(this));
        this.targetSelector.add(4, new RevengeGoal(this, new Class[0]).setGroupRevenge(new Class[0]));
        this.targetSelector.add(3, new AttackWithOwnerGoal(this));
        this.targetSelector.add(5, new ActiveTargetGoal<LivingEntity>(this, LivingEntity.class, 0, true, false, living -> living instanceof Monster && living.isAlive()));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getOwner() != null) {
            var living = this.getOwner();
            if (!this.getWorld().isClient){
                this.HP.setPercent(this.getHealth()/this.getMaxHealth());
                var dir = Math.toRadians(living.getYaw()+100);
                var look = new Vec3d(Math.sin(dir)*-1, 0, Math.cos(dir)).normalize().multiply(0.6).add(0,2 +0.3*Math.sin(this.age*0.04),0);
                Vec3d ownerPos = living.getPos().add(look);
                this.setPosition(ownerPos);
            }
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.PLAYERS;
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity) return false;
        if (this.hasStatusEffect(ModEffects.RAID_OBJECTIVE_CONTINUE_PHASE)) return false;

        return super.damage(source, amount);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void setOwner(PlayerEntity player) {
        super.setOwner(player);
    }

    @Override
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        if (player.getUuid().equals(this.getOwnerUuid())){
            HP.removePlayer(player);
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        if (player.getUuid().equals(this.getOwnerUuid())){
            HP.addPlayer(player);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        if (this.dataTracker.get(OWNER_UUID).isPresent()) {
            return this.dataTracker.get(OWNER_UUID).get();
        } else {
            return null;
        }
    }

    @Override
    public EntityView method_48926() {
        return getWorld();
    }


    protected boolean isOwner(Entity entity) {
        if (this.dataTracker.get(OWNER_UUID).isPresent()) {
            return entity.getUuid().equals(this.dataTracker.get(OWNER_UUID).get());
        }
        return false;
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        UUID uUID = this.getOwnerUuid();
        if (uUID == null) {
            return null;
        }
        return this.getWorld().getPlayerByUuid(uUID);
    }


    static class LookAtTargetGoal
            extends Goal {
        private final MonolookEntity monolook;

        public LookAtTargetGoal(MonolookEntity monolook) {
            this.monolook = monolook;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.monolook.getTarget() == null) {
                Vec3d vec3d = this.monolook.getVelocity();
                this.monolook.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776f);
                this.monolook.bodyYaw = this.monolook.getYaw();
            } else {
                LivingEntity livingEntity = this.monolook.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.monolook) < 512.0) {
                    double e = livingEntity.getX() - this.monolook.getX();
                    double f = livingEntity.getZ() - this.monolook.getZ();
                    this.monolook.setYaw(-((float) MathHelper.atan2(e, f)) * 57.295776f);
                    this.monolook.bodyYaw = this.monolook.getYaw();
                }
            }
        }
    }

    static class LookAtMoveDirection extends Goal {
        private final MonolookEntity monolook;

        public LookAtMoveDirection(MonolookEntity monolook) {
            this.monolook = monolook;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (monolook.getOwner() != null) {
                Vec3d movement = monolook.getOwner().getVelocity();
                if (!movement.equals(new Vec3d(0, 0, 0)) && monolook.getTarget() == null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (monolook.getOwner() != null) {
                double movement = monolook.getOwner().getYaw();
                monolook.setYaw((float) movement);
            }
        }
    }

    static class ShootFireballGoal
            extends Goal {
        private final MonolookEntity monolook;
        public int cooldown;

        public ShootFireballGoal(MonolookEntity monolook) {
            this.monolook = monolook;
        }

        @Override
        public boolean canStart() {
            return this.monolook.getTarget() != null;
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void stop() {
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.monolook.getTarget();
            if (livingEntity == null) {
                return;
            }
            double d = 64.0;
            if (livingEntity.squaredDistanceTo(this.monolook) < 512.0 && this.monolook.canSee(livingEntity)) {
                World world = this.monolook.getWorld();
                ++this.cooldown;
                if (this.cooldown == 10 && !this.monolook.isSilent()) {
                    world.syncWorldEvent(null, WorldEvents.GHAST_WARNS, this.monolook.getBlockPos(), 0);
                }
                if (this.cooldown>10 && !monolook.getWorld().isClient){
                    monolook.dataTracker.set(SHOOT_PROGRESS,this.cooldown-10);
                }
                if (this.cooldown == 20) {
                    if (!this.monolook.isSilent()) {
                        world.syncWorldEvent(null, WorldEvents.GHAST_SHOOTS, this.monolook.getBlockPos(), 0);
                    }
                    if (!world.isClient) {
                        var startPos = monolook.getEyePos();
                        var entityPos = livingEntity.getPos();
                        var up = new Vector3f(0, 1, 0);
                        var right = new Vector3f(entityPos.toVector3f()).sub(startPos.toVector3f()).cross(up).normalize();
                        ServerWorld serverWorld = (ServerWorld) world;
                        serverWorld.spawnParticles(new LineParticleEffect(
                                new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.4f)).add(right.mul(0.6f)),
                                new Vector3f((float) (entityPos.x), (float) (livingEntity.getBodyY(0.5)), (float) (entityPos.z)),
                                new Vector3f(255 / 255f, 184 / 255f, 117 / 255f)
                        ), startPos.x, startPos.y, startPos.z, 1, 0, 0, 0, 0);
                        livingEntity.damage(world.getDamageSources().create(DamageTypes.MAGIC), 8);
                        monolook.dataTracker.set(SHOOT_PROGRESS,0);
                    }
                    this.cooldown = -20;
                }
            } else if (this.cooldown > 0) {
                --this.cooldown;
            }
        }
    }
}