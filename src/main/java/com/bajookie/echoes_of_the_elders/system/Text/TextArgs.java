package com.bajookie.echoes_of_the_elders.system.Text;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class TextArgs {
    private final HashMap<String, Object> args;

    public TextArgs() {
        args = new HashMap<>();
    }

    public TextArgs put(String k, Object v) {
        this.args.put(k, v);
        return this;
    }

    public TextArgs putF(String k, float v) {
        this.args.put(k, TextUtil.f1(v));
        return this;
    }

    public TextArgs putF(String k, float v, int color) {
        this.args.put(k, TextUtil.f1(v).styled(s -> s.withColor(color)));
        return this;
    }

    public TextArgs putF(String k, float v, Formatting color) {
        this.args.put(k, TextUtil.f1(v).styled(s -> s.withColor(color)));
        return this;
    }

    public TextArgs putI(String k, int v) {
        this.args.put(k, Text.translatable("number.echoes_of_the_elders.int", v));
        return this;
    }

    public TextArgs putI(String k, int v, Formatting color) {
        this.args.put(k, Text.translatable("number.echoes_of_the_elders.int", v).styled(s -> s.withColor(color)));
        return this;
    }

    public TextArgs putI(String k, int v, int color) {
        this.args.put(k, Text.translatable("number.echoes_of_the_elders.int", v).styled(s -> s.withColor(color)));
        return this;
    }

    public Object get(String k) {
        return this.args.get(k);
    }

    public boolean hasKey(String key) {
        return this.args.containsKey(key);
    }

    public int hashCode() {
        return this.args.hashCode();
    }

    public TextArgs map(BiFunction<String, Object, Object> mapper) {
        var ret = new TextArgs();

        this.args.forEach((k, v) -> ret.put(k, mapper.apply(k, v)));

        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TextArgs textArgs) {
            return this.args.equals(textArgs.args);
        }

        return false;
    }
}
