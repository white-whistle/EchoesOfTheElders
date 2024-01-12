package com.bajookie.echoes_of_the_elders.client.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.text.*;

import java.util.Collections;
import java.util.List;

public class TooltipComponentSlot implements Text, OrderedText {
    private final TooltipData tooltipData;

    public TooltipComponentSlot(TooltipData data) {
        this.tooltipData = data;
    }

    public TooltipData getTooltipData() {
        return tooltipData;
    }

    @Override
    public Style getStyle() {
        return null;
    }

    @Override
    public TextContent getContent() {
        return new LiteralTextContent("{TooltipComponentSlot}");
    }

    @Override
    public List<Text> getSiblings() {
        return Collections.emptyList();
    }

    @Override
    public OrderedText asOrderedText() {
        return this;
    }

    @Override
    public boolean accept(CharacterVisitor visitor) {
        return false;
    }
}
