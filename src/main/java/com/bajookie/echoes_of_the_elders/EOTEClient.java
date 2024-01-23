package com.bajookie.echoes_of_the_elders;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.block.custom.SpiritalGrass;
import com.bajookie.echoes_of_the_elders.block.custom.entity.ModBlockEntities;
import com.bajookie.echoes_of_the_elders.client.CustomItemColors;
import com.bajookie.echoes_of_the_elders.client.StopwatchOverlay;
import com.bajookie.echoes_of_the_elders.client.tooltip.ItemTooltipComponent;
import com.bajookie.echoes_of_the_elders.client.tooltip.ItemTooltipData;
import com.bajookie.echoes_of_the_elders.entity.client.ModModelLayers;
import com.bajookie.echoes_of_the_elders.particles.ElectricShockParticle;
import com.bajookie.echoes_of_the_elders.particles.LightningParticle;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.particles.SecondSunParticle;
import com.bajookie.echoes_of_the_elders.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

public class EOTEClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelLayers.registerModMobLayers();
        ModBlocks.registerModBlocksModelLayers();

        ModModelPredicateProvider.registerModModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.SECOND_SUN_PARTICLE, SecondSunParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ELECTRIC_SHOCK, ElectricShockParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LIGHTNING_PARTICLE, LightningParticle.Factory::new);
        HudRenderCallback.EVENT.register(new StopwatchOverlay());

        TooltipComponentCallback.EVENT.register((data) -> {
            if (data instanceof ItemTooltipData itemTooltipData) {
                return new ItemTooltipComponent(itemTooltipData);
            }

            return null;
        });

        // DimensionRenderingRegistryImpl.registerSkyRenderer(ModDimensions.DEFENSE_DIM_LEVEL_KEY,new ModSkyRenderer());
        CustomItemColors.init();
        ClientNetworking.init();
    }
}
