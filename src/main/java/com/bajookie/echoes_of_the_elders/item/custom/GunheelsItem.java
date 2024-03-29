package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GunheelsItem extends ArmorItem implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {

    public GunheelsItem() {
        super(GUNHEEL_ARMOR_MATERIAL, Type.BOOTS, new ArtifactItemSettings());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.gunheels.effect"));

        super.appendTooltip(stack, world, tooltip, context);

    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity player) {
            var cdm = player.getItemCooldownManager();
            var item = stack.getItem();

            if (player.isOnGround() && cdm.isCoolingDown(item)) {
                cdm.remove(item);
            }
        }
    }

    public boolean doBulletjump(PlayerEntity player, ItemStack stack) {
        if (!player.isSprinting()) return false;
        if (player.isUsingRiptide()) return false;

        var cdm = player.getItemCooldownManager();
        var item = stack.getItem();

        if (cdm.isCoolingDown(item)) return false;

        cdm.set(item, this.getCooldown(stack));

        player.setSneaking(false);

        var level = 2;
        var world = player.getWorld();

        float f = player.getYaw();
        float g = player.getPitch();
        float h = -MathHelper.sin(f * ((float) Math.PI / 180)) * MathHelper.cos(g * ((float) Math.PI / 180));
        float k = -MathHelper.sin(g * ((float) Math.PI / 180));
        float l = MathHelper.cos(f * ((float) Math.PI / 180)) * MathHelper.cos(g * ((float) Math.PI / 180));
        float m = MathHelper.sqrt(h * h + k * k + l * l);
        float n = 3.0f * ((1.0f + (float) level) / 4.0f);
        player.addVelocity(h * n / m, k * n / m, l * n / m);
        player.useRiptide(20);
        if (player.isOnGround()) {
            player.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
        }
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
        world.playSoundFromEntity(null, player, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);

        return true;
    }

    private static final ArmorMaterial GUNHEEL_ARMOR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurability(Type type) {
            return 0;
        }

        @Override
        public int getProtection(Type type) {
            return 1;
        }

        @Override
        public int getEnchantability() {
            return 1;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }

        @Override
        public String getName() {
            return new Identifier(EOTE.MOD_ID, "gunheel").toString();
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    @Override
    public int getCooldown(ItemStack itemStack) {
        return 30;
    }
}
