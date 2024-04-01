package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.custom.GaleQuiverItem;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GaleArrowProjectile extends ArrowEntity implements ICustomProjectileCrit {
    private static final TrackedData<Boolean> MAXED = DataTracker.registerData(GaleArrowProjectile.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> LIFESPAN = DataTracker.registerData(GaleArrowProjectile.class, TrackedDataHandlerRegistry.INTEGER);

    private static final int MAX_LIFESPAN = 10 * 20;

    public GaleArrowProjectile(EntityType<? extends GaleArrowProjectile> entityType, World world) {
        super(entityType, world);

        setNoGravity(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(MAXED, false);
        this.dataTracker.startTracking(LIFESPAN, 0);

    }

    public GaleArrowProjectile(World world, @Nullable LivingEntity owner) {
        super(ModEntities.GALE_ARROW_ENTITY, world);
        if (owner != null) {
            this.setPos(owner.getX(), owner.getY() + 1.5, owner.getZ());
            this.setOwner(owner);
        }

        setNoGravity(true);
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            // dropStars(blockHitResult.getPos());
            this.discard();
        } else {
            super.onBlockHit(blockHitResult);
        }
    }

    @Override
    public void tick() {
        super.tick();

        var cLifespan = this.dataTracker.get(LIFESPAN);

        if (cLifespan >= MAX_LIFESPAN) {
            this.discard();
            return;
        }

        this.dataTracker.set(LIFESPAN, cLifespan + 1);

        // if (!this.getWorld().isClient) {
        //     Vec3d pos = this.getPos();
        //     Vec3d pos2 = this.getPos();
        //     Vec3d direction = this.getVelocity().normalize();
        //     Pair<Vec3d, Vec3d> plane = VectorUtil.perpendicularPlaneFromVector(direction);
        //     pos = pos.add(plane.getLeft().multiply(Math.cos(Math.toRadians(this.age * 20))));
        //     pos2 = pos2.add(plane.getLeft().multiply(Math.cos(Math.PI + Math.toRadians(this.age * 20))).multiply(0.6));
        //     pos = pos.add(plane.getRight().multiply(Math.sin(Math.toRadians(this.age * 20))));
        //     pos2 = pos2.add(plane.getRight().multiply(Math.sin(Math.PI + Math.toRadians(this.age * 20))).multiply(0.6));
        //     ((ServerWorld) this.getWorld()).spawnParticles(ModParticles.STARFALL_TRAIL_PARTICLE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        //     ((ServerWorld) this.getWorld()).spawnParticles(ModParticles.STARFALL_TRAIL_PARTICLE, pos2.x, pos2.y, pos2.z, 1, 0, 0, 0, 0);
        // }
    }

    @Override
    public void initFromStack(ItemStack stack) {
        this.dataTracker.set(MAXED, StackLevel.isMaxed(stack));
        super.initFromStack(stack);

        this.setDamage(this.getDamage() + GaleQuiverItem.BONUS_ARROW_DAMAGE.get(stack));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            // dropStars(entityHitResult.getPos());
        }


        super.onEntityHit(entityHitResult);
    }

    public boolean isMaxed() {
        return this.dataTracker.get(MAXED);
    }

    @Override
    public void spawnCritParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        world.addParticle(ModParticles.GALE_CRIT, x, y, z, velocityX, 0, velocityZ);
    }
}
