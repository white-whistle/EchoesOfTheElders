package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

public class ChainLightningProjectileEntity extends ThrownItemEntity {
    public ChainLightningProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public ChainLightningProjectileEntity(World world, LivingEntity livingEntity){
        super(ModEntities.CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE,livingEntity,world);
    }
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient){
            this.getWorld().sendEntityStatus(this,(byte) 3);
            hit(entityHitResult.getPos());
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient){
            this.getWorld().sendEntityStatus(this,(byte) 3);
            hit(blockHitResult.getPos());
        }
        super.onBlockHit(blockHitResult);
    }
    private void hit(Vec3d pos){
        if (this.getWorld() != null && !this.getWorld().isClient){
            Box box = new Box(new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ())).expand(20);

            var entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
            for (LivingEntity entity : entities) {
                ServerWorld worlder = (ServerWorld) this.getWorld();
                EntityType.LIGHTNING_BOLT.spawn(worlder,entity.getBlockPos(), SpawnReason.TRIGGERED);
            }
            this.discard();
        }
    }

    @Override
    public int getDefaultPortalCooldown() {
        return super.getDefaultPortalCooldown();
    }
}
