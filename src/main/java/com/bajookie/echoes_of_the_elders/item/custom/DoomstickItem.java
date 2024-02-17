package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.particles.LineParticleEffect;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

public class DoomstickItem extends Item implements IArtifact, IHasCooldown, IStackPredicate {

    protected StackedItemStat.Float effectDamage = new StackedItemStat.Float(20f, 250f);

    public DoomstickItem() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public int getArtifactMaxStack() {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var ret = VectorUtil.raycastWithBlocks(user, 32);

        if (ret != null) {
            var entity = ret.getEntity();
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                Vec3d userPos = user.getPos();
                Vec3d entityPos = new Vec3d(entity.getPos().toVector3f()).add(0, entity.getHeight() / 2, 0);
                Vec3d diff = userPos.subtract(entityPos);
                Random r = new Random();

                if (!world.isClient) {
                    ServerWorld serverWorld = (ServerWorld) user.getWorld();

                    var startPos = user.getEyePos();

                    var up = new Vector3f(0, 1, 0);
                    var right = new Vector3f(entityPos.toVector3f()).sub(startPos.toVector3f()).cross(up).normalize();

                    // beam effect
                    serverWorld.spawnParticles(new LineParticleEffect(
                            new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.4f)).add(right.mul(0.6f)),
                            new Vector3f((float) (entityPos.x), (float) (entityPos.y), (float) (entityPos.z)),
                            new Vector3f(255 / 255f, 184 / 255f, 117 / 255f)
                    ), startPos.x, startPos.y, startPos.z, 10, 0, 0, 0, 0);

                    // explosion
                    serverWorld.spawnParticles(ParticleTypes.LAVA, entityPos.x, entityPos.y, entityPos.z, 30, 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 1);

                    // ray fallout
                    for (double i = 1; i <= 20; i++) {
                        serverWorld.spawnParticles(ParticleTypes.LAVA, userPos.x - (diff.x * (i / 20)), userPos.y - (diff.y * (i / 20)) + 1, userPos.z - (diff.z * (i / 20)), 1, 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 1);
                    }

                    user.getItemCooldownManager().set(this, this.getCooldown(stack));
                    entity.damage(serverWorld.getDamageSources().create(DamageTypes.MAGIC, user), this.effectDamage.get(stack));
                }

                user.getWorld().playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 5f, 0.2f);

                return TypedActionResult.success(stack, world.isClient());
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.doomstick.effect", new TextArgs().putF("damage", effectDamage.get(stack))));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return 20 * 20;
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }

    //    @Override
    //    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    //        if (!user.getItemCooldownManager().isCoolingDown(this)) {
    //            Entity entity = RayBox.getCollidedLivingEntity(user,world,10);
    //            if(entity != null){
    //                Vec3d userPos = user.getPos();
    //                Vec3d entityPos = entity.getPos();
    //                Vec3d diff = userPos.subtract(entityPos);
    //                for (double i = 1; i <= 40; i++) {
    //                    user.getWorld().addParticle(ParticleTypes.LAVA, userPos.x - (diff.x * (i / 40)), userPos.y - (diff.y * (i / 40)) + 1, userPos.z - (diff.z * (i / 40)), 0, 0.1, 0);
    //                }
    //                user.getItemCooldownManager().set(this, 20 * 20);
    //                entity.setOnFireFor(4);
    //                entity.damage(entity.getWorld().getDamageSources().magic(), 30f);
    //            }
    //        }
    //        return super.use(world, user, hand);
    //    }
}
