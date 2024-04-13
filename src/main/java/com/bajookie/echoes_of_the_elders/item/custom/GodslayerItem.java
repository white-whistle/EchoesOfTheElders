package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.client.render.EffectLayer;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasFlatOverlay;
import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedAttributeModifiers;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 50)
public class GodslayerItem extends SwordItem implements IArtifact, IHasUpscaledModel, FabricItem, IStackPredicate, IHasFlatOverlay, IRaidReward {
    private final StackedItemStat.Float stackedAttackDamage = new StackedItemStat.Float(10f, 64f);

    private final StackedAttributeModifiers stackedAttributeModifiers = new StackedAttributeModifiers(index -> {
        var progress = index / (float) (GodslayerItem.this.getArtifactMaxStack() - 1);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) stackedAttackDamage.get(progress), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2, EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    });

    public GodslayerItem() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new ArtifactItemSettings());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return stackedAttributeModifiers.get(StackLevel.get(stack) - 1);
        }
        return super.getAttributeModifiers(stack, slot);
    }

    public static final Ability COSMIC_EDGE_ABILITY = new Ability("cosmic_edge", Ability.AbilityType.ON_HIT, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(COSMIC_EDGE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }


    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }

    @Override
    public boolean showFlatOverlay(ItemStack itemStack) {
        return true;
    }

    @Override
    public RenderLayer getFlatRenderLayer() {
        return EffectLayer.getPortalMasked(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
    }
}
