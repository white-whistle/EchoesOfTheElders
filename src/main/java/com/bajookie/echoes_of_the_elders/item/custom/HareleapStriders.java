package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HareleapStriders extends ArmorItem implements IArtifact, IStackPredicate, IRaidReward {

    public static StackedItemStat.Int MAX_HOP_STACKS = new StackedItemStat.Int(3, 9);
    public static float HOP_STACK_SPEED_INCREASE = 0.05f;

    public HareleapStriders() {
        super(HARELEAP_STRIDERS_MATERIAL, Type.BOOTS, new ArtifactItemSettings());
    }

    public static final Ability BUNNY_HOP_ABILITY = new Ability("bunny_hop", Ability.AbilityType.PASSIVE) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");

        }
    };

    public static final TooltipSection HOP_INFO = new TooltipSection.Info("hop") {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putI("speed_percent", (int) (HOP_STACK_SPEED_INCREASE * 100)));
            section.line("info2", new TextArgs().putI("max_stacks", MAX_HOP_STACKS.get(stack)));
        }
    };

    public static final List<Ability> ABILITIES = List.of(BUNNY_HOP_ABILITY);
    public static final List<TooltipSection> INFO = List.of(HOP_INFO);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public List<TooltipSection> getAdditionalInfo(ItemStack itemStack) {
        return INFO;
    }

    private static final ArmorMaterial HARELEAP_STRIDERS_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurability(Type type) {
            return 0;
        }

        @Override
        public int getProtection(Type type) {
            return 1;
        }

        @Override
        public int getEnchantability() {
            return 1;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }

        @Override
        public String getName() {
            return new Identifier(EOTE.MOD_ID, "hareleap").toString();
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    public void handleJump(LivingEntity entity, ItemStack stack) {
        var statusInstance = entity.getStatusEffect(ModEffects.HOP);
        var amp = 0;
        if (statusInstance != null) {
            amp = Math.min(statusInstance.getAmplifier() + 1, MAX_HOP_STACKS.get(stack) - 1);
        }

        entity.addStatusEffect(new StatusEffectInstance(ModEffects.HOP, 20 * 4, amp));
    }

}
