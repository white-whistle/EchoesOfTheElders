package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import net.minecraft.block.Block;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class IcicleProjectile extends ProjectileEntity implements FlyingItemEntity {
    public IcicleProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public IcicleProjectile(World world, double x, double y, double z, Entity owner) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.ICICLE_PROJECTILE_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.setOwner(owner);
        this.setVelocity(0,-0.7,0);
        this.refreshPositionAndAngles(x,y,z,this.getYaw(),this.getPitch());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > 80){
            this.discard();
        }
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.checkBlockCollision();
        if (!this.noClip) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        this.setPosition(this.getPos().add(this.getVelocity()));
        if (!this.getWorld().isClient){
            for(int i=0;i<3;i++){
                ((ServerWorld)this.getWorld()).spawnParticles(ModParticles.SNOW_FLAKE_PARTICLE,this.getX(),this.getY(),this.getZ(),1,0,0,0,0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().damage(this.getWorld().getDamageSources().create(DamageTypes.MAGIC,this.getOwner()),30);
        this.discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        if (state.getBlock() == Blocks.AIR) return;
        if (state.getBlock() == Blocks.WATER){
            System.out.println("wata");
            this.getWorld().setBlockState(this.getBlockPos(),Blocks.ICE.getDefaultState());
            this.discard();
        }
        this.discard();
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public ItemStack getStack() {
        return Items.DIAMOND.getDefaultStack();
    }
}
