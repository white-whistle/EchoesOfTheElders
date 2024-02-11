package com.bajookie.echoes_of_the_elders.screen.client.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class SimpleTooltipWidget extends ClickableWidget {

    public SimpleTooltipWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty());
    }

    public abstract Identifier getTexture();

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
