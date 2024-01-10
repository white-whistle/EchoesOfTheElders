package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RefresherRelic extends Item {
    private final Type type;
    public RefresherRelic(Type type) {
        super(new FabricItemSettings().maxCount(16));
        this.type=type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)){
            List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(user.getInventory().main, user.getInventory().armor, user.getInventory().offHand);
            for (DefaultedList<ItemStack> list: combinedInventory){
                for (ItemStack item:list){
                    if (user.getItemCooldownManager().isCoolingDown(item.getItem())){
                        user.getItemCooldownManager().remove(item.getItem());
                    }
                }
            }
            if (this.type == Type.Relic){
                user.getItemCooldownManager().set(this,20*60*5);
            }
        }
        return super.use(world, user, hand);
    }
    public enum Type{
        Relic,
        WTF
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var args = new TextArgs().put("cooldown", Text.literal("600"));
        if (this.type == Type.Relic){
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.refresher_relic.info"));
        } else {
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.wtf_relic.info"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
