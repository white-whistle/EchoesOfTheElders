package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.OptionalInt;

public class HookProjectileEntity extends ProjectileEntity {
    public static final TrackedData<Boolean> HIT = DataTracker.registerData(HookProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<OptionalInt> ENTITY_CATCH = DataTracker.registerData(HookProjectileEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);

    public HookProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public HookProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.HOOK_PROJECTILE_ENTITY_ENTITY_TYPE, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double) blockpos.getX() + 0.5D;
        double d1 = (double) blockpos.getY() + 1D;
        double d2 = (double) blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
    }

    @Override
    public void tick() {
        if (this.age >= 80) {
            this.remove(RemovalReason.DISCARDED);
        }
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        } else if (this.isInsideWaterOrBubbleColumn()) {
            this.discard();
        }
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        boolean bl = false;
        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.onCollision(hitResult);
        }
        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setVelocity(vec3d.multiply(1));
        if (!this.getWorld().isClient) {
            if (this.dataTracker.get(ENTITY_CATCH).isPresent()) {
                Entity entity = this.getWorld().getEntityById(this.dataTracker.get(ENTITY_CATCH).getAsInt());
                if (entity != null) {
                    entity.setPosition(d+vec3d.x*2,e+vec3d.y*2,f+vec3d.z*2);
                    entity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
                }
            }
        }
        this.setPosition(d, e, f);
    }


    private void invert() {
        this.setVelocity(this.getVelocity().multiply(-1));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() != this.getOwner()) {
            if (this.dataTracker.get(ENTITY_CATCH).isEmpty()) {
                this.dataTracker.set(ENTITY_CATCH, OptionalInt.of(entityHitResult.getEntity().getId()));
                invert();
            }
        } else {
            if (this.dataTracker.get(ENTITY_CATCH).isPresent()) {
                this.discard();
            }
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        if (this.dataTracker.get(ENTITY_CATCH).isPresent()) {
            this.getWorld().getEntityById(this.dataTracker.get(ENTITY_CATCH).getAsInt()).setVelocity(0, 0, 0);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld().isClient)
            return;
        if (hitResult instanceof BlockHitResult blockHitResult) {
            this.onBlockHit(blockHitResult);
        }
        if (hitResult instanceof EntityHitResult entityHitResult) {
            this.onEntityHit(entityHitResult);
        }
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(HIT, false);
        this.dataTracker.startTracking(ENTITY_CATCH, OptionalInt.empty());
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
