package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.custom.OrbOfLightning;
import com.bajookie.echoes_of_the_elders.mixin.ThrownItemEntityAccessor;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class OrbOfLightningProjectileEntity extends ThrownItemEntity {
    public OrbOfLightningProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public OrbOfLightningProjectileEntity(World world, LivingEntity livingEntity) {
        super(ModEntities.CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE, livingEntity, world);
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
    public void setItem(ItemStack item) {
        var ITEM = ((ThrownItemEntityAccessor) this).getITEM();

        this.getDataTracker().set(ITEM, item.copy());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            hit(entityHitResult.getPos());
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            hit(blockHitResult.getPos());
        }
        super.onBlockHit(blockHitResult);
    }

    private void hit(Vec3d pos) {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            Box box = new Box(new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ())).expand(20);
            var entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
            int delay = 0;
            int amp = OrbOfLightning.LIGHTNING_STRIKES.get(this.getItem());
            for (LivingEntity entity : entities) {
                if (entity instanceof PlayerEntity) continue;
                if (ModCapabilities.RAID_OBJECTIVE.hasCapability(entity)) continue;
                if (this.getOwner() instanceof LivingEntity living) {
                    entity.setAttacker(living);
                }
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.DELAYED_LIGHTNING_EFFECT, delay, amp));
                delay += 5;
            }
            this.discard();
        }
    }

    @Override
    public int getDefaultPortalCooldown() {
        return super.getDefaultPortalCooldown();
    }
}
