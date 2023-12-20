package com.bajookie.echoes_of_the_elders.util;

@SuppressWarnings("unused")
public class Color {
    private final int color;

    public Color(int rgb) {
        // Ensure that the provided rgb value is within the valid range
        if (rgb < 0x000000 || rgb > 0xFFFFFF) {
            throw new IllegalArgumentException("Invalid RGB value. It should be in the range 0x000000 - 0xFFFFFF.");
        }
        this.color = rgb;
    }

    public Color(int red, int green, int blue) {
        // Ensure that the provided components are within the valid range
        if (isComponentInvalid(red) || isComponentInvalid(green) || isComponentInvalid(blue)) {
            throw new IllegalArgumentException("Invalid color component. Each component should be in the range 0-255.");
        }
        this.color = (red << 16) | (green << 8) | blue;
    }

    private boolean isComponentInvalid(int component) {
        return component < 0 || component > 255;
    }

    public int getRed() {
        return (color >> 16) & 0xFF;
    }

    public int getGreen() {
        return (color >> 8) & 0xFF;
    }

    public int getBlue() {
        return color & 0xFF;
    }

    public int getRGB() {
        return color;
    }

    public float getRedF() {
        return this.getRed() / 255f;
    }

    public float getGreenF() {
        return this.getGreen() / 255f;
    }

    public float getBlueF() {
        return this.getBlue() / 255f;
    }

    public static Color fromHSL(float hue, float saturation, float lightness) {
        float c = (1 - Math.abs(2 * lightness - 1)) * saturation;
        float x = c * (1 - Math.abs((hue / 60) % 2 - 1));
        float m = lightness - c / 2;

        float r, g, b;
        if (0 <= hue && hue < 60) {
            r = c;
            g = x;
            b = 0;
        } else if (60 <= hue && hue < 120) {
            r = x;
            g = c;
            b = 0;
        } else if (120 <= hue && hue < 180) {
            r = 0;
            g = c;
            b = x;
        } else if (180 <= hue && hue < 240) {
            r = 0;
            g = x;
            b = c;
        } else if (240 <= hue && hue < 300) {
            r = x;
            g = 0;
            b = c;
        } else {
            r = c;
            g = 0;
            b = x;
        }

        return new Color(((int) ((r + m) * 255) << 16) | ((int) ((g + m) * 255) << 8) | (int) ((b + m) * 255));
    }
}
