package com.bajookie.echoes_of_the_elders.system.Text;

import com.bajookie.echoes_of_the_elders.util.sided.ShiftingUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public abstract class TooltipSection {

    public String name;

    public TooltipSection(String name) {
        this.name = name;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(title(stack, world, tooltip, context));

        var ctx = new TooltipSectionContext() {
            @Override
            public TooltipSectionContext line(String id, TextArgs args) {
                tooltip.add(TooltipSection.this.info(TextUtil.translatable(name + "." + id, args)));
                return this;
            }
        };

        var shifting = ShiftingUtil.isShifting();

        if (!shifting) {
            appendTooltipInfo(stack, world, tooltip, context, ctx);
        }
    }

    public MutableText title(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        return TextUtil.translatable(name + ".name");
    }

    public MutableText info(MutableText text) {
        return TextUtil.translatable("section.impl.info_template", new TextArgs().put("line", text));
    }

    public abstract void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section);

    public interface TooltipSectionContext {
        TooltipSectionContext line(String id, TextArgs args);

        default TooltipSectionContext line(String id) {
            line(id, null);
            return this;
        }
    }

    public static abstract class Info extends TooltipSection {

        public Info(String name) {
            super("info." + MOD_ID + "." + name);
        }

        @Override
        public MutableText title(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
            return TextUtil.withIcon(ModText.INFO, super.title(stack, world, tooltip, context));
        }
    }

}
