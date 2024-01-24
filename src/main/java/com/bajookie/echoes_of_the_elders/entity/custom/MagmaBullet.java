package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class MagmaBullet extends ProjectileEntity implements FlyingItemEntity {
    private final int power;

    public MagmaBullet(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.power = 1;
    }

    public MagmaBullet(World world, double x, double y, double z, int power, Entity owner) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.MAGMA_BULLET_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.setOwner(owner);
        this.refreshPosition();
        this.power = power;
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (!this.getWorld().isClient()) {
            this.move(MovementType.SELF, this.getVelocity());
        }
        this.checkBlockCollision();
        if (!this.noClip) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        if (this.age > 200) {
            this.discard();
        }
        if (this.getWorld().isClient()) {
            if (this.age % 1 == 0) {
                this.getWorld().addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
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
                living.damage(living.getWorld().getDamageSources().create(DamageTypes.MAGIC,this.getOwner()),this.power);
            }
            discard();
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    public ItemStack getStack() {
        return Items.DIAMOND.getDefaultStack();
    }
}
