package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SparkingMitts extends Item implements IArtifact, IStackPredicate, IHasCooldown, IRaidReward, GloveItem {
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 40, 20 * 20);
    public static final StackedItemStat.Int ATTACKS = new StackedItemStat.Int(4, 20);
    public static final StackedItemStat.Float STUN_DURATION = new StackedItemStat.Float(2f, 8f);
    public static final float SPARK_DAMAGE = 4;

    public SparkingMitts() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);

        Box box = new Box(user.getX() - 30, user.getY() - 5, user.getZ() - 30, user.getX() + 30, user.getY() + 5, user.getZ() + 30);
        List<Entity> list = world.getOtherEntities(user, box, (target) -> {
            if (target instanceof LivingEntity living) {
                if (living instanceof PlayerEntity) return false;
                if (living.isDead()) return false;
                return living.hasStatusEffect(ModEffects.SHOCK_EFFECT);
            }
            return false;
        });

        var cdm = user.getItemCooldownManager();
        if (!cdm.isCoolingDown(this) && list.size() > 0) {
            if (!world.isClient) {
                for (Entity entity : list) {
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffects.ELECTRIC_STUN_EFFECT, (int) (STUN_DURATION.get(stack) * 20), 1, true, false), user);

                    var ePos = entity.getPos();
                    ((ServerWorld) world).spawnParticles(ModParticles.LIGHTNING_PARTICLE, ePos.x, ePos.y + (entity.getHeight() / 2), ePos.z, 20, 1, 0, 1, 0);
                }
            }

            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 1);
            cdm.set(this, COOLDOWN.get(stack));
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            target.addStatusEffect(new StatusEffectInstance(ModEffects.CONDUCTING_EFFECT, 5, ATTACKS.get(stack), true, false));
        }
        return false;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public static Ability OVERCHARGE_ABILITY = new Ability("overcharge", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().put("duration", TextUtil.formatTime((int) (STUN_DURATION.get(stack) * 20))));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static Ability SPARKING_TOUCH_ABILITY = new Ability("sparking_touch", Ability.AbilityType.ON_HIT, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("number_of_attacks", ATTACKS.get(stack)).putF("damage", SPARK_DAMAGE));
        }
    };

    public static List<Ability> ABILITIES = List.of(OVERCHARGE_ABILITY, SPARKING_TOUCH_ABILITY);
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
    public Model getBaseModel() {
        return Models.HANDHELD;
    }
}
