package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.custom.GaleArrowProjectile;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.IProjectileProvider;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.ability.TooltipHelper;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.screen.client.particles.ScreenParticleManager;
import com.bajookie.echoes_of_the_elders.screen.client.particles.imps.RegenParticle;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.system.Text.TooltipSection;
import com.bajookie.echoes_of_the_elders.util.Interator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GaleQuiverItem extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward, IProjectileProvider {
    public static StackedItemStat.Float BONUS_ARROW_DAMAGE = new StackedItemStat.Float(1f, 10f);
    public static StackedItemStat.Int REGEN_RATE = new StackedItemStat.Int(20 * 6, 20);

    private final String regenTicksId = new Identifier(EOTE.MOD_ID, "regen").toString();

    protected static final int MAX_USAGE = 16;

    public GaleQuiverItem() {
        super(new ArtifactItemSettings().maxCount(1).maxDamage(MAX_USAGE));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity user) {
            var nbt = stack.getOrCreateNbt();
            // initialize nbt
            if (!nbt.contains(regenTicksId)) {
                nbt.putInt(regenTicksId, 0);
            }

            int regen = nbt.getInt(regenTicksId);
            if (stack.getDamage() > 0) {
                if (regen > REGEN_RATE.get(stack)) {
                    nbt.putInt(regenTicksId, 0);
                    stack.setDamage(stack.getDamage() - 1);

                    if (world.isClient) {
                        Interator.of(5).forEach(i -> {
                            ScreenParticleManager.addParticle(
                                    new RegenParticle(ScreenParticleManager.getStackPosition(stack))
                                            .randomizeOffset(3.5f)
                            );
                        });
                    }
                } else {
                    nbt.putInt(regenTicksId, regen + 1);
                }
            }

        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(this)) return super.use(world, user, hand);

        ItemStack stack = user.getStackInHand(hand);

        return TypedActionResult.success(stack);
    }

    public static Ability BOTTOMLESS_QUIVER_ABILITY = new Ability("bottomless_quiver", Ability.AbilityType.PASSIVE) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section
                    .line("info1", new TextArgs().put("duration", TextUtil.formatTime(REGEN_RATE.get(stack))));
        }
    };

    public static TooltipSection.Info GALE_ARROW_INFO = new TooltipSection.Info("gale_arrow") {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section
                    .line("info1", new TextArgs().putF("damage", BONUS_ARROW_DAMAGE.get(stack)))
                    .line("info2");
        }
    };

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        new TooltipHelper(stack, world, tooltip, context).sections(
                BOTTOMLESS_QUIVER_ABILITY,
                GALE_ARROW_INFO
        );

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 20 * 30;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x03a5fc;
    }

    @Override
    public ItemStack getProjectile(ItemStack self, ItemStack rangedWeapon) {
        return StackLevel.set(ModItems.GALE_ARROW.getDefaultStack(), StackLevel.get(self));
    }

    @Override
    public void onConsume(ItemStack self, ItemStack rangedWeapon) {
        self.setDamage(self.getDamage() + 1);
    }

    @Override
    public boolean canProvideProjectile(ItemStack self, ItemStack rangedWeapon) {
        return self.getDamage() < self.getMaxDamage();
    }

    public static class Arrow extends ArrowItem implements IArtifact {
        public Arrow() {
            super(new FabricItemSettings());
        }

        @Override
        public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
            var arrowEntity = new GaleArrowProjectile(world, shooter);
            arrowEntity.initFromStack(stack);

            return arrowEntity;
        }
    }


}
