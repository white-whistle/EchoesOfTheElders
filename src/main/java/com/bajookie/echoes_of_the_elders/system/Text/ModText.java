package com.bajookie.echoes_of_the_elders.system.Text;

import com.bajookie.echoes_of_the_elders.client.CustomItemColors;
import com.bajookie.echoes_of_the_elders.client.animation.AnimationUtil;
import com.bajookie.echoes_of_the_elders.util.Color;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@SuppressWarnings("unused")
public class ModText {
    public static final HashMap<String, Function<MutableText, Text>> registeredTags = new HashMap<>();
    public static final Function<MutableText, Text> FIRE = registerIconTag("fire", colored(0xFFB12F));
    public static final Function<MutableText, Text> LIGHTNING = registerIconTag("lightning", colored(0xF8FF6F));
    public static final Function<MutableText, Text> MAGIC = registerIconTag("magic", colored(0xD874FF));
    public static final Function<MutableText, Text> PHYSICAL = registerIconTag("physical", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> BOOST = registerIconTag("boost", colored(0x74CF5B));
    public static final Function<MutableText, Text> DASH = registerIconTag("dash", colored(0x2DD875));
    public static final Function<MutableText, Text> GOLD = registerIconTag("gold", colored(0xE9B115));
    public static final Function<MutableText, Text> HEART = registerIconTag("heart", colored(0xD32A2A));
    public static final Function<MutableText, Text> HUNGER = registerIconTag("hunger", colored(0xb58657));
    public static final Function<MutableText, Text> EXPLOSION = registerIconTag("explosion", colored(0xf6ad5b));
    public static final Function<MutableText, Text> POTION = registerIconTag("potion", colored(0xC9AEFF));
    public static final Function<MutableText, Text> DIMENSION = registerIconTag("dimension", colored(0xA960C2));
    public static final Function<MutableText, Text> ON_HIT = registerIconTag("on_hit", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> UNIQUE = registerIconTag("unique", ModText::rainbowText);
    public static final Function<MutableText, Text> NEGATIVE_EFFECT = registerIconTag("negative_effect", colored(0xC73835), "effect", 0xC73835);
    public static final Function<MutableText, Text> POSITIVE_EFFECT = registerIconTag("positive_effect", colored(0x55C743), "effect", 0x55C743);
    public static final Function<MutableText, Text> SHIELD = registerIconTag("shield", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> ARROW = registerIconTag("arrow", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> PICK = registerIconTag("pick", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> ITEM = registerIconTag("item", colored(Formatting.WHITE));

    public static final Function<MutableText, Text> INFO = registerIconTag("info");
    public static final Function<MutableText, Text> RECHARGE = registerIconTag("recharge", colored(0x6777ff));
    public static final Function<MutableText, Text> COOLDOWN = registerIconTag("cooldown", colored(0x4A56C7));
    public static final Function<MutableText, Text> COOLDOWN_STATIC = registerIconTag("cooldown_static", colored(0x4A56C7));
    public static final Function<MutableText, Text> COOLDOWN_REDUCTION = registerIconTag("cooldown_reduction", colored(Formatting.BLUE));
    public static final Function<MutableText, Text> STACK_LEVEL = registerIconTag("power_star", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> TIER = registerIconTag("tier", ModText::rainbowText, "tier", () -> CustomItemColors.rainbow().getRGB());
    public static final Function<MutableText, Text> SOULBOUND = registerIconTag("soulbound", colored(0xc789d8));
    public static final Function<MutableText, Text> PRISM = registerIconTag("prism", ModText::rainbowText);

    public static final Function<MutableText, Text> ACTIVE_ABILITY = registerIconTag("active");
    public static final Function<MutableText, Text> PASSIVE_ABILITY = registerIconTag("passive");
    public static final Function<MutableText, Text> SPECIAL_ABILITY = registerIconTag("star");

    public static final Function<MutableText, Text> RIGHT_CLICK = registerIconTag("right_click");
    public static final Function<MutableText, Text> LEFT_CLICK = registerIconTag("left_click");
    public static final Function<MutableText, Text> KEY = registerIconTag("key", colored(Formatting.WHITE));
    public static final Function<MutableText, Text> CURSOR = registerIconTag("cursor");
    public static final Function<MutableText, Text> SWITCH_ON = registerIconTag("switch_on", colored(Formatting.GREEN));
    public static final Function<MutableText, Text> SWITCH_OFF = registerIconTag("switch_off", colored(Formatting.GRAY));

    public static final Function<MutableText, Text> PAD = registerIconTag("pad");
    public static final Function<MutableText, Text> SPACE_SMALL = registerIconTag("space_small");
    public static final Function<MutableText, Text> RAINBOW = registerTag("rainbow", ModText::rainbowText);

    static {
        for (Formatting value : Formatting.values()) {
            registeredTags.put(value.name(), t -> t.styled(s -> s.withColor(value)));
        }
    }

    public static MutableText join(Text t1, Text t2) {
        return TextUtil.concat((MutableText) t1, (MutableText) t2);
    }

    public static Function<MutableText, Text> registerIconTag(String name, Function<MutableText, Text> fn, String iconName, int iconColor) {
        var fontId = new Identifier(MOD_ID, iconName);
        var icon = Text.literal("@").styled(s -> s.withFont(fontId).withColor(iconColor));

        Function<MutableText, Text> wrappedWithIcon = (mt) -> join(icon, fn.apply(mt));

        registeredTags.put(name, wrappedWithIcon);

        return wrappedWithIcon;
    }

    public static Function<MutableText, Text> registerIconTag(String name, Function<MutableText, Text> fn, String iconName, Supplier<Integer> iconColor) {
        var fontId = new Identifier(MOD_ID, iconName);
        Supplier<Text> getIcon = () -> Text.literal("@").styled(s -> s.withFont(fontId).withColor(iconColor.get()));

        Function<MutableText, Text> wrappedWithIcon = (mt) -> join(getIcon.get(), fn.apply(mt));

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

    public static Function<MutableText, Text> colored(int color) {
        return (MutableText text) -> text.styled(s -> s.withColor(color));
    }

    public static Function<MutableText, Text> colored(Formatting color) {
        return (MutableText text) -> text.styled(s -> s.withColor(color));
    }

    public static void init(HashMap<String, Function<MutableText, Text>> tags) {
        tags.putAll(registeredTags);
    }
}
