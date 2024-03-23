package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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

public class AtlasGreaves extends ArmorItem implements IArtifact, IStackPredicate {

    public AtlasGreaves() {
        super(ATLAS_GREAVES_MATERIAL, Type.LEGGINGS, new FabricItemSettings().rarity(Rarity.RARE).maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.atlas_greaves.grounded.info1"));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.atlas_greaves.grounded.info2"));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.atlas_greaves.grounded.info3"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    public static boolean isEffectActive(LivingEntity entity) {
        var legStack = entity.getEquippedStack(EquipmentSlot.LEGS);
        return legStack.isOf(ModItems.ATLAS_GREAVES) && entity.isOnGround();
    }

    private static final ArmorMaterial ATLAS_GREAVES_MATERIAL = new ArmorMaterial() {
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
            return new Identifier(EOTE.MOD_ID, "gangway").toString();
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

}
