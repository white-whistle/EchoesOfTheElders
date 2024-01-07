package com.bajookie.echoes_of_the_elders;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.client.CustomItemColors;
import com.bajookie.echoes_of_the_elders.client.StopwatchOverlay;
import com.bajookie.echoes_of_the_elders.entity.client.ModModelLayers;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.particles.SecondSunParticle;
import com.bajookie.echoes_of_the_elders.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EOTEClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelLayers.registerModMobLayers();
        ModBlocks.registerModBlocksModelLayers();

        ModModelPredicateProvider.registerModModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.SECOND_SUN_PARTICLE, SecondSunParticle.Factory::new);
        HudRenderCallback.EVENT.register(new StopwatchOverlay());

        CustomItemColors.init();

    }
}
