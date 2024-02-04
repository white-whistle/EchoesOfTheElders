package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class StarArrowProjectile extends ArrowEntity {
    private boolean star = false;

    public StarArrowProjectile(EntityType<? extends StarArrowProjectile> entityType, World world) {
        super(entityType, world);
    }

    public StarArrowProjectile(World world, @Nullable LivingEntity owner, boolean isStar) {
        super(ModEntities.STAR_ARROW_ENTITY, world);
        this.setPos(owner.getX(), owner.getY() + 1.5, owner.getZ());
        this.setOwner(owner);
        this.star = isStar;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.getWorld() != null && !this.getWorld().isClient &&this.star) {
            dropStars(blockHitResult.getPos());
            this.discard();
        } else {
            super.onBlockHit(blockHitResult);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && this.star) {
            Vec3d pos = this.getPos();
            Vec3d pos2 = this.getPos();
            Vec3d direction = this.getVelocity().normalize();
            Pair<Vec3d, Vec3d> plane = VectorUtil.perpendicularPlaneFromVector(direction);
            pos = pos.add(plane.getLeft().multiply(Math.cos(Math.toRadians(this.age * 20))));
            pos2 = pos2.add(plane.getLeft().multiply(Math.cos(Math.PI + Math.toRadians(this.age * 20))).multiply(0.6));
            pos = pos.add(plane.getRight().multiply(Math.sin(Math.toRadians(this.age * 20))));
            pos2 = pos2.add(plane.getRight().multiply(Math.sin(Math.PI + Math.toRadians(this.age * 20))).multiply(0.6));
            ((ServerWorld) this.getWorld()).spawnParticles(ModParticles.STARFALL_TRAIL_PARTICLE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
            ((ServerWorld) this.getWorld()).spawnParticles(ModParticles.STARFALL_TRAIL_PARTICLE, pos2.x, pos2.y, pos2.z, 1, 0, 0, 0, 0);
        }
    }

    private void dropStars(Vec3d pos) {
        if (this.star) {
            Box box = new Box(new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ())).expand(12);
            var entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
            Random r = new Random();
            for (LivingEntity entity : entities) {
                if (entity instanceof PlayerEntity) continue;
                if (this.getOwner() instanceof LivingEntity living) {
                    entity.setAttacker(living);
                }
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.STARFALL_EFFECT, 25 + r.nextInt(1, 4) * 5, 1));
            }
        }
    }

    @Override
    public void initFromStack(ItemStack stack) {
        super.initFromStack(stack);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            dropStars(entityHitResult.getPos());
        }
        super.onEntityHit(entityHitResult);
    }
}
