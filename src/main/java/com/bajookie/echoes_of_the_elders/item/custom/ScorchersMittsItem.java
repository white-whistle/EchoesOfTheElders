package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.ParticleUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScorchersMittsItem extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {

    protected StackedItemStat.Float effectRange = new StackedItemStat.Float(16f, 64f);
    protected StackedItemStat.Float fireModifier = new StackedItemStat.Float(1f, 5f);

    public ScorchersMittsItem() {
        super(new ArtifactItemSettings());
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFire(true);
        target.setFireTicks(20 * 6);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        var stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);

        var pos = user.getPos();
        Box box = new Box(new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ())).expand(effectRange.get(stack));

        var entities = world.getOtherEntities(user, box);
        var triggered = false;

        for (Entity entity : entities) {
            var fireTicks = entity.getFireTicks();
            var isOnFire = entity.isOnFire();
            if (!isOnFire) continue;

            var fireInstances = Math.max(fireTicks / 20, 1);

            entity.damage(entity.getDamageSources().onFire(), fireInstances * fireModifier.get(stack));
            entity.setOnFire(false);
            entity.setFireTicks(0);

            ParticleUtil.particleRing(world, ParticleTypes.LAVA, entity.getPos().toVector3f(), ParticleUtil.Y, ParticleUtil.zAxis(2), ParticleUtil.zAxis(-0.2f), 36);

            triggered = true;
        }

        if (triggered) {
            world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 5f, 0.2f);

            user.getItemCooldownManager().set(this, this.getCooldown(stack));

            return TypedActionResult.success(stack, world.isClient());
        }

        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 0.2f);

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.scorchers_mitts.on_hit"));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.scorchers_mitts.effect1", new TextArgs().putF("multiplier", fireModifier.get(stack))));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.scorchers_mitts.effect2"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 20 * 5;
    }
}
