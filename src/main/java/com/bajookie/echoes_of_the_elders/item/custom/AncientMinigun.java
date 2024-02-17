package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.particles.LineParticleEffect;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class AncientMinigun extends Item implements IArtifact {
    public AncientMinigun() {
        super(new FabricItemSettings().maxCount(1).maxDamage(200));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 600;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks < 590) {
            int currentAmmo = stack.getDamage();
            if (currentAmmo >= this.getMaxDamage() && user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(this, 20 * 7);
            } else {
                if (!world.isClient) {
                    var hit = ProjectileUtil.getCollision(user, entity -> !entity.isSpectator() && entity.canHit(), 60);

                    if (hit != null) {
                        ServerWorld serverWorld = (ServerWorld) world;

                        Vec3d pos = hit.getPos();
                        if (hit.getType() == HitResult.Type.ENTITY && hit instanceof EntityHitResult entityHitResult) {
                            var ent = entityHitResult.getEntity();

                            ent.damage(ModDamageSources.echoAttack(user), 1);
                        }

                        var startPos = user.getEyePos();

                        var up = new Vector3f(0, 1, 0);
                        var right = new Vector3f(pos.toVector3f()).sub(startPos.toVector3f()).cross(up).normalize();


                        serverWorld.spawnParticles(new LineParticleEffect(
                                new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.4f)).add(right.mul(0.6f)),
                                new Vector3f((float) (pos.x), (float) (pos.y), (float) (pos.z)),
                                new Vector3f(250 / 255f, 245 / 255f, 182 / 255f)
                        ), startPos.x, startPos.y, startPos.z, 1, 0, 0, 0, 0);
                    }
                }
                stack.setDamage(currentAmmo + 1);
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if ((selected & !player.isUsingItem()) || !selected) {
                if (stack.getDamage() != 0) {
                    stack.setDamage(stack.getDamage() - 1);
                    if (stack.getDamage() != 0) {
                        stack.setDamage(stack.getDamage() - 1);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
