package ssl.wastaken.kamipp.features.modules.client;

import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.features.setting.Setting;

public class Font extends Module {
    public static Setting<String> name;
    public static Setting<Styles> style;
    public static Setting<Integer> size;
    public static Setting<Boolean> antiAlias;
    public static Setting<Boolean> metrics;
    public static Setting<Shadows> shadow;
    private static Font INSTANCE = new Font();

    public Font() {
        super("Font","Allows you to customize the client's font.", Category.CLIENT, false, false, true);
        this.name = new Setting<String>("FontName", "Arial", "Name of the font.");
        this.style = new Setting<Styles>("Style", Styles.Plain);
        this.size = new Setting<Integer>("Size", 18, 10, 50);
        this.antiAlias = new Setting<Boolean>("AntiAlias",  true);
        this.metrics = new Setting<Boolean>("Metrics", true);
        this.shadow = new Setting<Shadows>("Shadow",Shadows.Normal);
        this.setInstance();
    }
    private void setInstance() {
        INSTANCE = this;
    }

    public static Font getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Font();
        }
        return INSTANCE;
    }

    public enum Styles {
        Plain,
        Italic,
        Bold,
        Both;
    }
    public enum Shadows {
        None,
        Small,
        Normal;
    }

}
