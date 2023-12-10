package com.bajookie.echoes_of_the_elders.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MidasHammerItem extends PickaxeItem {
    public MidasHammerItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }


    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.getItemCooldownManager().set(this, 20 * 6);
            entity.getWorld().addParticle(ParticleTypes.SMOKE, entity.getX(), entity.getY(), entity.getZ(), 0, 1, 0);
            if (entity.getHealth() <= 50) {
                int hp = Math.round(entity.getHealth());
                int nugget = hp % 9;
                int ingot = (hp - nugget) / 9;
                ItemEntity item = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GOLD_INGOT, ingot));
                ItemEntity itemN = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GOLD_NUGGET, nugget));
                entity.getWorld().spawnEntity(item);
                entity.getWorld().spawnEntity(itemN);
                user.getWorld().playSound(user, entity.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.AMBIENT, 1, 1);
            }
            entity.damage(entity.getWorld().getDamageSources().magic(), 50f);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random r = new Random();
        if (r.nextInt(10) == 5) {
            ItemEntity item = new ItemEntity(target.getWorld(), target.getX(), target.getY(), target.getZ(), new ItemStack(Items.GOLD_NUGGET, 1));
            target.getWorld().spawnEntity(item);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.midas_hammer.attack"));
        tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.midas_hammer.effect"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
