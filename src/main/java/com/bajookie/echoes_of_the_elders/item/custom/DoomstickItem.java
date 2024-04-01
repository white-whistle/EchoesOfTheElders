package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.particles.LineParticleEffect;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.HandUtil;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
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
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

public class DoomstickItem extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {

    public DoomstickItem() {
        super(new ArtifactItemSettings());
    }

    @Override
    public int getArtifactMaxStack() {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);

        ABILITY.cast(world, user, stack, false);

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.doomstick.effect", new TextArgs().putF("damage", ABILITY_DAMAGE.get(stack))));
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

    public static final StackedItemStat.Float ABILITY_DAMAGE = new StackedItemStat.Float(20f, 250f);
    public static final Ability ABILITY = new Ability("doom_beam", Ability.AbilityType.ACTIVE) {

        @Override
        public boolean cast(World world, PlayerEntity user, ItemStack stack, boolean ignoreCooldown) {
            var item = stack.getItem();
            var ret = VectorUtil.raycastWithBlocks(user, 32);
            if (ret == null) return false;
            if (!ignoreCooldown && user.getItemCooldownManager().isCoolingDown(item)) return false;

            var entity = ret.getEntity();

            Vec3d userPos = user.getPos();
            Vec3d entityPos = new Vec3d(entity.getPos().toVector3f()).add(0, entity.getHeight() / 2, 0);
            Vec3d diff = userPos.subtract(entityPos);
            Random r = new Random();

            if (!world.isClient) {
                ServerWorld serverWorld = (ServerWorld) user.getWorld();

                var startPos = user.getEyePos();

                var up = new Vector3f(0, 1, 0);
                var right = new Vector3f(entityPos.toVector3f()).sub(startPos.toVector3f()).cross(up).normalize();

                var dir = HandUtil.getHandDir(user, HandUtil.stackToHand(user, stack));

                // beam effect
                serverWorld.spawnParticles(new LineParticleEffect(
                        new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.4f)).add(right.mul(0.6f * dir)),
                        new Vector3f((float) (entityPos.x), (float) (entityPos.y), (float) (entityPos.z)),
                        new Vector3f(255 / 255f, 184 / 255f, 117 / 255f)
                ), startPos.x, startPos.y, startPos.z, 10, 0, 0, 0, 0);

                // explosion
                serverWorld.spawnParticles(ParticleTypes.LAVA, entityPos.x, entityPos.y, entityPos.z, 30, 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 1);

                // ray fallout
                for (double i = 1; i <= 20; i++) {
                    serverWorld.spawnParticles(ParticleTypes.LAVA, userPos.x - (diff.x * (i / 20)), userPos.y - (diff.y * (i / 20)) + 1, userPos.z - (diff.z * (i / 20)), 1, 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 0.1 * ((float) r.nextInt(11) / 10), 1);
                }


                entity.damage(serverWorld.getDamageSources().create(DamageTypes.MAGIC, user), DoomstickItem.ABILITY_DAMAGE.get(stack));
            }

            user.getWorld().playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 5f, 0.2f);

            if (!ignoreCooldown && item instanceof IHasCooldown iHasCooldown) {
                user.getItemCooldownManager().set(item, iHasCooldown.getCooldown(stack));
            }

            return true;
        }
    };
}
