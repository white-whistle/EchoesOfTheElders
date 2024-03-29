package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.particles.LinkParticleEffect;
import com.bajookie.echoes_of_the_elders.particles.ZapParticleEffect;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class LifeLinkRelic extends Item {
    public LifeLinkRelic() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        if (!world.isClient) {
            Vec3d startPos = user.getEyePos();
            Vec3d _end = new Vec3d(VectorUtil.pitchYawRollToDirection(user.getPitch(), user.getYaw(), user.getRoll())).normalize().multiply(8);
            Vec3d end = startPos.add(_end);
            ServerWorld serverWorld = (ServerWorld) world;
            var up = new Vector3f(0, 1, 0);
            var right = new Vector3f(end.toVector3f()).sub(startPos.toVector3f()).cross(up).normalize();
            serverWorld.spawnParticles(new LinkParticleEffect(
                    new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.4f)).add(right.mul(0.6f)),
                    new Vector3f((float) (end.x), (float) (end.y), (float) (end.z)),
                    new Vector3f(255f / 255, 5f / 255, 20f / 255)
            ), startPos.x, startPos.y, startPos.z, 1, 0, 0, 0, 0);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
