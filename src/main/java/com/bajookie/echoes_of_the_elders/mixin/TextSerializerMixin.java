package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.system.Text.components.ModTextComponent;
import com.bajookie.echoes_of_the_elders.system.Text.components.ModTranslatableTextComponent;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import com.google.gson.*;
import net.minecraft.text.*;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(Text.Serializer.class)
public abstract class TextSerializerMixin {

    @Shadow
    protected abstract void addStyle(Style style, JsonObject json, JsonSerializationContext context);

    @Shadow
    public abstract JsonElement serialize(Text text, Type type, JsonSerializationContext jsonSerializationContext);

    @Shadow
    public abstract MutableText deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;

    @Inject(method = "serialize(Lnet/minecraft/text/Text;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", at = @At("HEAD"), cancellable = true)
    private void serialize(Text text, Type type, JsonSerializationContext jsonSerializationContext, CallbackInfoReturnable<JsonElement> cir) {
        var textContent = text.getContent();
        if (!(textContent instanceof ModTextComponent modTextComponent)) return;

        JsonObject jsonObject = new JsonObject();

        if (!text.getStyle().isEmpty()) {
            addStyle(text.getStyle(), jsonObject, jsonSerializationContext);
        }

        if (!text.getSiblings().isEmpty()) {
            JsonArray jsonArray = new JsonArray();
            for (Text text2 : text.getSiblings()) {
                jsonArray.add(serialize(text2, (Type) ((Object) Text.class), jsonSerializationContext));
            }
            jsonObject.add("extra", jsonArray);
        }

        if (!modTextComponent.getArgs().isEmpty()) {
            jsonObject.add("with", modTextComponent.getArgs().toJson());
        }

        if (modTextComponent instanceof ModTranslatableTextComponent modTranslatableTextComponent) {
            jsonObject.addProperty(ModIdentifier.string("translate"), modTranslatableTextComponent.getKey());
            String string = modTranslatableTextComponent.getFallback();
            if (string != null) {
                jsonObject.addProperty("fallback", string);
            }

            cir.setReturnValue(jsonObject);
            return;
        }

        jsonObject.addProperty(ModIdentifier.string("text"), modTextComponent.getContent());
        cir.setReturnValue(jsonObject);
    }

    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;", at = @At("HEAD"), cancellable = true)
    private void deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<MutableText> cir) {
        if (jsonElement.isJsonObject()) {
            String string;
            MutableText mutableText = null;
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject.has(ModIdentifier.string("text"))) {
                string = JsonHelper.getString(jsonObject, ModIdentifier.string("text"));

                var args = new TextArgs();
                if (jsonObject.has("with")) {
                    var jsonArgs = jsonObject.getAsJsonObject("with");
                    args.fromJson(jsonArgs);
                }

                mutableText = string.isEmpty() ? Text.empty() : TextUtil.plain(string, args);
                cir.setReturnValue(mutableText);
            } else if (jsonObject.has(ModIdentifier.string("translate"))) {
                string = JsonHelper.getString(jsonObject, ModIdentifier.string("translate"));
                // String string2 = JsonHelper.getString(jsonObject, "fallback", null);

                var args = new TextArgs();
                if (jsonObject.has("with")) {
                    var jsonArgs = jsonObject.getAsJsonObject("with");
                    args.fromJson(jsonArgs);
                }

                mutableText = TextUtil.translatable(string, args);
                cir.setReturnValue(mutableText);
            }

            if (mutableText == null) return;

            if (jsonObject.has("extra")) {
                JsonArray jsonArray2 = JsonHelper.getArray(jsonObject, "extra");
                if (jsonArray2.size() <= 0) throw new JsonParseException("Unexpected empty array of components");
                for (int j = 0; j < jsonArray2.size(); ++j) {
                    mutableText.append(deserialize(jsonArray2.get(j), type, jsonDeserializationContext));
                }
            }
            mutableText.setStyle((Style) jsonDeserializationContext.deserialize(jsonElement, (Type) ((Object) Style.class)));

            cir.setReturnValue(mutableText);
        }
    }

}
