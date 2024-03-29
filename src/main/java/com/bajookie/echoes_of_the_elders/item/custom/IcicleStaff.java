package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.IcicleProjectile;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ILeftClickAbility;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IcicleStaff extends Item implements IArtifact, IHasCooldown, IStackPredicate, ILeftClickAbility, IRaidReward {
    private static final List<Tag> TAGS = List.of(Tag.ICE);

    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 40, 10 * 20);

    public IcicleStaff() {
        super(new ArtifactItemSettings());
    }

    @Override
    public List<Tag> getTags() {
        return TAGS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                EntityHitResult e = VectorUtil.raycastWithBlocks(user, 10);
                if (e == null && !user.getItemCooldownManager().isCoolingDown(this)) {
                    return super.use(world, user, hand);
                } else {
                    Entity entity = e.getEntity();
                    IcicleProjectile projectile = new IcicleProjectile(world, entity.getX(), entity.getY() + 10, entity.getZ(), user, true);
                    world.spawnEntity(projectile);
                    user.getItemCooldownManager().set(this, this.getCooldown(user.getStackInHand(hand)));
                }
            }

        }
        return super.use(world, user, hand);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return this.cooldown.get(itemStack);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -1.4f, EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }

        return builder.build();
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.icicle_staff.effect1.info1"));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.icicle_staff.effect2.info1"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void performLeftClickAbility(ItemStack stack, World world, PlayerEntity user) {
        if (!world.isClient) {
            IcicleProjectile projectile = new IcicleProjectile(world, user.getX(), user.getY() + 1.6, user.getZ(), user, false);
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.5f, 0f);
            world.spawnEntity(projectile);
            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5f, 1f);
        }
    }
}
