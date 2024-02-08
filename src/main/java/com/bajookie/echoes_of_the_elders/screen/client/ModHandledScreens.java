package com.bajookie.echoes_of_the_elders.screen.client;

import com.bajookie.echoes_of_the_elders.screen.ModScreenHandlerTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class ModHandledScreens {
    public static void init() {
        HandledScreens.register(ModScreenHandlerTypes.PANDORAS_BAG, PandorasBagScreen::new);
        HandledScreens.register(ModScreenHandlerTypes.RAID_CONTINUE, RaidContinueScreen::new);
    }
}
