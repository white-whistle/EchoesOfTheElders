package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.EldermanEntity;
import com.bajookie.echoes_of_the_elders.entity.custom.FlowerDefenseEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.entity.LivingEntity;

public class EldermanModel<T extends EldermanEntity>  extends EndermanEntityModel<T> {
    public EldermanModel(ModelPart modelPart) {
        super(modelPart);
    }
}
