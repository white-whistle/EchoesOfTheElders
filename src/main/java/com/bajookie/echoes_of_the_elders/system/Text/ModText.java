package com.bajookie.echoes_of_the_elders.system.Text;

import com.bajookie.echoes_of_the_elders.client.animation.AnimationUtil;
import com.bajookie.echoes_of_the_elders.util.Color;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@SuppressWarnings("unused")
public class ModText {
    private static final HashMap<String, Function<MutableText, Text>> registeredTags = new HashMap<>();
    public static final Function<MutableText, Text> FIRE = registerIconTag("fire", (t) -> t.styled(s -> s.withColor(0xFFB12F)));
    public static final Function<MutableText, Text> LIGHTNING = registerIconTag("lightning", (t) -> t.styled(s -> s.withColor(0xF8FF6F)));
    public static final Function<MutableText, Text> MAGIC = registerIconTag("magic", (t) -> t.styled(s -> s.withColor(0xD874FF)));
    public static final Function<MutableText, Text> BOOST = registerIconTag("boost", (t) -> t.styled(s -> s.withColor(0x74CF5B)));
    public static final Function<MutableText, Text> DASH = registerIconTag("dash", (t) -> t.styled(s -> s.withColor(0x2DD875)));
    public static final Function<MutableText, Text> GOLD = registerIconTag("gold", (t) -> t.styled(s -> s.withColor(0xE9B115)));
    public static final Function<MutableText, Text> HEART = registerIconTag("heart", (t) -> t.styled(s -> s.withColor(0xD32A2A)));
    public static final Function<MutableText, Text> POTION = registerIconTag("potion", (t) -> t.styled(s -> s.withColor(0xC9AEFF)));
    public static final Function<MutableText, Text> DIMENSION = registerIconTag("dimension", (t) -> t.styled(s -> s.withColor(0xA960C2)));
    public static final Function<MutableText, Text> ON_HIT = registerIconTag("on_hit");
    public static final Function<MutableText, Text> UNIQUE = registerIconTag("unique", ModText::rainbowText);
    public static final Function<MutableText, Text> NEGATIVE_EFFECT = registerIconTag("negative_effect", (t) -> t.styled(s -> s.withColor(0xC73835)), "effect", 0xC73835);
    public static final Function<MutableText, Text> POSITIVE_EFFECT = registerIconTag("positive_effect", (t) -> t.styled(s -> s.withColor(0x55C743)), "effect", 0x55C743);

    public static final Function<MutableText, Text> COOLDOWN = registerIconTag("cooldown", (t) -> t.styled(s -> s.withColor(0x4A56C7)));
    public static final Function<MutableText, Text> COOLDOWN_STATIC = registerIconTag("cooldown_static", (t) -> t.styled(s -> s.withColor(0x4A56C7)));
    public static final Function<MutableText, Text> COOLDOWN_REDUCTION = registerIconTag("cooldown_reduction", (t) -> t.styled(s -> s.withColor(Formatting.BLUE)));
    public static final Function<MutableText, Text> STACK_LEVEL = registerIconTag("power_star");

    public static final Function<MutableText, Text> ACTIVE_ABILITY = registerIconTag("active");
    public static final Function<MutableText, Text> PASSIVE_ABILITY = registerIconTag("passive");
    public static final Function<MutableText, Text> SPECIAL_ABILITY = registerIconTag("star");

    public static final Function<MutableText, Text> RIGHT_CLICK = registerIconTag("right_click");
    public static final Function<MutableText, Text> LEFT_CLICK = registerIconTag("left_click");

    public static final Function<MutableText, Text> PAD = registerIconTag("pad");
    public static final Function<MutableText, Text> RAINBOW = registerTag("rainbow", ModText::rainbowText);

    static {
        for (Formatting value : Formatting.values()) {
            registeredTags.put(value.name(), t -> t.styled(s -> s.withColor(value)));
        }
    }

    public static MutableText join(Text t1, Text t2) {
        return Text.translatable("tooltip.echoes_of_the_elders.join", t1, t2);
    }

    public static Function<MutableText, Text> registerIconTag(String name, Function<MutableText, Text> fn, String iconName, int iconColor) {
        var fontId = new Identifier(MOD_ID, iconName);
        var icon = Text.literal("@").styled(s -> s.withFont(fontId).withColor(iconColor));

        Function<MutableText, Text> wrappedWithIcon = (mt) -> join(icon, fn.apply(mt));

        registeredTags.put(name, wrappedWithIcon);

        return wrappedWithIcon;
    }

    public static Function<MutableText, Text> registerIconTag(String name, Function<MutableText, Text> fn) {
        return registerIconTag(name, fn, name, 0xffffff);
    }

    public static Function<MutableText, Text> registerIconTag(String name) {
        return registerIconTag(name, t -> t);
    }

    public static Function<MutableText, Text> registerTag(String name, Function<MutableText, Text> fn) {
        registeredTags.put(name, fn);

        return fn;
    }

    public static Text rainbowText(MutableText text) {
        var progress = AnimationUtil.HUE_SHIFT_ANIMATION.getProgress(0);

        var c = Color.fromHSL(360 * progress, 0.5f, 0.7f).getRGB();

        return text.styled(s -> s.withColor(c));
    }

    public static void init(HashMap<String, Function<MutableText, Text>> tags) {
        tags.putAll(registeredTags);
    }
}
