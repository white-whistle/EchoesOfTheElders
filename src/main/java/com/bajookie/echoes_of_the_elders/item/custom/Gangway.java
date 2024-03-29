package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.client.ModKeyBindings;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.ability.IHasSlotAbility;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
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

public class Gangway extends ArmorItem implements IArtifact, IHasSlotAbility, IHasCooldown, IStackPredicate, IRaidReward {

    private static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 60, 20 * 5);

    public Gangway() {
        super(GANGWAY_ITEM_MATERIAL, Type.HELMET, new ArtifactItemSettings());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.hotkey", new TextArgs().put("hotkey", ModKeyBindings.HELMET_ABILITY.getBoundKeyLocalizedText()).put("name", TextUtil.translatable("ability.echoes_of_the_elders.gangway.name"))));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.gangway.info1"));
        tooltip.add(TextUtil.translatable("ability.echoes_of_the_elders.gangway.info2", new TextArgs().putF("damage", DoomstickItem.ABILITY_DAMAGE.get(stack))));

        super.appendTooltip(stack, world, tooltip, context);
    }

    private static final ArmorMaterial GANGWAY_ITEM_MATERIAL = new ArmorMaterial() {
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

    @Override
    public @Nullable Ability getAbility(EquipmentSlot equipmentSlot) {
        if (equipmentSlot != EquipmentSlot.HEAD) return null;

        return DoomstickItem.ABILITY;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }
}
