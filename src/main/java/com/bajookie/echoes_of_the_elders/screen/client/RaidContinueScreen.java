package com.bajookie.echoes_of_the_elders.screen.client;

import com.bajookie.echoes_of_the_elders.screen.RaidContinueScreenHandler;
import com.bajookie.echoes_of_the_elders.screen.client.widget.RaidContinueScrollKnob;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class RaidContinueScreen extends HandledScreen<RaidContinueScreenHandler> {
    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/container/raid_continue.png");
    private RaidContinueScrollKnob knob;
    private float currentValue = 0.51f;
    private boolean wasDragged = false;

    public RaidContinueScreen(RaidContinueScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 153;
        this.backgroundHeight = 146;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        var v = currentValue;
        var d = this.knob.disabled;
        this.init(client, width, height);
        currentValue = v;
        this.knob.disabled = d;
    }

    @Override
    protected void init() {
        super.init();

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.knob = this.addDrawableChild(new RaidContinueScrollKnob(i + 13, j + 101, 127, currentValue) {
            @Override
            public void onCommitted() {
                if (this.value == 0) {
                    this.disabled = true;
                } else if (this.value == 1) {
                    this.disabled = true;
                }
            }
        });

        this.knob.setFocused(true);
        this.focusOn(this.knob);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        var titleMessage = Text.translatable("screen.echoes_of_the_elders.raid_continue.title");
        var titleWidth = this.textRenderer.getWidth(titleMessage);
        context.drawText(this.textRenderer, titleMessage, 80 - titleWidth / 2, 18, 0xffffff, false);

        var contMessage = Text.translatable("screen.echoes_of_the_elders.raid_continue.continue");
        var contWidth = this.textRenderer.getWidth(contMessage);
        context.drawText(this.textRenderer, contMessage, 80 - contWidth / 2, 81, 0xffffff, false);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if ((wasDragged || this.knob.isMouseOver(mouseX, mouseY)) && this.knob.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            wasDragged = true;
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (wasDragged && this.knob.mouseReleased(mouseX, mouseY, button)) {
            wasDragged = false;
            return true;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }
}
