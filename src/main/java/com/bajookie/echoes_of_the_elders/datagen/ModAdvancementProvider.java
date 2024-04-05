package com.bajookie.echoes_of_the_elders.datagen;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<AdvancementEntry> consumer) {
        AdvancementEntry elderPrism = registerAdvancementTaskNoParent(ModItems.ELDER_PRISM, consumer);
        AdvancementEntry dimEnterAdvancement = Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(ModBlocks.ARTIFACT_VAULT),
                        Text.translatable("advance.echoes_of_the_elders.enter_spirit_realm.title"),
                        Text.translatable("advance.echoes_of_the_elders.enter_spirit_realm.description"),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false))
                .parent(elderPrism)
                .criterion(MOD_ID + ":enter_spirit_realm", ChangedDimensionCriterion.Conditions.to(ModDimensions.DEFENSE_DIM_LEVEL_KEY))
                .build(consumer, MOD_ID + "enter_lost_dim_adv");
        AdvancementEntry midas = registerAdvancementTask(ModItems.MIDAS_HAMMER, dimEnterAdvancement, consumer);
        AdvancementEntry lightningOrb = registerAdvancementTask(ModItems.ORB_OF_LIGHTNING, dimEnterAdvancement, consumer);
        AdvancementEntry timeToken = registerAdvancementTask(ModItems.TIME_GLYPH, dimEnterAdvancement, consumer);
        AdvancementEntry lotus = registerAdvancementTask(ModItems.RADIANT_LOTUS, dimEnterAdvancement, consumer);
        AdvancementEntry vitality = registerAdvancementTask(ModItems.VITALITY_PUMP, dimEnterAdvancement, consumer);
        AdvancementEntry gale = registerAdvancementTask(ModItems.GALE_QUIVER, dimEnterAdvancement, consumer);
        AdvancementEntry scorchersMitts = registerAdvancementTask(ModItems.SCORCHERS_MITTS, dimEnterAdvancement, consumer);
        AdvancementEntry doomStick = registerAdvancementTask(ModItems.DOOMSTICK_ITEM, dimEnterAdvancement, consumer);
        AdvancementEntry mirage = registerAdvancementTask(ModItems.POTION_MIRAGE, dimEnterAdvancement, consumer);
        AdvancementEntry witherBulk = registerAdvancementTask(ModItems.WITHERS_BULWARK, dimEnterAdvancement, consumer);
        AdvancementEntry quickening = registerAdvancementTask(ModItems.QUICKENING_BAND, dimEnterAdvancement, consumer);
        AdvancementEntry realityPick = registerAdvancementTask(ModItems.REALITY_PICK, dimEnterAdvancement, consumer);
        AdvancementEntry godSlayer = registerAdvancementTask(ModItems.GODSLAYER, dimEnterAdvancement, consumer);
        AdvancementEntry jumper = registerAdvancementTask(ModItems.GUNHEELS, dimEnterAdvancement, consumer);
        AdvancementEntry cheater = registerAdvancementTask(ModItems.WTF_TOKEN, dimEnterAdvancement, consumer);
    }

    public AdvancementEntry registerAdvancement(Item display, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, AdvancementEntry prev, Consumer<AdvancementEntry> consumer) {
        return Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(display),
                        Text.translatable(display.getTranslationKey()),
                        Text.translatable("advance.echoes_of_the_elders.description.general", Text.translatable(display.getTranslationKey())),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        frame,
                        showToast,
                        announceToChat,
                        hidden))
                .criterion(MOD_ID + ":get_" + display, InventoryChangedCriterion.Conditions.items(display))
                .parent(prev).build(consumer, MOD_ID + display + "_adv");
    }

    public AdvancementEntry registerAdvancementNoParent(Item display, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, Consumer<AdvancementEntry> consumer) {
        return Advancement.Builder.create()
                .display(new AdvancementDisplay(new ItemStack(display),
                        Text.translatable(display.getTranslationKey()),
                        Text.translatable("echoes_of_the_elders:advance.description.general", Text.translatable(display.getTranslationKey())),
                        new Identifier(MOD_ID, "textures/block/ancient_tree_log.png"),
                        frame,
                        showToast,
                        announceToChat,
                        hidden))
                .criterion("get" + display.toString(), InventoryChangedCriterion.Conditions.items(display))
                .build(consumer, MOD_ID + display.toString() + "_adv");
    }

    public AdvancementEntry registerAdvancement(Item display, String title, String description, AdvancementFrame frame, boolean showToast, boolean announceToChat, AdvancementEntry prev, Consumer<AdvancementEntry> consumer) {
        return registerAdvancement(display, frame, showToast, announceToChat, false, prev, consumer);
    }

    public AdvancementEntry registerAdvancementChallenge(Item display, String title, String description, AdvancementEntry prev, Consumer<AdvancementEntry> consumer) {
        return registerAdvancement(display, AdvancementFrame.CHALLENGE, true, true, false, prev, consumer);
    }

    public AdvancementEntry registerAdvancementChallengeNoParent(Item display, String title, String description, Consumer<AdvancementEntry> consumer) {
        return registerAdvancementNoParent(display, AdvancementFrame.CHALLENGE, true, true, false, consumer);
    }

    public AdvancementEntry registerAdvancementChallenge(Item display, String title, AdvancementEntry prev, Consumer<AdvancementEntry> consumer) {
        return registerAdvancement(display, AdvancementFrame.CHALLENGE, true, true, false, prev, consumer);
    }

    public AdvancementEntry registerAdvancementChallengeNoParent(Item display, String title, Consumer<AdvancementEntry> consumer) {
        return registerAdvancementNoParent(display, AdvancementFrame.CHALLENGE, true, true, false, consumer);
    }

    public AdvancementEntry registerAdvancementTask(Item display, AdvancementEntry prev, Consumer<AdvancementEntry> consumer) {
        return registerAdvancement(display, AdvancementFrame.TASK, true, false, false, prev, consumer);
    }

    public AdvancementEntry registerAdvancementTaskNoParent(Item display, String title, String description, Consumer<AdvancementEntry> consumer) {
        return registerAdvancementNoParent(display, AdvancementFrame.TASK, true, false, false, consumer);
    }

    public AdvancementEntry registerAdvancementTaskNoParent(Item display, Consumer<AdvancementEntry> consumer) {
        return registerAdvancementNoParent(display, AdvancementFrame.TASK, true, false, false, consumer);
    }
}
