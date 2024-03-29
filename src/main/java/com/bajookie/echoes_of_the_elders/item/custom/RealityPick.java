package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedAttributeModifiers;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RealityPick extends PickaxeItem implements IArtifact, IHasUpscaledModel, IStackPredicate, IRaidReward {
    protected static final int MAX_COUNT = 16;
    private final StackedItemStat.Float stackedAttackDamage = new StackedItemStat.Float(4f, 16f);
    private final StackedItemStat.Float miningSpeedMultiplier = new StackedItemStat.Float(0.1f, 4f);

    protected final StackedAttributeModifiers stackedAttributeModifiers = new StackedAttributeModifiers((index) -> {
        var progress = index / (float) (MAX_COUNT - 1);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) stackedAttackDamage.get(progress), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2, EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    });

    @Override
    public int getArtifactMaxStack() {
        return MAX_COUNT;
    }

    public RealityPick() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new ArtifactItemSettings());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return stackedAttributeModifiers.get(StackLevel.get(stack) - 1);
        }
        return super.getAttributeModifiers(stack, slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.reality_pick.effect"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return super.getMiningSpeedMultiplier(stack, state) + miningSpeedMultiplier.get(stack);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }

    @Override
    public boolean shouldUseUpscaledModel(ItemStack itemStack) {
        return StackLevel.isMaxed(itemStack);
    }
}
