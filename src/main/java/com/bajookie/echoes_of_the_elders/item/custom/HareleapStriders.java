package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HareleapStriders extends ArmorItem implements IArtifact, IStackPredicate {

    public static StackedItemStat.Int MAX_HOP_STACKS = new StackedItemStat.Int(3, 9);
    public static float HOP_STACK_SPEED_INCREASE = 0.1f;

    public HareleapStriders() {
        super(HARELEAP_STRIDERS_MATERIAL, Type.BOOTS, new FabricItemSettings().rarity(Rarity.RARE).maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.hareleap_striders.hop.info1"));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.hareleap_striders.hop.info2", new TextArgs().putI("speed_percent", (int) (HOP_STACK_SPEED_INCREASE * 100))));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.hareleap_striders.hop.info3", new TextArgs().putI("max_stacks", MAX_HOP_STACKS.get(stack))));

        super.appendTooltip(stack, world, tooltip, context);
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

        entity.addStatusEffect(new StatusEffectInstance(ModEffects.HOP, 20 * 10, amp));
    }

}
