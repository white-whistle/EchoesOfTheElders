package com.bajookie.echoes_of_the_elders.item.reward;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropCondition {

    private static final HashMap<Item, List<DropConditionHandler<?>>> registry = new HashMap<>();

    public static void init() {
        ModItems.registeredModItems.forEach(item -> {
            if (item instanceof IRaidReward) {
                register(item);
            }
        });
    }

    public static boolean canDrop(Item item, IRaidReward.RaidRewardDropContext ctx) {
        if (!registry.containsKey(item)) return false;

        var dropConditions = registry.get(item);

        return dropConditions.stream().allMatch(condition -> condition.canDrop(ctx));
    }

    public static void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var item = stack.getItem();
        if (!registry.containsKey(item)) return;

        var dropConditions = registry.get(item);
        if (dropConditions.size() == 0) return;

        tooltip.add(Text.empty());
        tooltip.add(TextUtil.translatable("raid_drops.echoes_of_the_elders.title"));
        dropConditions.forEach(condition -> condition.appendTooltip(stack, world, tooltip, context));
    }

    private static void register(Item item) {
        var clazz = item.getClass();
        var annotations = clazz.getAnnotations();

        var handlers = new ArrayList<DropConditionHandler<?>>();

        for (var anno : annotations) {
            if (anno instanceof RaidLevelBetween raidLevelBetween) {
                handlers.add(new RaidLevelBetween.Handler(raidLevelBetween));
            }
        }

        registry.put(item, handlers);
    }

    public abstract static class DropConditionHandler<T> {
        public T config;

        public DropConditionHandler(T config) {
            this.config = config;
        }

        public abstract boolean canDrop(IRaidReward.RaidRewardDropContext ctx);

        void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface RaidLevelBetween {
        int min() default 0;

        int max() default Integer.MAX_VALUE;
        
        class Handler extends DropConditionHandler<RaidLevelBetween> {
            public Handler(RaidLevelBetween config) {
                super(config);
            }

            @Override
            public boolean canDrop(IRaidReward.RaidRewardDropContext ctx) {
                var level = ctx.level();
                var min = config.min();
                var max = config.max();

                return level >= min && level < max;
            }

            @Override
            void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                var args = new TextArgs().putI("min", config.min()).putI("max", config.max());

                if (config.min() == 0) {
                    tooltip.add(TextUtil.translatable("raid_drops.echoes_of_the_elders.level.lt", args));
                } else if (config.max() == Integer.MAX_VALUE) {
                    tooltip.add(TextUtil.translatable("raid_drops.echoes_of_the_elders.level.gt", args));
                } else {
                    tooltip.add(TextUtil.translatable("raid_drops.echoes_of_the_elders.level", args));
                }
            }
        }
    }

}
