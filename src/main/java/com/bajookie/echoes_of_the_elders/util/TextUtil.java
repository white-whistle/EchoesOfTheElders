package com.bajookie.echoes_of_the_elders.util;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

public class TextUtil {
    private static float toFixed1(float v) {
        return Float.parseFloat(String.format("%.1f", v));
    }

    public static Object f1(float v, int color) {
        var text = Math.floor(v) == v ? Text.translatable("number.echoes_of_the_elders.int", (int) v) : Text.translatable("number.echoes_of_the_elders.f1", toFixed1(v));
        return text.styled(style -> style.withColor(TextColor.fromRgb(color)).withUnderline(true));
    }

    public static Object f1(float v) {
        return TextUtil.f1(v, 0x70dee6);
    }

    public static Text passive(Text o) {
        return Text.translatable("tooltip.echoes_of_the_elders.passive", o).styled(style -> style.withFont(new Identifier(EOTE.MOD_ID, "glyph_font")));
    }
}
