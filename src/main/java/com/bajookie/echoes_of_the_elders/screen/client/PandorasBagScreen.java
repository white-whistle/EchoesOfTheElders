package com.bajookie.echoes_of_the_elders.screen.client;

import com.bajookie.echoes_of_the_elders.screen.PandorasBagScreenHandler;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class PandorasBagScreen extends HandledScreen<PandorasBagScreenHandler> {
    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/container/pandoras_bag.png");

    public PandorasBagScreen(PandorasBagScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        ++this.backgroundHeight;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0xf284ec, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0xf284ec, false);
    }
}
