package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.MagmaBullet;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoltenChamber extends Item implements IArtifact, IStackPredicate, IHasCooldown {
    protected final StackedItemStat.Int reloadSpeed = new StackedItemStat.Int(10 * 6, 20 * 3);
    protected final StackedItemStat.Int shotDamage = new StackedItemStat.Int(10,30);
    protected final StackedItemStat.Int meltThrow = new StackedItemStat.Int(0,4);

    public MoltenChamber() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 10 * 6;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.setCurrentHand(hand);
        }
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            if (!world.isClient) {
                endUse(player, 0, stack, world);
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    private void endUse(PlayerEntity player, int remainingUseTicks, ItemStack stack, World world) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.1f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        int damage = (6 - remainingUseTicks / 10);
        if (damage == 0) damage =1;
        MagmaBullet bullet = new MagmaBullet(world,player.getEyePos().x,player.getEyePos().y,player.getEyePos().z,damage*this.shotDamage.get(stack),player,player.getPitch(),player.getYaw());
        bullet.setVelocity(player,player.getPitch(),player.getYaw(),player.getRoll(),4f,0);
        world.spawnEntity(bullet);
        player.getItemCooldownManager().set(this, this.reloadSpeed.get(stack));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player){
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                if (!world.isClient) {
                    endUse(player, remainingUseTicks, stack, world);
                }
            }
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return reloadSpeed.get(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.molten_chamber.shot_min", new TextArgs().putF("damage_min", this.shotDamage.get(stack))));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.molten_chamber.shot_max", new TextArgs().putF("damage_max",6* this.shotDamage.get(stack))));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.molten_chamber.shot_wall", new TextArgs().putF("melt",this.meltThrow.get(stack))));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
