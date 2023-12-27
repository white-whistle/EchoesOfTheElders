package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class EchoingSword extends SwordItem {
        public EchoingSword() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new FabricItemSettings().maxCount(1));
    }
}
