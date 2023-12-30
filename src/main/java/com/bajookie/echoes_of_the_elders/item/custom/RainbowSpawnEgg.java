package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.client.CustomItemColors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;

public class RainbowSpawnEgg extends SpawnEggItem {
    public RainbowSpawnEgg(EntityType<? extends MobEntity> type, int secondaryColor, Settings settings) {
        super(type, 0x000000, secondaryColor, settings);
    }

    @Override
    public int getColor(int tintIndex) {
        if (tintIndex == 0) return CustomItemColors.rainbow().getRGB();
        
        return super.getColor(tintIndex);
    }
}
