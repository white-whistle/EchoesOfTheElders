package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IcicleProjectile extends ProjectileEntity implements FlyingItemEntity {
    private static final TrackedData<Boolean> FALL = DataTracker.registerData(IcicleProjectile.class, TrackedDataHandlerRegistry.BOOLEAN);
    public IcicleProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this,this.getOwner() != null ? this.getOwner().getId() : 0);
    }

    public IcicleProjectile(World world, double x, double y, double z, Entity owner, boolean fall) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.ICICLE_PROJECTILE_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.dataTracker.set(FALL, fall);
        if (fall){
            this.setVelocity(0,-0.7,0);
        }
        this.setOwner(owner);
        this.refreshPositionAndAngles(x,y,z,this.getYaw(),this.getPitch());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > 80){
            this.discard();
        }
        if (!this.getWorld().isClient){
            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            this.checkBlockCollision();
            if (!this.noClip) {
                this.onCollision(hitResult);
                this.velocityDirty = true;
            }
            for(int i=0;i<3;i++){
                ((ServerWorld)this.getWorld()).spawnParticles(ModParticles.SNOW_FLAKE_PARTICLE,this.getX(),this.getY(),this.getZ(),1,0,0,0,0);
            }
        }
        if (!this.dataTracker.get(FALL)){
            Vec3d vec3d = this.getVelocity();
            double d = this.getX() + vec3d.x;
            double e = this.getY() + vec3d.y;
            double f = this.getZ() + vec3d.z;
            this.updateRotation();
            if (this.isTouchingWater()) {
                for (int i = 0; i < 4; ++i) {
                    float g = 0.25f;
                    this.getWorld().addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
                }
            }
            this.setVelocity(vec3d.multiply(0.99f));
            if (!this.hasNoGravity()) {
                Vec3d vec3d2 = this.getVelocity();
                this.setVelocity(vec3d2.x, vec3d2.y - 0.01f, vec3d2.z);
            }
            this.setPosition(d, e, f);
        } else {
            this.setPosition(this.getPos().add(this.getVelocity()));
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().damage(this.getWorld().getDamageSources().create(DamageTypes.MAGIC,this.getOwner()),this.dataTracker.get(FALL) ? 30f:10f);
        this.discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        if (!state.isSolid()) return;
        if (state.getBlock() == Blocks.AIR) return;
        if (state.getBlock() == Blocks.WATER){
            this.discard();
        }
        this.discard();
    }

    @Override
    public void onRemoved() {
        if (this.dataTracker.get(FALL)){
            this.getWorld().playSound(this.getX(),this.getY(),this.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,4f,4f,false);
        } else {
            this.getWorld().playSound(this.getX(),this.getY(),this.getZ(), SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.PLAYERS,4f,4f,false);
        }
        super.onRemoved();
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FALL,false);
    }

    @Override
    public ItemStack getStack() {
        return this.dataTracker.get(FALL)? Blocks.PACKED_ICE.asItem().getDefaultStack() : Items.SNOWBALL.getDefaultStack();
    }

    @Override
    public boolean hasNoGravity() {
        return this.dataTracker.get(FALL);
    }
}
