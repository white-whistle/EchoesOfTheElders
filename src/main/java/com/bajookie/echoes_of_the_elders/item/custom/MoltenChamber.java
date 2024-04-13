package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.datagen.ModModelProvider;
import com.bajookie.echoes_of_the_elders.entity.custom.MoltenChamberShotProjectile;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 10, max = 60)
public class MoltenChamber extends Item implements IArtifact, IStackPredicate, IHasCooldown, IRaidReward {
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(10 * 6, 20 * 3);
    public static final StackedItemStat.Int SHOT_DAMAGE = new StackedItemStat.Int(10, 30);
    public static final StackedItemStat.Float SHOT_AMP = new StackedItemStat.Float(1f, 6f);
    public static final int CHARGE_DURATION = 30;

    public MoltenChamber() {
        super(new ArtifactItemSettings());
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.setCurrentHand(hand);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    private void shootProjectile(PlayerEntity player, float charge, ItemStack stack, World world) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.GUN_SHOT_01, SoundCategory.PLAYERS, 0.5f, 1f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        var amp = SHOT_AMP.get(charge);
        MoltenChamberShotProjectile bullet = new MoltenChamberShotProjectile(world, player.getEyePos().x, player.getEyePos().y, player.getEyePos().z, (int) (amp * SHOT_DAMAGE.get(stack)), player, player.getPitch(), player.getYaw());
        bullet.setVelocity(player, player.getPitch(), player.getYaw(), player.getRoll(), 5f, 0);
        world.spawnEntity(bullet);
        player.getItemCooldownManager().set(this, this.getCooldown(stack));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                if (!world.isClient) {
                    var charge = (getMaxUseTime(stack) - remainingUseTicks) / (float) CHARGE_DURATION;
                    shootProjectile(player, charge, stack, world);
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
        return COOLDOWN.get(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.molten_chamber.effect.info1", new TextArgs().putF("damage_min", this.SHOT_DAMAGE.get(stack)).putF("damage_max", SHOT_AMP.max * this.SHOT_DAMAGE.get(stack))));

        super.appendTooltip(stack, world, tooltip, context);
    }

    public static final Ability MOLTEN_SHOT = new Ability("molten_shot", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("damage_min", SHOT_DAMAGE.get(stack)).putF("damage_max", SHOT_AMP.max * SHOT_DAMAGE.get(stack)));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(MOLTEN_SHOT);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public Model getBaseModel() {
        return ModModelProvider.GUN;
    }
}
