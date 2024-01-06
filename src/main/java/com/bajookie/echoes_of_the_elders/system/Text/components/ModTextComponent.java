package com.bajookie.echoes_of_the_elders.system.Text.components;

import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TranslationTags;
import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.Texts;

public class ModTextComponent
        implements TextContent {
    protected String content;
    private final TextArgs args;
    private List<StringVisitable> messageParts = ImmutableList.of();
    private static final Pattern ARG_FORMAT = Pattern.compile("\\{([A-Za-z0-9_]+)}");
    private static final Pattern TAG_FORMAT = Pattern.compile("<(\\w+)(?:>(.*?)</\\1>|/>)");

    public ModTextComponent(String content, TextArgs args) {
        this.content = content;
        this.args = args;
    }

    protected void updateMessageParts() {
        try {
            this.messageParts = parseMessage(content);
        } catch (TranslationException translationException) {
            this.messageParts = ImmutableList.of(StringVisitable.plain(content));
        }
    }

    private List<StringVisitable> parseMessage(String message) {
        List<StringVisitable> ret;

        if (message == null) return ImmutableList.of(StringVisitable.plain(""));

        ret = parseTags(message);
        if (ret != null) return ret;

        ret = parseArgs(message);
        if (ret != null) return ret;

        // no special parse found, return plain string
        return ImmutableList.of(StringVisitable.plain(message));

    }

    protected MutableText getMutableWithArgs(String content) {
        return MutableText.of(new ModTextComponent(content, this.args));
    }

    @Nullable
    private List<StringVisitable> breakdownString(String translation, Matcher matcher, Supplier<StringVisitable> parseMatch, boolean isRich) {
        ImmutableList.Builder<StringVisitable> builder = ImmutableList.builder();

        boolean found = false;
        int startIndex = 0;

        // Find all matches
        while (matcher.find(startIndex)) {

            found = true;
            int matchStartIndex = matcher.start();
            int matchEndIndex = matcher.end();

            if (matchStartIndex > startIndex) {
                String trailingStartText = translation.substring(startIndex, matchStartIndex);

                builder.add(isRich ? getMutableWithArgs(trailingStartText) : StringVisitable.plain(trailingStartText));
            }

            builder.add(parseMatch.get());

            startIndex = matchEndIndex;
        }

        if (!found) return null;

        if (startIndex < translation.length()) {
            String trailingEndText = translation.substring(startIndex);

            builder.add(isRich ? getMutableWithArgs(trailingEndText) : StringVisitable.plain(trailingEndText));
        }

        return builder.build();
    }

    @Nullable
    private List<StringVisitable> parseTags(String translation) {
        if (translation == null) return null;

        Matcher matcher = TAG_FORMAT.matcher(translation);

        return breakdownString(translation, matcher, () -> {
            var groupCount = matcher.groupCount();

            // handle self closing tag
            if (groupCount == 1) {
                String tagName = matcher.group(1);

                return TranslationTags.getTagValue(tagName);
            }

            String tagName = matcher.group(1);
            String tagContent = matcher.group(2);

            var richRagContent = getMutableWithArgs(tagContent);

            return TranslationTags.getTagValue(tagName, richRagContent);
        }, true);
    }

    @Nullable
    private List<StringVisitable> parseArgs(String translation) {
        if (translation == null) return null;

        Matcher matcher = ARG_FORMAT.matcher(translation);

        return breakdownString(translation, matcher, () -> {
            String argName = matcher.group(1);

            var arg = args.get(argName);

            if (arg instanceof Text t) return t;

            return Text.literal(arg.toString());
        }, false);
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> visitor, Style style) {
        this.updateMessageParts();
        for (StringVisitable stringVisitable : this.messageParts) {
            Optional<T> optional = stringVisitable.visit(visitor, style);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        this.updateMessageParts();
        for (StringVisitable stringVisitable : this.messageParts) {
            Optional<T> optional = stringVisitable.visit(visitor);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
        var parsedValues = this.args.map((k, v) -> {
            try {
                return v instanceof Text ? Texts.parse(source, (Text) v, sender, depth) : v;
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        return MutableText.of(new ModTextComponent(this.content, parsedValues));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModTextComponent translatableTextContent)) return false;
        if (!Objects.equals(this.content, translatableTextContent.content)) return false;
        if (!(this.args.equals(translatableTextContent.args))) return false;
        return true;
    }

    public int hashCode() {
        int i = Objects.hashCode(this.content);
        i = 31 * i + this.args.hashCode();
        return i;
    }

    public String toString() {
        return "translation{key='" + this.content + "'" + ", args=" + (this.args.toString()) + "}";
    }

    public String getContent() {
        return this.content;
    }

    public TextArgs getArgs() {
        return this.args;
    }

    public static class TranslationException
            extends IllegalArgumentException {
        public TranslationException(ModTranslatableTextComponent text, String message) {
            super(String.format(Locale.ROOT, "Error parsing: %s: %s", text, message));
        }

        public TranslationException(ModTranslatableTextComponent text, int index) {
            super(String.format(Locale.ROOT, "Invalid index %d requested for %s", index, text));
        }

        public TranslationException(ModTranslatableTextComponent text, Throwable cause) {
            super(String.format(Locale.ROOT, "Error while parsing: %s", text), cause);
        }
    }
}


