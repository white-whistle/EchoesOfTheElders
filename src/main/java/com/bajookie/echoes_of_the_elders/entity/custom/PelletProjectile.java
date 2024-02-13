package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PelletProjectile extends ProjectileEntity {
    private final int power;
    public PelletProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.power = 1;
    }

    public PelletProjectile(World world, double x, double y, double z, int power, Entity owner, float pitch, float yaw) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.PELLET_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.setOwner(owner);
        this.power = power;
        this.setRotation(yaw,pitch);
        this.refreshPositionAndAngles(x,y,z,this.getYaw(),this.getPitch());
    }
    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.setVelocity(this.getVelocity());
        this.setPosition(this.getPos().add(this.getVelocity()));
        this.checkBlockCollision();
        if (!this.noClip) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        if (this.age > 40) {
            this.discard();
        }
        if (this.getWorld().isClient()) {
            if (this.age % 1 == 0) {
                this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void initDataTracker() {

    }
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() != this.getOwner()){
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof LivingEntity living){
                if (this.getOwner() != null){
                    living.damage(ModDamageSources.echoAttack(this.getOwner()),power);
                }
            }
            discard();
        }
        super.onEntityHit(entityHitResult);
    }
}
