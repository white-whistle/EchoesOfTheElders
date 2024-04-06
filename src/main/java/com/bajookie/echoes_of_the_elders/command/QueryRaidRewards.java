package com.bajookie.echoes_of_the_elders.command;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class QueryRaidRewards {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(
                CommandManager.literal("eote_qrr")
                        .then(
                                CommandManager.argument("level", IntegerArgumentType.integer(0))
                                        .executes(QueryRaidRewards::run)
                        )

        );
    }

    private static int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {

        var src = ctx.getSource();
        var player = src.getPlayer();
        int level = IntegerArgumentType.getInteger(ctx, "level");

        if (player == null) return -1;

        var world = player.getWorld();

        var dropCtx = new IRaidReward.RaidRewardDropContext(world, null, player, level);

        var allArtifacts = ModItems.registeredModItems.stream().filter(item -> item instanceof IArtifact).toList();
        var allRewards = ModItems.registeredModItems.stream().filter(item -> item instanceof IRaidReward).toList();
        var droppable = ModItems.registeredModItems.stream().filter(item -> DropCondition.canDrop(item, dropCtx)).toList();

        if (droppable.size() == 0) {
            src.sendFeedback(() -> Text.literal("No droppable items found"), false);
            return 1;
        }

        StringBuilder feedback = new StringBuilder("Querying raid drops at level " + level);

        for (var drop : droppable) {
            feedback.append("\n - ").append(drop);
        }

        feedback.append("\n Found §9")
                .append(droppable.size())
                .append("§r out of §9")
                .append(allRewards.size())
                .append("§r raid rewards (artifacts: §9")
                .append(allArtifacts.size())
                .append("§r)");

        src.sendFeedback(() -> Text.literal(feedback.toString()), false);
        return 1;
    }

}
