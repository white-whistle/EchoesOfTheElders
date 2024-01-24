package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AirSweeperProjectileEntity extends ProjectileEntity implements FlyingItemEntity {
    private final float maxPull;
    private final float speed;
    private final boolean isMaxed;
    private final int currentCount;
    private static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(AirSweeperProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);


    public AirSweeperProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.maxPull = 0.1f;
        this.isMaxed = false;
        this.speed = 0.5f;
        this.currentCount = 1;
    }

    public AirSweeperProjectileEntity(World world, double x, double y, double z, int target,float maxPull, boolean isMaxed,float speed,int currentStacks) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.AIR_SWEEPER_PROJECTILE_ENTITY_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.maxPull = maxPull;
        this.isMaxed = isMaxed;
        this.speed = speed;
        this.currentCount = currentStacks;
        this.dataTracker.set(TARGET_ID,target);
        this.setVelocity(0, 0.5, 0);
        this.refreshPositionAndAngles(x,y,z,this.getYaw(),this.getPitch());
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (!this.noClip) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        if (this.age >200){
            this.discard();
        }
        if (this.dataTracker.get(TARGET_ID) == -1) {
            this.discard(); // TODO
        } else {
            if (this.age>=20){
                World world = this.getWorld();
                Entity entity = world.getEntityById(this.dataTracker.get(TARGET_ID));
                if (entity instanceof LivingEntity livingEntity) {
                    if (!world.isClient){
                        if (this.isMaxed){
                            ((ServerWorld)world).spawnParticles(ParticleTypes.END_ROD,this.getX(),this.getY(),this.getZ(),1,0,0,0,0);
                        }
                        else if (this.currentCount >=16){
                            ((ServerWorld)world).spawnParticles(ParticleTypes.FLAME,this.getX(),this.getY(),this.getZ(),1,0,0,0,0);
                        } else if (this.currentCount >=8){
                            ((ServerWorld)world).spawnParticles(ParticleTypes.SMOKE,this.getX(),this.getY(),this.getZ(),1,0,0,0,0);
                        }
                    }
                    if (this.isMaxed){
                        predictHoming(livingEntity);
                    } else {
                        simpleHoming(livingEntity);
                    }

                } else {
                    this.discard();
                }
            } else {
                this.setPosition(this.getPos().add(this.getVelocity()));
            }
        }
    }
    private void simpleHoming(LivingEntity livingEntity){
        Vec3d currentPosition = this.getPos();
        Vec3d targetPosition = livingEntity.getPos();
        Vec3d currentVelocity = this.getVelocity();
        Vec3d desiredDirection = targetPosition.subtract(currentPosition).normalize();
        Vec3d newDirection = currentVelocity.normalize().add(desiredDirection.multiply(maxPull)).normalize();
        this.setVelocity(newDirection.multiply(this.speed));
        this.setPosition(this.getPos().add(this.getVelocity()));
    }
    private void predictHoming(LivingEntity livingEntity){
        Vec3d currentPosition = this.getPos();
        Vec3d targetPosition = livingEntity.getPos();
        Vec3d currentVelocity = this.getVelocity();
        double timeToTarget = targetPosition.distanceTo(currentPosition) / currentVelocity.length();
        Vec3d targetVelocity = livingEntity.getVelocity();
        Vec3d predictedPosition = targetPosition.add(targetVelocity.multiply(timeToTarget));
        Vec3d desiredDirection = predictedPosition.subtract(currentPosition).normalize();
        Vec3d newDirection = currentVelocity.normalize().add(desiredDirection.multiply(maxPull)).normalize();
        this.setVelocity(newDirection.multiply(this.speed));
        this.setPosition(this.getPos().add(this.getVelocity()));
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(TARGET_ID, -1);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!entityHitResult.getEntity().getWorld().isClient){
            entityHitResult.getEntity().getWorld().createExplosion(null,entityHitResult.getPos().x,entityHitResult.getPos().y,entityHitResult.getPos().z,3,false, World.ExplosionSourceType.MOB);
            this.discard();
        }
        super.onEntityHit(entityHitResult);

    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    public ItemStack getStack() {
        return Items.FIREWORK_ROCKET.getDefaultStack();
    }
}
