package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.IHasToggledEffect;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.util.InventoryUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bajookie.echoes_of_the_elders.system.ItemStack.CustomItemNbt.EFFECT_ENABLED;

@DropCondition.RaidLevelBetween(max = 100)
public class WithersBulwark extends Item implements IArtifact, IHasCooldown, IStackPredicate, IHasToggledEffect, IRaidReward {
    private final StackedItemStat.Int cooldown = new StackedItemStat.Int(10 * 20, 10);

    public WithersBulwark() {
        super(new ArtifactItemSettings());
    }

    public static Ability WITHER_SCALES_ABILITY = new Ability("wither_scales", Ability.AbilityType.PASSIVE, Ability.AbilityTrigger.TOGGLED) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
            if (StackLevel.isMaxed(stack)) {
                section.line("info2");
            }
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(WITHER_SCALES_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
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
