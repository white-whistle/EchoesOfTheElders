package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TravelCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateAdvancement(Consumer<AdvancementEntry> consumer) {
        AdvancementEntry getElderPrismAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.ELDER_PRISM),
                        Text.literal("The Great Prism"),
                        Text.literal("use the Elder Prism to enter the Lost dimension"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_elder_prism", InventoryChangedCriterion.Conditions.items(ModItems.ELDER_PRISM))
                .build(consumer,MOD_ID+"get_elder_prism_adv");
        AdvancementEntry dimEnterAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModBlocks.ARTIFACT_VAULT),
                        Text.literal("Got Lost?"),
                        Text.literal("get to the lost dimension"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false))
                .parent(getElderPrismAdvancement)
                .criterion("dim_enter", ChangedDimensionCriterion.Conditions.to(ModDimensions.DEFENSE_DIM_LEVEL_KEY))
                .build(consumer,MOD_ID+"enter_lost_dim_adv");
        AdvancementEntry getMidasAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.MIDAS_HAMMER),
                        Text.literal("Golden Power"),
                        Text.literal("Find the Midas Hammer Relic"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_midas", InventoryChangedCriterion.Conditions.items(ModItems.MIDAS_HAMMER))
                .build(consumer,MOD_ID+"get_midas_adv");
        AdvancementEntry getLightningOrbAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.CHAIN_LIGHTNING_ITEM),
                        Text.literal("Electric Power"),
                        Text.literal("Find the Lightning Orb Relic"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_lightning", InventoryChangedCriterion.Conditions.items(ModItems.CHAIN_LIGHTNING_ITEM))
                .parent(dimEnterAdvancement)
                .build(consumer,MOD_ID+"get_lightning_adv");
        AdvancementEntry getRefresherAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.TIME_TOKEN),
                        Text.literal("Again!"),
                        Text.literal("Find the Time token Relic!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_refresher", InventoryChangedCriterion.Conditions.items(ModItems.TIME_TOKEN))
                .build(consumer,MOD_ID+"get_refresher_adv");
        AdvancementEntry getLotusAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.RADIANT_LOTUS),
                        Text.literal("Clarity"),
                        Text.literal("Find the Radiant Lotus!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_lotus", InventoryChangedCriterion.Conditions.items(ModItems.RADIANT_LOTUS))
                .build(consumer,MOD_ID+"get_lotus_adv");
        AdvancementEntry getVitalityAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.VITALITY_PUMP),
                        Text.literal("Pump It"),
                        Text.literal("Find the Vitality Heart!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_vitality", InventoryChangedCriterion.Conditions.items(ModItems.VITALITY_PUMP))
                .build(consumer,MOD_ID+"get_vitality_adv");
        AdvancementEntry getGaleAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.GALE_CORE),
                        Text.literal("Up and Away"),
                        Text.literal("Find the Gale Core!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_gale", InventoryChangedCriterion.Conditions.items(ModItems.GALE_CORE))
                .build(consumer,MOD_ID+"get_gale_adv");
        AdvancementEntry getMittsAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.SCORCHERS_MITTS),
                        Text.literal("Burning Snap"),
                        Text.literal("Find the Scorchers Mitts!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_scorch", InventoryChangedCriterion.Conditions.items(ModItems.SCORCHERS_MITTS))
                .build(consumer,MOD_ID+"get_scorch_adv");
        AdvancementEntry getDoomAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.DOOMSTICK_ITEM),
                        Text.literal("Pump It"),
                        Text.literal("Find the Doom Stick!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_doom", InventoryChangedCriterion.Conditions.items(ModItems.DOOMSTICK_ITEM))
                .build(consumer,MOD_ID+"get_doom_adv");
        AdvancementEntry getMirageAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.POTION_MIRAGE),
                        Text.literal("Dupe It"),
                        Text.literal("Find the Potion Mirage!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_mirage", InventoryChangedCriterion.Conditions.items(ModItems.POTION_MIRAGE))
                .build(consumer,MOD_ID+"get_mirage_adv");
        AdvancementEntry getWitherScaleAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.WITHER_SCALES_ITEM),
                        Text.literal("Blocked!"),
                        Text.literal("Find the Wither Bulk!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_bulk", InventoryChangedCriterion.Conditions.items(ModItems.WITHER_SCALES_ITEM))
                .build(consumer,MOD_ID+"get_bulk_adv");
        AdvancementEntry getQuickAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.QUICKENING_BAND),
                        Text.literal("Fast Thinking"),
                        Text.literal("Find the Quickening Band!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_quick", InventoryChangedCriterion.Conditions.items(ModItems.QUICKENING_BAND))
                .build(consumer,MOD_ID+"get_quick_adv");
        AdvancementEntry getRealityAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.VITALITY_PUMP),
                        Text.literal("But How?"),
                        Text.literal("Find the Reality Pick!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_pick", InventoryChangedCriterion.Conditions.items(ModItems.REALITY_PICK))
                .parent(dimEnterAdvancement)
                .build(consumer,MOD_ID+"get_pick_adv");
        AdvancementEntry getGodAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.VITALITY_PUMP),
                        Text.literal("Slayed"),
                        Text.literal("Find the God Slayer!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_god", InventoryChangedCriterion.Conditions.items(ModItems.GODSLAYER))
                .parent(dimEnterAdvancement)
                .build(consumer,MOD_ID+"get_god_adv");
        AdvancementEntry getGunnedAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.GUNHEELS),
                        Text.literal("Jumper"),
                        Text.literal("Find the Gunned Heels!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_gunned", InventoryChangedCriterion.Conditions.items(ModItems.GUNHEELS))
                .parent(dimEnterAdvancement)
                .build(consumer,MOD_ID+"get_gunned_adv");
        AdvancementEntry getCheatAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModItems.WTF_RELIC),
                        Text.literal("Cheater"),
                        Text.literal("cheat the WTF!"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false))
                .criterion("get_cheat", InventoryChangedCriterion.Conditions.items(ModItems.WTF_RELIC))
                .parent(dimEnterAdvancement)
                .build(consumer,MOD_ID+"get_cheat_adv");

    }
}
