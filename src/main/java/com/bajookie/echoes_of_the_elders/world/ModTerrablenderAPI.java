package com.bajookie.echoes_of_the_elders.world;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.world.biome.ModMaterialRules;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModTerrablenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,MOD_ID, ModMaterialRules.makeRules());
    }
}
