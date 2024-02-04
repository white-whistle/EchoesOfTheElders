package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.s2c.CapabilitySync;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class TvArrowEntity extends ProjectileEntity implements FlyingItemEntity,Mount {
    public TvArrowEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.speed = 0.5f;
    }
    @Override
    protected void initDataTracker() {
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
    }

    public TvArrowEntity(World world, double x, double y, double z, float speed, LivingEntity owner) {
        super((EntityType<? extends ProjectileEntity>) ModEntities.TV_ARROW_ENTITY_ENTITY_TYPE, world);
        this.setOwner(owner);
        this.setPosition(x, y, z);
        this.prevPitch = owner.getPitch();
        this.prevYaw = owner.getYaw();
        this.setRotation(owner.getYaw(), owner.getPitch());
        this.speed = speed;
        this.refreshPositionAndAngles(x, y, z, owner.getYaw(), owner.getPitch());
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (!this.noClip) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        if (this.age<20){
            this.setPosition(this.getPos().add(this.getVelocity()));
        }
        else if (this.age < 300) {
            var pass = (LivingEntity) this.getOwner();
            if (pass != null) {
                Vec3d desiredDirection = new Vec3d(0,0,0);
                if (this.getControllingPassenger() != null){
                    System.out.println("pass");
                    desiredDirection = new Vec3d(VectorUtil.pitchYawRollToDirection(pass.getPitch(), pass.getYaw(), pass.getRoll()));
                }
                this.updateRotation();
                Vec3d newDirection = this.getVelocity().normalize().add(desiredDirection.multiply(0.13)).normalize();
                this.setVelocity(newDirection.multiply(1.3));
                this.setPosition(this.getPos().add(this.getVelocity()));
            } else {
                this.discard();
            }
        } else {
            this.discard();
        }
        if (!this.getWorld().isClient) {
            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.POOF, this.prevX, this.prevY, this.prevZ, 1, 0, 0, 0, 0);
            this.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
        }
    }
    private void detonate() {
        if (!this.getWorld().isClient) {
            var user = (PlayerEntity) this.getOwner();
            if (user != null) {
                ModCapabilities.SCREEN_SWITCH_OBJECTIVE.attach(user, (screenSwitchCapability -> {
                    screenSwitchCapability.setTargetScreen(null);
                }));
                CapabilitySync.send((ServerPlayerEntity) user, user);
            }
            this.getWorld().createExplosion(this.getOwner(), this.getX(), this.getY(), this.getZ(), 16, true, World.ExplosionSourceType.MOB);
        }
        this.discard();
    }

    @Override
    protected void updateRotation() {
        Vec3d vec3d = this.getVelocity();
        this.setPitch(this.updateRotationMod(this.prevPitch, (float) Math.toDegrees(MathHelper.atan2(vec3d.y * -1, MathHelper.sqrt((float) (vec3d.x * vec3d.x + vec3d.z * vec3d.z))))));
        this.setYaw(this.updateRotationMod(this.prevYaw, (float) Math.toDegrees(MathHelper.atan2(vec3d.z, vec3d.x) - MathHelper.HALF_PI)));
    }

    private float updateRotationMod(float prevRot, float newRot) {
        while (newRot - prevRot < -180.0f) {
            newRot += 360.0f;
        }
        while (newRot - prevRot >= 180.0f) {
            newRot -= 360.0f;
        }
        return MathHelper.lerp(0.15f, prevRot, newRot);
    }


    /*
        Vec3d vec3d = this.getVelocity();
        double d = vec3d.horizontalLength();
        this.setPitch(MathHelper.lerp(0.2f, this.prevPitch, (float) Math.toDegrees(MathHelper.atan2(vec3d.y*-1,MathHelper.sqrt((float) (vec3d.x*vec3d.x+vec3d.z*vec3d.z))))));
        this.setYaw(MathHelper.lerp(0.2f, this.prevYaw, (float) Math.toDegrees(MathHelper.atan2(vec3d.z,vec3d.x)-MathHelper.HALF_PI)));
        System.out.println(this.getYaw());
     */


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.detonate();
    }

    @Override
    public ItemStack getStack() {
        return Items.FIREWORK_ROCKET.getDefaultStack();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return true;
    }
}
