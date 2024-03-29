package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class RadiantLotusItem extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {
    protected StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 60 * 5, 60);
    protected StackedItemStat.Float HEAL_PERCENT = new StackedItemStat.Float(0.1f, 0.25f);
    protected StackedItemStat.Int HUNGER_RESTORE = new StackedItemStat.Int(2, 4);

    private final TargetPredicate targetPredicate = TargetPredicate.createNonAttackable();

    public RadiantLotusItem() {
        super(new ArtifactItemSettings());
    }

    public void removePlayerNegativeEffects(PlayerEntity player) {
        Collection<StatusEffectInstance> effects = player.getStatusEffects();
        for (StatusEffectInstance effect : effects) {
            if (!effect.getEffectType().isBeneficial()) {
                player.removeStatusEffect(effect.getEffectType());
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            var stack = user.getStackInHand(hand);
            user.getItemCooldownManager().set(this, this.getCooldown(stack));

            var hungerRestore = HUNGER_RESTORE.get(stack);
            var healPercent = HEAL_PERCENT.get(stack);

            var players = world.getPlayers(targetPredicate, null, user.getBoundingBox().expand(20, 12, 20));
            players.forEach(player -> {
                removePlayerNegativeEffects(player);
                player.heal(player.getMaxHealth() * healPercent);
                player.getHungerManager().add(hungerRestore, hungerRestore);
            });

            if (world.isClient) {
                MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
            }

            world.playSound(user, user.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1f, 0.5f);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var hungerRestore = HUNGER_RESTORE.get(stack);
        var healPercent = HEAL_PERCENT.get(stack);

        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.radiant_lotus.effect.info1"));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.radiant_lotus.effect.info2", new TextArgs().putF("heal_percent", healPercent * 100).putI("hunger_amount", hungerRestore)));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.radiant_lotus.effect.info3"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return COOLDOWN.get(stack);
    }
}
