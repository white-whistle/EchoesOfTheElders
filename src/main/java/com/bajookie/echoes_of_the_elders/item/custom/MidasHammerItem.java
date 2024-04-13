package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

@DropCondition.RaidLevelBetween(max = 50)
public class MidasHammerItem extends PickaxeItem implements IArtifact, IHasCooldown, IRaidReward {

    public static final StackedItemStat.Float EFFECT_DAMAGE = new StackedItemStat.Float(20f, 100f);
    public static final StackedItemStat.Float DROP_CHANCE = new StackedItemStat.Float(0.1f, 1f);

    public MidasHammerItem() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 6, -2f, new ArtifactItemSettings());
    }


    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.getItemCooldownManager().set(this, this.getCooldown(stack));
            user.getWorld().playSound(user, entity.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.AMBIENT, 1, 1);
            if (!user.getWorld().isClient()) {
                ServerWorld world = (ServerWorld) user.getWorld();
                world.spawnParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 1, 0, 1);
                int hp = Math.round(entity.getHealth());
                entity.damage(world.getDamageSources().create(DamageTypes.MAGIC, user), 50f);
                if (entity.isDead()) {
                    int nugget = hp % 9;
                    int ingot = (hp - nugget) / 9;
                    ItemEntity item = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GOLD_INGOT, ingot));
                    ItemEntity itemN = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GOLD_NUGGET, nugget));
                    world.spawnEntity(item);
                    world.spawnEntity(itemN);
                }
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random r = new Random();
        if (r.nextFloat() < DROP_CHANCE.get(stack)) {
            ItemEntity item = new ItemEntity(target.getWorld(), target.getX(), target.getY(), target.getZ(), new ItemStack(Items.GOLD_NUGGET, 1));
            target.getWorld().spawnEntity(item);
        }
        return super.postHit(stack, target, attacker);
    }

    public static final Ability GREEDY_STRIKE = new Ability("greedy_strike", Ability.AbilityType.ON_HIT, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putI("percent", (int) (DROP_CHANCE.get(stack) * 100)));
        }
    };

    public static final Ability EXECUTIONERS_FEE = new Ability("executioners_fee", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("damage", EFFECT_DAMAGE.get(stack)));
            section.line("info2");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static List<Ability> ABILITIES = List.of(EXECUTIONERS_FEE, GREEDY_STRIKE);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 20 * 60 * 5;
    }
}
