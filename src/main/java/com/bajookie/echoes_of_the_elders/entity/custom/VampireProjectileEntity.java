package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VampireProjectileEntity extends ProjectileEntity {
    public static final TrackedData<Boolean> HIT = DataTracker.registerData(VampireProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int duration = 20*5;

    public VampireProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public VampireProjectileEntity(World world,PlayerEntity player) {
        this(ModEntities.VAMPIRE_PROJECTILE_ENTITY_TYPE,world);
        setOwner(player);
        BlockPos pos = player.getBlockPos();
        this.refreshPositionAndAngles(pos.getX()+0.5D,pos.getY()+1.75D,pos.getZ()+0.5D,this.getYaw(),this.getPitch());
    }

    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(HIT,false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.dataTracker.get(HIT)){
            if (this.age >= this.duration){
                this.discard();
            }
            this.setVelocity(0,0,0);
        } else {
            this.setVelocity(this.getVelocity());
        }
        if (this.age >=20*30){
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() != getOwner() && entityHitResult.getEntity() instanceof LivingEntity livingEntity){
            this.dataTracker.set(HIT,true);
            this.duration = this.age+this.duration;
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient) {
            this.discard();
        }
        super.onBlockHit(blockHitResult);
    }


    @Nullable
    public PlayerEntity getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
    }

}
