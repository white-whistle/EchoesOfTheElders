package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.IcicleProjectile;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ILeftClickAbility;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
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

@DropCondition.RaidLevelBetween(max = 50)
public class IcicleStaff extends Item implements IArtifact, IHasCooldown, IStackPredicate, ILeftClickAbility, IRaidReward {
    private static final List<Tag> TAGS = List.of(Tag.ICE);

    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 40, 20 * 5);
    public static final float SNOWBALL_DAMAGE = 10;
    public static final float ICICLE_DAMAGE = 30;

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
        return COOLDOWN.get(itemStack);
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

    public static final Ability MAGIC_SNOWBALL_ABILITY = new Ability("magic_snowball", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("damage", SNOWBALL_DAMAGE));
        }
    };

    public static final Ability HEAVY_ICE_ABILITY = new Ability("heavy_ice", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("damage", ICICLE_DAMAGE));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(MAGIC_SNOWBALL_ABILITY, HEAVY_ICE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
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
