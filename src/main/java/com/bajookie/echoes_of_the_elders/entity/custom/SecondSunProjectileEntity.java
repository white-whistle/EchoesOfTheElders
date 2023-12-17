package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class SecondSunProjectileEntity extends ThrownItemEntity {
    public SecondSunProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public SecondSunProjectileEntity(World world, LivingEntity livingEntity){
        super(ModEntities.SECOND_SUN_PROJECTILE_ENTITY_TYPE,livingEntity,world);
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
            ServerWorld world =(ServerWorld) this.getWorld();
            world.spawnParticles(ModParticles.SECOND_SUN_PARTICLE,pos.x,pos.y,pos.z,1,0,1,0,0);
            NbtCompound compTop = new NbtCompound();
            NbtCompound comp = new NbtCompound();
            this.writeNbt(compTop);
            if (compTop.contains(MOD_ID+"hit_data")){
                return;
            }
            comp.putDouble(MOD_ID+"x_pos",pos.x);
            comp.putDouble(MOD_ID+"y_pos",pos.y);
            comp.putDouble(MOD_ID+"z_pos",pos.z);
            comp.putInt(MOD_ID+"life",0);
            compTop.put(MOD_ID+"hit_data",comp);
            this.readNbt(compTop);
        }
    }

    @Override
    public void tick() {
        super.tick();
        NbtCompound compTop = new NbtCompound();
        this.writeNbt(compTop);
        if (compTop.contains(MOD_ID+"hit_data")){
            int life = compTop.getInt(MOD_ID+"life");
            life++;
            if (life>=60){
                discard();
                ServerWorld world =(ServerWorld) this.getWorld();
                world.spawnParticles(ModParticles.SECOND_SUN_PARTICLE,this.getPos().x,this.getPos().y,this.getPos().z,1,0,0,0,0);
            }
        }

    }

    @Override
    public int getDefaultPortalCooldown() {
        return super.getDefaultPortalCooldown();
    }
}
