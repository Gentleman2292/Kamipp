package ssl.wastaken.kamipp.manager;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.util.FontRenderer;
import ssl.wastaken.kamipp.mixin.mixins.accessors.IMinecraft;

import java.io.InputStream;
import java.awt.Font;
import java.awt.Color;

public class FontManager implements IMinecraft{

    public FontRenderer FONT_RENDERER;

    public void load() {
        this.FONT_RENDERER = new FontRenderer(getFont("Lato-Medium.ttf", Float.intBitsToFloat(Float.floatToIntBits(0.042888146f) ^ 0x7F0FAB7B)));
    }

    public float drawString(final String text, final float x, final float y, final Color color) {
        if (Kami.moduleManager.isModuleEnabled("FontMod")) {
            return (float)this.FONT_RENDERER.drawStringWithShadow(text, x, y, color.getRGB());
        }
        return (float)FontManager.mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
    }

    public float getStringWidth(final String text) {
        if (Kami.moduleManager.isModuleEnabled("FontMod")) {
            return (float)this.FONT_RENDERER.getStringWidth(text);
        }
        return (float)FontManager.mc.fontRenderer.getStringWidth(text);
    }

    public float getHeight() {
        if (Kami.moduleManager.isModuleEnabled("FontMod")) {
            return (float)this.FONT_RENDERER.getHeight();
        }
        return (float)FontManager.mc.fontRenderer.FONT_HEIGHT;
    }

    public static Font getFont(final String fontName, final float size) {
        try {
            final InputStream inputStream = FontManager.class.getResourceAsStream("/assets/europa/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            inputStream.close();
            return awtClientFont;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, (int)size);
        }
    }
}

