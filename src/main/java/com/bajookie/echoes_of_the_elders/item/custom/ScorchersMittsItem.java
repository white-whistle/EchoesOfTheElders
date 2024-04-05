package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
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

public class ScorchersMittsItem extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward, GloveItem {

    protected static StackedItemStat.Float EFFECT_RANGE = new StackedItemStat.Float(16f, 64f);
    protected static StackedItemStat.Float FIRE_MODIFIER = new StackedItemStat.Float(1f, 5f);
    protected static StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 5, 50);

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
        Box box = new Box(new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ())).expand(EFFECT_RANGE.get(stack));

        var entities = world.getOtherEntities(user, box);
        var triggered = false;

        for (Entity entity : entities) {
            var fireTicks = entity.getFireTicks();
            var isOnFire = entity.isOnFire();
            if (!isOnFire) continue;

            var fireInstances = Math.max(fireTicks / 20, 1);

            entity.damage(entity.getDamageSources().onFire(), fireInstances * FIRE_MODIFIER.get(stack));
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

    public static Ability FIRE_SNAP_ABILITY = new Ability("fire_snap", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("multiplier", FIRE_MODIFIER.get(stack)));
            section.line("info2");
            section.line("info3", new TextArgs().putF("distance", EFFECT_RANGE.get(stack)));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static Ability SCORCHING_TOUCH_ABILITY = new Ability("scorching_touch", Ability.AbilityType.ON_HIT, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static List<Ability> ABILITIES = List.of(FIRE_SNAP_ABILITY, SCORCHING_TOUCH_ABILITY);
    public static List<TooltipSection> INFO = List.of(GloveItem.GLOVE_INFO);

    @Override
    public List<Ability> getAbilities(ItemStack stack) {
        return ABILITIES;
    }

    @Override
    public List<TooltipSection> getAdditionalInfo(ItemStack stack) {
        return INFO;
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return COOLDOWN.get(stack);
    }
}
