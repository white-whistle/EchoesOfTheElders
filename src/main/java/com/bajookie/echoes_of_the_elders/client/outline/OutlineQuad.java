package com.bajookie.echoes_of_the_elders.client.outline;

import net.minecraft.client.render.model.BakedQuad;

public class OutlineQuad extends BakedQuad {
    public OutlineQuad(BakedQuad delegate) {
        super(delegate.getVertexData(), delegate.getColorIndex(), delegate.getFace(), delegate.getSprite(), delegate.hasShade());
    }
}
