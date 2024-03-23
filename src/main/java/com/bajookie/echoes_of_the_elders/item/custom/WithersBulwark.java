package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.IHasToggledEffect;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.InventoryUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bajookie.echoes_of_the_elders.system.ItemStack.CustomItemNbt.EFFECT_ENABLED;

public class WithersBulwark extends Item implements IArtifact, IHasCooldown, IStackPredicate, IHasToggledEffect {
    private final StackedItemStat.Int cooldown = new StackedItemStat.Int(10 * 20, 10);

    public WithersBulwark() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(
                TextUtil.join(
                        TextUtil.translatable("tooltip.echoes_of_the_elders.wither_scales_item.effect.name"),
                        IHasToggledEffect.getText(stack)
                )
        );
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.wither_scales_item.effect.info1"));
        if (StackLevel.isMaxed(stack)) {
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.wither_scales_item.effect.info2"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return cooldown.get(itemStack);
    }

    public static boolean handleDamage(PlayerEntity user, DamageSource source, float amount) {
        Entity entity = source.getSource();
        PlayerInventory inventory = user.getInventory();

        var stack = StackLevel.getBest(InventoryUtil.toStream(inventory).filter(s -> s.isOf(ModItems.WITHERS_BULWARK) && EFFECT_ENABLED.get(s)));
        if (stack == null) return false;

        var cdm = user.getItemCooldownManager();
        if (cdm.isCoolingDown(ModItems.WITHERS_BULWARK)) return false;

        if (entity instanceof PersistentProjectileEntity) {
            cdm.set(ModItems.WITHERS_BULWARK, ModItems.WITHERS_BULWARK.getCooldown(stack));
            if (StackLevel.isMaxed(stack)) {
                var attacker = source.getAttacker();
                if (attacker != null) {
                    attacker.damage(source, amount);
                }

            }
            return true;
        }

        return false;
    }
}
