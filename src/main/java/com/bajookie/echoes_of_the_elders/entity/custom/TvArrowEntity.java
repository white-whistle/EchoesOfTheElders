package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TvArrowEntity extends ProjectileEntity implements FlyingItemEntity, Mount {
    private static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(TvArrowEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final Vec3d userPos;

    public TvArrowEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.speed = 0.5f;
        this.userPos = new Vec3d(0, 0, 0);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(TARGET_ID, -1);
    }


    public TvArrowEntity(World world, double x, double y, double z, int target, float speed, Vec3d userPos) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.TV_ARROW_ENTITY_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.speed = speed;
        this.userPos = userPos;
        this.dataTracker.set(TARGET_ID, target);
        this.setVelocity(0, 0.1, 0);
        this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (!this.noClip) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        if (this.age < 300) {
            if (this.getControllingPassenger() != null) {
                var pass = this.getControllingPassenger();
                Vec3d desiredDirection = new Vec3d(VectorUtil.pitchYawRollToDirection(pass.getPitch(), pass.getYaw(), pass.getRoll()));
                Vec3d newDirection = this.getVelocity().normalize().add(desiredDirection.multiply(0.1)).normalize();
                this.setVelocity(newDirection.multiply(1.3));
                this.setPosition(this.getPos().add(this.getVelocity()));
            } else {
                detonate(true);
            }
        } else {
            if (this.getControllingPassenger() != null) {
                this.getControllingPassenger().dismountVehicle();
                returnUserToPos();
            }
            this.setPosition(this.getPos().add(this.getVelocity()));
        }
        if (this.age > 400) {
            this.detonate(false);
        }
    }

    private void returnUserToPos() {
        if (this.getOwner() != null) {
            this.getOwner().teleport(this.userPos.x,this.userPos.y,this.userPos.z);
        }
    }

    private void detonate(boolean returnUser) {
        if (returnUser) {
            returnUserToPos();
        }
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this.getOwner(), this.getX(), this.getY(), this.getZ(), 6, false, World.ExplosionSourceType.MOB);
        }
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.detonate(this.getFirstPassenger() != null);
    }

    @Override
    public ItemStack getStack() {
        return Items.FIREWORK_ROCKET.getDefaultStack();
    }

    /*
    RIDING
     */

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        passenger.setPosition(this.userPos);
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return true;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean shouldDismountUnderwater() {
        return false;
    }

    @Override
    public void dismountVehicle() {
        super.dismountVehicle();
    }
}
