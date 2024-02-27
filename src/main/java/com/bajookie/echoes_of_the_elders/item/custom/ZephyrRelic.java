package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.particles.LineParticleEffect;
import com.bajookie.echoes_of_the_elders.particles.ZapParticleEffect;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.Random;

public class ZephyrRelic extends Item {
    public ZephyrRelic() {
        super(new FabricItemSettings().maxCount(16));
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient && remainingUseTicks % 4 == 0) {

            Vec3d startPos = user.getEyePos();
            Vec3d _end = new Vec3d(VectorUtil.pitchYawRollToDirection(user.getPitch(), user.getYaw(), user.getRoll())).normalize().multiply(8);
            Vec3d end = startPos.add(_end);
            ServerWorld serverWorld = (ServerWorld) world;
            var up = new Vector3f(0, 1, 0);
            var right = new Vector3f(end.toVector3f()).sub(startPos.toVector3f()).cross(up).normalize();
            serverWorld.spawnParticles(new ZapParticleEffect(
                    new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.4f)).add(right.mul(0.6f)),
                    new Vector3f((float) (end.x), (float) (end.y), (float) (end.z)),
                    new Vector3f(92f / 255, 5f / 255, 179f / 255)
            ), startPos.x, startPos.y, startPos.z, 1, 0, 0, 0, 0);
            EntityHitResult hit = VectorUtil.raycast(user, 6, 3, 3);
            if (hit != null && (hit.getEntity() instanceof LivingEntity living)) {
                living.damage(ModDamageSources.echoAttack(user), 8);
            }
        }
        if (remainingUseTicks % 4 == 0) {
            world.playSound(user.getX(), user.getY(), user.getZ(), ModSounds.ZEPHYR_SOUND, SoundCategory.PLAYERS, 4f, 1f, false);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }
}
