package com.bajookie.echoes_of_the_elders.client.tooltip;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;

@Environment(EnvType.CLIENT)
public class ItemTooltipComponent implements TooltipComponent {

    private final ItemTooltipData data;

    public ItemTooltipComponent(ItemTooltipData data) {
        this.data = data;
    }

    @Override
    public int getHeight() {
        return this.getRowsHeight() + 4;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return this.getColumnsWidth();
    }

    private int getColumnsWidth() {
        return this.getColumns() * 18 + 2;
    }

    private int getRowsHeight() {
        return this.getRows() * 20 + 2;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        var stack = data.stack();
        context.drawItem(stack, x + 1, y + 1, 0);
        context.drawItemInSlot(textRenderer, stack, x + 1, y + 1);
    }

    private int getColumns() {
        return Math.max(2, (int) Math.ceil(Math.sqrt((double) 1 + 1.0)));
    }

    private int getRows() {
        return (int) Math.ceil(((double) 1 + 1.0) / (double) this.getColumns());
    }
}
