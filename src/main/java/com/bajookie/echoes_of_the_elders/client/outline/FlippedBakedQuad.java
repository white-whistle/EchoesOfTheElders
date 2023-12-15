package com.bajookie.echoes_of_the_elders.client.outline;

import com.bajookie.echoes_of_the_elders.util.ArrayUtil;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlippedBakedQuad extends BakedQuad {
    public FlippedBakedQuad(int[] vertexData, int colorIndex, Direction face, Sprite sprite, boolean shade) {
        super(ArrayUtil.reverseVertex(vertexData, 8), colorIndex, face, sprite, shade);
//        super(vertexData, colorIndex, face, sprite, shade);

//        System.out.println(Arrays.toString(vertexData));
    }
}
