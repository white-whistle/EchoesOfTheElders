package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedAttributeModifiers;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 15, max = 100)
public class EchoingSword extends SwordItem implements IArtifact, IStackPredicate, IHasCooldown, IRaidReward {
    public static final StackedItemStat.Int ATTACK_DAMAGE = new StackedItemStat.Int(5, 16);
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 20, 20 * 5);

    public EchoingSword() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, -1.4f, new ArtifactItemSettings());
    }

    private final StackedAttributeModifiers stackedAttributeModifiers = new StackedAttributeModifiers(index -> {
        var progress = index / (float) (16 - 1);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) ATTACK_DAMAGE.get(progress), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -1.4f, EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    });

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0f;
        }
        return state.isIn(BlockTags.SWORD_EFFICIENT) ? 1.5f : 1.0f;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return stackedAttributeModifiers.get(StackLevel.get(stack) - 1);
        } else {
            return super.getAttributeModifiers(stack, slot);
        }
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public static Ability ECHOED_STRIKE_ABILITY = new Ability("echoed_strike", Ability.AbilityType.ON_HIT, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static List<Ability> ABILITIES = List.of(ECHOED_STRIKE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack stack) {
        return ABILITIES;
    }
}
