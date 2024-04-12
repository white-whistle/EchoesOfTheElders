package com.bajookie.echoes_of_the_elders.item.ability;

import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import com.bajookie.echoes_of_the_elders.util.sided.ShiftingUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipHelper {
    List<Text> tooltip;
    ItemStack stack;
    World world;
    TooltipContext context;

    public TooltipHelper(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        this.stack = stack;
        this.world = world;
        this.tooltip = tooltip;
        this.context = context;
    }

    public TooltipHelper section(TooltipSection section) {
        section.appendTooltip(stack, world, tooltip, context);
        return this;
    }

    public TooltipHelper emptyLine() {
        tooltip.add(Text.empty());
        return this;
    }

    public TooltipHelper sections(TooltipSection... sections) {
        var shifting = ShiftingUtil.isShifting();

        int i = 0;
        for (var s : sections) {
            if (i > 0 && !shifting) emptyLine();
            section(s);
            i++;
        }
        return this;
    }
}
