package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.custom.OrbOfAnnihilation;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.s2c.CapabilitySync;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.bajookie.echoes_of_the_elders.system.ItemStack.CustomItemNbt.EFFECT_ENABLED;

public class OrbOfAnnihilationEntity extends ProjectileEntity implements FlyingItemEntity, Mount {

    private static final TrackedData<ItemStack> STACK = DataTracker.registerData(OrbOfAnnihilationEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public OrbOfAnnihilationEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.speed = 0.5f;
    }

    public void setStack(ItemStack itemStack) {
        this.dataTracker.set(STACK, itemStack);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(STACK, ItemStack.EMPTY);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
    }

    public OrbOfAnnihilationEntity(World world, double x, double y, double z, float speed, LivingEntity owner) {
        super(ModEntities.ORB_OF_ANNIHILATION_ENTITY_TYPE, world);
        this.setOwner(owner);
        this.setPosition(x, y, z);
        this.prevPitch = owner.getPitch();
        this.prevYaw = owner.getYaw();
        this.setRotation(owner.getYaw(), owner.getPitch());
        this.speed = speed;
        this.setVelocity(0, 0.8, 0);
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
        if (this.age < 20) {
            this.setPosition(this.getPos().add(this.getVelocity()));
        } else if (this.age < 300) {
            var pass = this.getControllingPassenger();
            if (pass != null) {
                Vec3d desiredDirection = new Vec3d(VectorUtil.pitchYawRollToDirection(pass.getPitch(), pass.getYaw(), pass.getRoll()));
                this.updateRotation();
                Vec3d newDirection = this.getVelocity().normalize().add(desiredDirection.multiply(0.13)).normalize();
                this.setVelocity(newDirection.multiply(1.3));
                this.setPosition(this.getPos().add(this.getVelocity()));
            } else {
                Vec3d desiredDirection = new Vec3d(0, 0, 0);
                this.updateRotation();
                Vec3d newDirection = this.getVelocity().normalize().add(desiredDirection.multiply(0.13)).normalize();
                this.setVelocity(newDirection.multiply(1.3));
                this.setPosition(this.getPos().add(this.getVelocity()));
            }

            if (this.age == 280) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GHAST_WARN, SoundCategory.PLAYERS, 1f, 1f, true);
            }
        } else {
            this.detonate();
        }
        if (!this.getWorld().isClient) {
            ((ServerWorld) this.getWorld()).spawnParticles(ModParticles.THICK_TRAIL_PARTICLE, this.prevX, this.prevY, this.prevZ, 1, 0, 0, 0, 0);
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
            this.getWorld().createExplosion(this.getOwner(), this.getX(), this.getY(), this.getZ(), OrbOfAnnihilation.EXPLOSION_POWER.get(this.getStack()), EFFECT_ENABLED.get(this.getStack()), World.ExplosionSourceType.MOB);
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
    protected boolean canAddPassenger(Entity passenger) {
        return true;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getPassengerList().isEmpty() ? null : (LivingEntity) this.getPassengerList().get(0);
    }

    @Override
    public ItemStack getStack() {
        return this.dataTracker.get(STACK);
    }
}
