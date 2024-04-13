package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.mixin.ItemCooldownManagerAccessor;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 20, max = 60)
public class TimeGlyph extends Item implements IArtifact, IHasCooldown, IRaidReward {
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(1200 * 20, 60 * 20);

    public TimeGlyph() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var cdm = user.getItemCooldownManager();

        if (!cdm.isCoolingDown(this)) {
            clearCooldowns(cdm);

            cdm.set(this, this.getCooldown(stack));

            if (world.isClient) {
                MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }

    public static void clearCooldowns(ItemCooldownManager itemCooldownManager) {
        var entries = ((ItemCooldownManagerAccessor) itemCooldownManager).getEntries();
        if (entries == null) return;

        Item[] keys = entries.keySet().toArray(new Item[]{});

        for (var key : keys) {
            itemCooldownManager.remove(key);
        }
    }

    public static final Ability REFRESH_ABILITY = new Ability("refresh", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(REFRESH_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return this.cooldown.get(itemStack);
    }
}
