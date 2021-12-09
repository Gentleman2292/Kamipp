package ssl.wastaken.kamipp.features.modules.render;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;

import java.awt.*;

public class EnchantColor extends Module {
    public Setting<Integer> red = register(new Setting("Red", 255, 0, 255));
    public Setting<Integer> green = register(new Setting("Green", 255, 0, 255));
    public Setting<Integer> blue = register(new Setting("Blue", 255, 0, 255));
    public Setting<Boolean> rainbow = register(new Setting("Rainbow", false));

    public EnchantColor() {
        super("EnchantColor", "Changes the enchant glint color", Module.Category.RENDER, true, false, true);
    }

    public static Color getColor(long offset, float fade) {
        if (!Kami.moduleManager.getModuleT(EnchantColor.class).rainbow.getValue()) {
            return new Color(Kami.moduleManager.getModuleT(EnchantColor.class).red.getValue(), Kami.moduleManager.getModuleT(EnchantColor.class).green.getValue(), Kami.moduleManager.getModuleT(EnchantColor.class).blue.getValue());
        }
        float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }

    @Override
    public void onUpdate() {
        if (rainbow.getValue()) {
            cycleRainbow();
        }
    }

    public void cycleRainbow() {
        float[] tick_color = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };
        int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);

        red.setValue((color_rgb_o >> 16) & 0xFF);
        green.setValue((color_rgb_o >> 8) & 0xFF);
        blue.setValue(color_rgb_o & 0xFF);
    }
}
