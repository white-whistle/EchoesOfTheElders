package com.bajookie.echoes_of_the_elders.screen.client.widget;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public abstract class RaidContinueScrollKnob extends SliderWidget {
    private static final Identifier STAR_KNOB = new ModIdentifier("widget/star_knob");
    public boolean disabled = false;

    public RaidContinueScrollKnob(int x, int y, int width, float value) {
        super(x, y, width, 32, ScreenTexts.EMPTY, value);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawGuiTexture(STAR_KNOB, this.getX() + (int) (this.value * (double) (this.width - 32)), this.getY(), 32, 32);
        context.drawItem(ModItems.OLD_KEY.getDefaultStack(), this.getX() + (int) (this.value * (double) (this.width - 32)) + 8, this.getY() + 8);
    }


    @Override
    protected void updateMessage() {
    }

    @Override
    protected void applyValue() {

    }

    @Override
    protected boolean isValidClickButton(int button) {
        if (this.disabled) return false;

        return super.isValidClickButton(button);
    }

    public abstract void onCommitted();

    @Override
    public void onRelease(double mouseX, double mouseY) {
        if (this.value < 0.25) {
            this.value = 0;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1.0f));
        } else if (this.value > 0.75) {
            this.value = 1;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1.0f));
        } else {
            this.value = 0.51;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }

        onCommitted();
    }
}
