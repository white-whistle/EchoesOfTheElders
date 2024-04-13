package com.bajookie.echoes_of_the_elders.datagen;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface WebView {
    @Retention(RetentionPolicy.RUNTIME)
    @interface OverrideTexture {
        String value();
    }
}
