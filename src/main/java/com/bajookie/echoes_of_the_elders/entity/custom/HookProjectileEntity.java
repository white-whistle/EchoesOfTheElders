package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.block.AbstractBlock;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.OptionalInt;

public class HookProjectileEntity extends ProjectileEntity {
    public static final TrackedData<Boolean> HIT = DataTracker.registerData(HookProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<OptionalInt> ENTITY_CATCH = DataTracker.registerData(HookProjectileEntity.class,TrackedDataHandlerRegistry.OPTIONAL_INT);

    public HookProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public HookProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.HOOK_PROJECTILE_ENTITY_ENTITY_TYPE, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double) blockpos.getX() + 0.5D;
        double d1 = (double) blockpos.getY() + 2D;
        double d2 = (double) blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
    }

    @Override
    public void tick() {
        if (this.age >= 80) {
            this.remove(RemovalReason.DISCARDED);
        }

        HitResult hitresult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitresult.getType() != HitResult.Type.MISS)
            this.onCollision(hitresult);
        Vec3d vec3 = this.getVelocity();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.move(MovementType.SELF,this.getVelocity());
        //this.refreshPositionAndAngles(d0,d1,d2,this.getYaw(),this.getPitch());
        this.setVelocity(this.getVelocity().multiply(0.99f));
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        } else if (this.isInsideWaterOrBubbleColumn()) {
            this.discard();
        }
        if (!this.getWorld().isClient){
            if (this.dataTracker.get(ENTITY_CATCH).isPresent()){
                Entity entity = this.getWorld().getEntityById(this.dataTracker.get(ENTITY_CATCH).getAsInt());
                if (entity != null){
                    entity.refreshPositionAfterTeleport(d0,d1,d2);
                    entity.setVelocity(0,0,0);
                }
            }
        }
    }


    private void invert(){
        this.setVelocity(this.getVelocity().multiply(-1));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() != this.getOwner()) {
            if (this.dataTracker.get(ENTITY_CATCH).isEmpty()){
                this.dataTracker.set(ENTITY_CATCH,OptionalInt.of(entityHitResult.getEntity().getId()));
                invert();
            }
        } else {
            if (this.dataTracker.get(ENTITY_CATCH).isPresent()){
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
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld().isClient)
            return;
        if (hitResult instanceof BlockHitResult blockHitResult){
            this.onBlockHit(blockHitResult);
        }
        if (hitResult instanceof EntityHitResult entityHitResult){
            this.onEntityHit(entityHitResult);
        }
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(HIT, false);
        this.dataTracker.startTracking(ENTITY_CATCH,OptionalInt.empty());
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
