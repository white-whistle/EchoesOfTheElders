package com.bajookie.echoes_of_the_elders.item.ability;

import com.bajookie.echoes_of_the_elders.system.Text.ModText;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class Ability extends TooltipSection {

    @Override
    public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSection.TooltipSectionContext section) {
    }

    public enum AbilityType {
        PASSIVE(ModText.PASSIVE_ABILITY),
        ACTIVE(ModText.ACTIVE_ABILITY),
        SPECIAL(ModText.SPECIAL_ABILITY);

        final Function<MutableText, Text> textWrapper;

        AbilityType(Function<MutableText, Text> textWrapper) {
            this.textWrapper = textWrapper;
        }
    }

    AbilityType abilityType;
    String abilityName;

    public Ability(String name, AbilityType abilityType) {
        super("ability." + MOD_ID + "." + name);
        this.abilityName = name;
        this.abilityType = abilityType;
    }

    @Override
    public MutableText title(MutableText text) {
        return (MutableText) this.abilityType.textWrapper.apply(text);
    }

    public boolean cast(World world, PlayerEntity user, ItemStack itemStack, boolean ignoreCooldown) {
        return false;
    }

}
