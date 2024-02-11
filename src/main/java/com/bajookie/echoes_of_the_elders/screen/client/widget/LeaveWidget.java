package com.bajookie.echoes_of_the_elders.screen.client.widget;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LeaveWidget extends SimpleTooltipWidget {
    private static final Identifier TEXTURE = new ModIdentifier("widget/leave");

    public LeaveWidget(int x, int y, int width, int height) {
        super(x, y, width, height);

        setTooltip(Tooltip.of(Text.translatable("screen.echoes_of_the_elders.raid_continue.leave.description")));
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }

}
