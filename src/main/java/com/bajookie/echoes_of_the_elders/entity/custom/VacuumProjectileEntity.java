package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.effects.NoGravityEffect;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ModStatus;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VacuumProjectileEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> HIT = DataTracker.registerData(VacuumProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private List<Entity> entities = new ArrayList<>();
    public VacuumProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(HIT,false);
        super.initDataTracker();
    }

    public VacuumProjectileEntity(World world, LivingEntity livingEntity) {
        super(ModEntities.VACUUM_PROJECTILE_ENTITY_ENTITY_TYPE, livingEntity, world);
    }

    @Override
    public void tick() {
        if (this.dataTracker.get(HIT)){
            if (this.age <= 20){
                this.setVelocity(0,0.23,0);
            }else {
                this.suckMonsters();
                this.setVelocity(0,0,0);
                if (!this.getWorld().isClient()){
                    Random r = new Random();
                    ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.PORTAL,this.getX(),this.getY(),this.getZ(),1,0,0,0,1);
                }
            }
            if (this.age >= 160){
                implode();
                this.discard();
            }
        }
        super.tick();
    }

    private void suckMonsters() {
        getHits();
        if (!this.entities.isEmpty()) {
            for (Entity living : this.entities) {
                if (living !=null){
                    if (NoGravityEffect.tryApply((LivingEntity) living)){
                        ((LivingEntity)living).addStatusEffect(new StatusEffectInstance(ModEffects.NO_GRAVITY_EFFECT,3,1,true,false));
                    }
                    living.addVelocity(this.getPos().add(Math.sin(this.age)*2,0,Math.cos(this.age)*2).subtract(living.getPos()).normalize().multiply( Math.min(0.8/this.getPos().distanceTo(living.getPos()),0.55)));
                }
            }
        }
    }

    private void implode(){
        Random r = new Random();
        for(Entity entity:entities){
            ((LivingEntity)entity).setVelocity(new Vec3d(r.nextInt(-5,5)*0.45,r.nextInt(-5,5)*0.45,r.nextInt(-5,5)*0.45).multiply(Math.min(1/this.getPos().distanceTo(entity.getPos()),1)));
        }
    }
    /*
            Entity prev=null;
        if (prev != null){
            entity.startRiding(prev);
        }
        prev=entity;
     */

    private void getHits(){
        Box box = new Box(this.getX() - 20, this.getY() - 20, this.getZ() - 20, this.getX() + 20, this.getY() + 20, this.getZ() + 20);
        this.entities = this.getWorld().getOtherEntities(this, box, entity -> entity instanceof LivingEntity && !(entity instanceof PlayerEntity));
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.dataTracker.get(HIT)){
            this.setData();
            return;
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.dataTracker.get(HIT)){
            this.setData();
            return;
        }
        super.onBlockHit(blockHitResult);
    }
    private void setData(){
        this.dataTracker.set(HIT,true);
        this.setNoGravity(true);
        this.age = 0;
    }

    @Override
    public boolean isCollidable() {
        return this.dataTracker.get(HIT);
    }



    @Override
    protected Item getDefaultItem() {
        return ModItems.VACUUM_RELIC;
    }
}
