package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.Raid.networking.c2s.C2SSyncItemCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 50)
public class ArtifactHammer extends MagicHammer implements IArtifact, IHasCooldown, IRaidReward {
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * (60 * 60) * 3, 20 * (60 * 30));

    public ArtifactHammer() {
        super(new ArtifactItemSettings());
    }

    @Override
    public ClickResult canUpgrade(PlayerEntity user, ItemStack self, ItemStack other) {
        if (user.getItemCooldownManager().isCoolingDown(self.getItem())) {
            if (other.isOf(this)) {
                return ClickResult.FORWARD;
            }

            return fail(user, self, other);
        }

        return super.canUpgrade(user, self, other);
    }

    @Override
    public boolean isOneTimeUse() {
        return false;
    }

    @Override
    public void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor) {
        StackLevel.raise(other, 1);

        user.getItemCooldownManager().set(self.getItem(), getCooldown(self));

        if (user.getWorld().isClient) {
            C2SSyncItemCooldown.send(self.getItem(), getCooldown(self));
        }
    }

    @Override
    public int getArtifactMaxStack() {
        return 128;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public static final List<Ability> ABILITIES = List.of(ARTISANS_TOUCH_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
