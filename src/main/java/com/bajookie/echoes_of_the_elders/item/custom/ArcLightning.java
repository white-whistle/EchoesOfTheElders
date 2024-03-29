package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArcLightning extends Item implements IArtifact, IStackPredicate, IHasCooldown, IRaidReward {
    protected final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 40, 20 * 20);
    protected final StackedItemStat.Int ATTACKS = new StackedItemStat.Int(4, 20);
    protected final StackedItemStat.Float STUN_DURATION = new StackedItemStat.Float(2f, 8f);

    public ArcLightning() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Box box = new Box(user.getX() - 30, user.getY() - 5, user.getZ() - 30, user.getX() + 30, user.getY() + 5, user.getZ() + 30);
            List<Entity> list = world.getOtherEntities(user, box, (target) -> {
                if (target instanceof LivingEntity living) {
                    if (living instanceof PlayerEntity) return false;
                    return living.hasStatusEffect(ModEffects.SHOCK_EFFECT);
                }
                return false;
            });
            for (Entity entity : list) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffects.ELECTRIC_STUN_EFFECT, 20 * 2, 1, true, false), user);
            }
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.arc_lightning.hit", new TextArgs().putF("number_of_attacks", this.ATTACKS.get(stack)).putF("damage", 4)));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.arc_lightning.active", new TextArgs().putF("stun_seconds", STUN_DURATION.get(stack))));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
