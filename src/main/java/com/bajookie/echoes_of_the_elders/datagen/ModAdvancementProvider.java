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
                .parent(dimEnterAdvancement)
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
                .parent(dimEnterAdvancement)
                .build(consumer,MOD_ID+"get_refresher_adv");

    }
}
