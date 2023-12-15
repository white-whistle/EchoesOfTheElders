package com.bajookie.echoes_of_the_elders.client.outline;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemOutlineRenderer {

    public static void renderItemOutline(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
//        boolean bl = !stack.isEmpty();

        for (BakedQuad bakedQuad : quads) {
            int i = -1;
//            if (bl && bakedQuad.hasColor()) {
//                i = this.colors.getColor(stack, bakedQuad.getColorIndex());
//            }
            float f = (float) (i >> 16 & 0xFF) / 255.0f;
            float g = (float) (i >> 8 & 0xFF) / 255.0f;
            float h = (float) (i & 0xFF) / 255.0f;

            var dir = bakedQuad.getFace();
            var dVec = dir.getVector();
//            var oVec = dVec.multiply(-1);
            var offset = 1 / 16f;

            matrices.push();

            matrices.translate(dVec.getX() * offset, dVec.getY() * offset, dVec.getZ() * offset);
            matrices.push();
            matrices.scale(1 + ((1 - dVec.getX()) * offset), 1 + ((1 - dVec.getY()) * offset), 1 + ((1 - dVec.getZ()) * offset));

            MatrixStack.Entry entry = matrices.peek();

//            bakedQuad.getFace().

//            vertices.quad(entry, bakedQuad, f, g, h, light, overlay);
            var flipped = new FlippedBakedQuad(bakedQuad.getVertexData(), bakedQuad.getColorIndex(), bakedQuad.getFace().getOpposite(), bakedQuad.getSprite(), bakedQuad.hasShade());

            vertices.quad(entry, flipped, f, g, h, 0, overlay);

            matrices.pop();
            matrices.pop();
        }
    }

}
