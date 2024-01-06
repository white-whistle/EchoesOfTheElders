package com.bajookie.echoes_of_the_elders.system.Text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Function;

/**
 * This is the registry/mapping of TagName->TextMutation
 */
public class TranslationTags {
    private static final HashMap<String, Function<MutableText, Text>> tags = new HashMap<>();

    public static Text getTagValue(String tagName, @Nullable MutableText tagContents) {
        if (!tags.containsKey(tagName)) return tagContents;

        return tags.get(tagName).apply(tagContents);
    }

    static {
        ModText.init(tags);
    }

    public static Text getTagValue(String tagName) {
        return getTagValue(tagName, null);
    }
}
