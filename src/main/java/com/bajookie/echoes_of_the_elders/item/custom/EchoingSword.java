package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.mixin.PlayerEntityMixin;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackableItemSettings;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedAttributeModifiers;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class EchoingSword extends SwordItem implements IArtifact, IStackPredicate,IHasCooldown {
    private final StackedItemStat.Int stackedAttackDamage = new StackedItemStat.Int(5, 20);
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 40, 20 * 20);
    public EchoingSword() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 1.4f, new StackableItemSettings().rarity(Rarity.EPIC).maxCount(16));
    }

    private final StackedAttributeModifiers stackedAttributeModifiers = new StackedAttributeModifiers(index -> {
        var progress = index / (float) (16 - 1);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) stackedAttackDamage.get(progress), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", 1.4f, EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    });
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0f;
        }
        return state.isIn(BlockTags.SWORD_EFFICIENT) ? 1.5f : 1.0f;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        if (attacker instanceof PlayerEntity user){
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.ECHO_HIT,10,this.stackedAttackDamage.get(stack),true,false,false));
                user.getItemCooldownManager().set(this, this.getCooldown(stack));
            }

        }
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0f) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return stackedAttributeModifiers.get(stack.getCount() - 1);
        }
        return super.getAttributeModifiers(stack, slot);
    }



    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return this.cooldown.get(itemStack);
    }
}
