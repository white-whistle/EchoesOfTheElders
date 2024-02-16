package com.bajookie.echoes_of_the_elders.screen;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlerTypes {
    public static final ScreenHandlerType<PandorasBagScreenHandler> PANDORAS_BAG = register("pandoras_bag", PandorasBagScreenHandler::new);
    public static final ScreenHandlerType<RaidContinueScreenHandler> RAID_CONTINUE = register("raid_continue", RaidContinueScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new ModIdentifier(id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void init() {
    }

}
