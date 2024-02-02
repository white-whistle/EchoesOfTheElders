package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

import static net.minecraft.entity.passive.BeeEntity.field_28638;

public class ZomBeeEntity extends HostileEntity implements Flutterer {
    private float currentPitch;
    private float lastPitch;

    public ZomBeeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new FlightMoveControl(this, 20, true);
        this.lookControl = new ZomBeeLookControl(this);
    }

    public ZomBeeEntity(World world,boolean haveRaider){
        this(ModEntities.ZOMBEE_ENTITY_TYPE,world);
        if (haveRaider){
            ZombieEntity zombie = new ZombieEntity(world);
            zombie.setBaby(true);
            zombie.startRiding(this);
        }
    }
    public ZomBeeEntity(World world,Item raiderWeapon){
        this(world,true);
        ZombieEntity zombie = new ZombieEntity(world);
        zombie.setBaby(true);
        zombie.setStackInHand(zombie.getActiveHand(),new ItemStack(raiderWeapon));
        zombie.startRiding(this);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(9, new SwimGoal(this));
        this.goalSelector.add(8, new ZomBeeWanderAroundGoal());
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        //this.goalSelector.add(1, new MeleeAttackGoal(this, 1f, true));
        this.goalSelector.add(1, new ZomBeeAttack(this));

        this.targetSelector.add(2, new ActiveTargetGoal<LivingEntity>(this, LivingEntity.class, 0, false, false, living -> ((living instanceof PlayerEntity player && !player.getAbilities().creativeMode) || living instanceof FlowerDefenseEntity ||
                living instanceof IronGolemEntity || living instanceof SnowGolemEntity || living instanceof VillagerEntity) && living.isAlive()
        ));

    }

    class ZomBeeWanderAroundGoal
            extends Goal {
        private static final int MAX_DISTANCE = 22;

        ZomBeeWanderAroundGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return ZomBeeEntity.this.navigation.isIdle() && ZomBeeEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return ZomBeeEntity.this.navigation.isFollowingPath();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                ZomBeeEntity.this.navigation.startMovingAlong(ZomBeeEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = ZomBeeEntity.this.getRotationVec(0.0f);
            int i = 8;
            Vec3d vec3d3 = AboveGroundTargeting.find(ZomBeeEntity.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964f, 3, 1);
            if (vec3d3 != null) {
                return vec3d3;
            }
            return NoPenaltySolidTargeting.find(ZomBeeEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    class ZomBeeAttack extends Goal {
        private final MobEntity mob;
        private LivingEntity target;
        private int cooldown;

        public ZomBeeAttack(MobEntity mob) {
            this.mob = mob;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (livingEntity instanceof PlayerEntity player) {
                if (player.getAbilities().creativeMode) {
                    return false;
                }
            }
            this.target = livingEntity;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (this.target == null){
                this.stop();
                return false;
            }
            if (!this.target.isAlive()) {
                this.stop();
                return false;
            }
            if (target instanceof PlayerEntity player) {
                if (player.getAbilities().creativeMode) {
                    this.stop();
                    return false;
                }
            }
            if (this.mob.squaredDistanceTo(this.target) > 225.0) {
                this.stop();
                return false;
            }
            return !this.mob.getNavigation().isIdle() || this.canStart();
        }

        @Override
        public void stop() {
            mob.setTarget(null);
            this.target = null;
            this.mob.getNavigation().stop();
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            this.mob.getLookControl().lookAt(this.target, 30.0f, 30.0f);
            double d = this.mob.getWidth() * 2.0f * (this.mob.getWidth() * 2.0f);
            double e = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
            double f = 0.8;
            if (e > d && e < 16.0) {
                f = 1.33;
            } else if (e < 225.0) {
                f = 0.6;
            }
            this.mob.getNavigation().startMovingTo(this.target, f);
            this.cooldown = Math.max(this.cooldown - 1, 0);
            if (e > d) {
                return;
            }
            if (this.cooldown > 0) {
                return;
            }
            this.cooldown = 20;
            this.mob.tryAttack(this.target);
        }
    }

    class ZomBeeLookControl
            extends LookControl {
        ZomBeeLookControl(MobEntity entity) {
            super(entity);
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        protected boolean shouldStayHorizontal() {
            return false;
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.playSound(SoundEvents.ENTITY_BEE_STING, 1.0f, 1.0f);
        return super.tryAttack(target);
    }

    @Override
    public int getXpToDrop() {
        if (this.isBaby()) {
            this.experiencePoints = (int) ((double) this.experiencePoints * 2.5);
        }
        return super.getXpToDrop();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {

            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            @Override
            public void tick() {
                super.tick();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public static EntityType<ZomBeeEntity> createEntityType(List<Item> list) {
        return FabricEntityTypeBuilder.<ZomBeeEntity>create(SpawnGroup.MONSTER, (entity, world) -> new ZomBeeEntity(entity, world))
                .dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build();
    }

    public static DefaultAttributeContainer.Builder createZombeeAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.5f);
    }

    public float getBodyPitch(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastPitch, this.currentPitch);
    }

    private void updateBodyPitch() {
        this.lastPitch = this.currentPitch;
        this.currentPitch = this.isNearTarget() ? Math.min(1.0f, this.currentPitch + 0.2f) : Math.max(0.0f, this.currentPitch - 0.24f);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return super.getTarget();
    }

    private boolean isNearTarget() {
        if (this.getTarget() != null) {
            return this.getPos().distanceTo(this.getTarget().getPos()) <= 5;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.updateBodyPitch();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return true;
    }

    @Override
    public boolean isFlappingWings() {
        return this.isInAir() && this.age % field_28638 == 0;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BEE_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }


    static class StingTargetGoal extends ActiveTargetGoal<PlayerEntity> {
        StingTargetGoal(ZomBeeEntity bee) {
            super(bee, PlayerEntity.class, 10, true, false, living -> true);
        }

        @Override
        public boolean canStart() {
            return this.canSting() && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            boolean bl = this.canSting();
            if (!bl || this.mob.getTarget() == null) {
                this.target = null;
                return false;
            }
            return true;
        }

        private boolean canSting() {
            return true;
        }
    }
}
