package com.bajookie.echoes_of_the_elders.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(QueryRaidRewards::register);
    }
}
