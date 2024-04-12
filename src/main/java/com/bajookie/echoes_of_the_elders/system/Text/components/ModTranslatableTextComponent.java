package com.bajookie.echoes_of_the_elders.system.Text.components;

import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Language;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;

public class ModTranslatableTextComponent extends ModTextComponent {
    private final String key;
    @Nullable
    private final String fallback;
    private final TextArgs args;
    @Nullable
    private Language languageCache;

    public ModTranslatableTextComponent(String key, @Nullable String fallback, TextArgs args) {
        super("", args);
        this.key = key;
        this.fallback = fallback;
        this.args = args == null ? new TextArgs() : args;
    }

    @Override
    protected void updateMessageParts() {
        Language language = Language.getInstance();
        if (language == this.languageCache) {
            return;
        }

        this.languageCache = language;
        this.content = this.fallback != null ? language.get(this.key, this.fallback) : language.get(this.key);

        super.updateMessageParts();
    }


    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) {
        var parsedValues = this.args.map((k, v) -> {
            try {
                return v instanceof Text ? Texts.parse(source, (Text) v, sender, depth) : v;
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        return MutableText.of(new ModTranslatableTextComponent(this.key, this.fallback, parsedValues));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object o) {
        if (super.equals(o)) {
            if (!(o instanceof ModTranslatableTextComponent translatableTextContent)) return false;
            if (!Objects.equals(this.key, translatableTextContent.key)) return false;
            if (!Objects.equals(this.fallback, translatableTextContent.fallback)) return false;
        }

        return true;
    }

    public int hashCode() {
        int i = Objects.hashCode(this.key);
        i = 31 * i + Objects.hashCode(this.fallback);
        i = 31 * i + this.args.hashCode();
        return i;
    }

    public String toString() {
        return "translation{key='" + this.key + "'" + (String) (this.fallback != null ? ", fallback='" + this.fallback + "'" : "") + ", args=" + (this.args.toString()) + "}";
    }

    public String getKey() {
        return this.key;
    }

    @Nullable
    public String getFallback() {
        return this.fallback;
    }

    public TextArgs getArgs() {
        return this.args;
    }
}


