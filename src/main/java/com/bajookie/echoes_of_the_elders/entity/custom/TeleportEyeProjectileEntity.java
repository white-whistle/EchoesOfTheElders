package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;
import java.util.Set;

public class TeleportEyeProjectileEntity extends ThrownItemEntity implements FlyingItemEntity {
    private int lifeTime = 0;

    public TeleportEyeProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public TeleportEyeProjectileEntity(World world, LivingEntity livingEntity) {
        super(ModEntities.TELEPORT_EYE_PROJECTILE_ENTITY_TYPE, livingEntity, world);

    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public int getDefaultPortalCooldown() {
        return super.getDefaultPortalCooldown();
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient) {
            ServerWorld world = (ServerWorld) this.getWorld();
            this.lifeTime++;
            this.addVelocity(0, 0.03, 0);
            if (lifeTime >= 20 * 5) {
                if (!this.getWorld().isClient()) {
                    ServerWorld serverWorld = (ServerWorld) this.getWorld();
                    Box box = new Box(new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ())).expand(20);
                    var players = serverWorld.getNonSpectatingEntities(PlayerEntity.class, box);
                    EOTE.LOGGER.info(players+"");
                    players.forEach((playerEntity)->{
                        MinecraftServer minecraftServer = world.getServer();
                        var registryKey = playerEntity.getWorld().getRegistryKey();
                        if (registryKey == World.END) {
                            return;
                        }
                        var destinationDimKey = registryKey == World.OVERWORLD ? ModDimensions.DEFENSE_DIM_LEVEL_KEY : World.OVERWORLD;
                        EOTE.LOGGER.info(destinationDimKey+"");
                        ServerWorld destinationWorld = minecraftServer.getWorld(destinationDimKey);

                        if (destinationWorld != null && !playerEntity.hasVehicle()) {

                            if (playerEntity.isRemoved()) {
                                return;
                            }

                            double d = DimensionType.getCoordinateScaleFactor(playerEntity.getWorld().getDimension(), destinationWorld.getDimension());
                            WorldBorder worldBorder = destinationWorld.getWorldBorder();
                            BlockPos destinationPos = worldBorder.clamp(playerEntity.getX() * d, playerEntity.getY(), playerEntity.getZ() * d);
                            playerEntity.teleport(destinationWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), Set.of(), playerEntity.getYaw(), playerEntity.getPitch());
                        }
                    });

                }
                this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0f, 1.0f);
                this.discard();
            }
            world.spawnParticles(ParticleTypes.ENCHANT.getType(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
        super.tick();
    }
}
