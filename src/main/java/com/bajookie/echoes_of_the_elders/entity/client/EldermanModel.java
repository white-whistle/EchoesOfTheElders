package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.EldermanEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EndermanEntityModel;

public class EldermanModel<T extends EldermanEntity> extends EndermanEntityModel<T> {
    public EldermanModel(ModelPart modelPart) {
        super(modelPart);
    }
}
