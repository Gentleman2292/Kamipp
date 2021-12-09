package ssl.wastaken.kamipp.features.modules.render;

import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;

public class PhobosChams
        extends Module {
    private static PhobosChams INSTANCE = new PhobosChams();
    public Setting<Boolean> colorSync = this.register(new Setting<Boolean>("Sync", false));
    public Setting<Boolean> colored = this.register(new Setting<Boolean>("Colored", false));
    public Setting<Boolean> textured = this.register(new Setting<Boolean>("Textured", false));
    public Setting<Boolean> rainbow = this.register(new Setting<Object>("Rainbow", Boolean.valueOf(false), v -> this.colored.getValue()));
    public Setting<Integer> saturation = this.register(new Setting<Object>("Saturation", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(100), v -> this.colored.getValue() != false && this.rainbow.getValue() != false));
    public Setting<Integer> brightness = this.register(new Setting<Object>("Brightness", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(100), v -> this.colored.getValue() != false && this.rainbow.getValue() != false));
    public Setting<Integer> speed = this.register(new Setting<Object>("Speed", Integer.valueOf(40), Integer.valueOf(1), Integer.valueOf(100), v -> this.colored.getValue() != false && this.rainbow.getValue() != false));
    public Setting<Boolean> xqz = this.register(new Setting<Object>("XQZ", Boolean.valueOf(false), v -> this.colored.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> red = this.register(new Setting<Object>("Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> green = this.register(new Setting<Object>("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> blue = this.register(new Setting<Object>("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> alpha = this.register(new Setting<Object>("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue()));
    public Setting<Integer> hiddenRed = this.register(new Setting<Object>("Hidden Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.xqz.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> hiddenGreen = this.register(new Setting<Object>("Hidden Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.xqz.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> hiddenBlue = this.register(new Setting<Object>("Hidden Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.xqz.getValue() != false && this.rainbow.getValue() == false));
    public Setting<Integer> hiddenAlpha = this.register(new Setting<Object>("Hidden Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.colored.getValue() != false && this.xqz.getValue() != false && this.rainbow.getValue() == false));

    public PhobosChams() {
        super("PhobosChams", "Renders players through walls.", Module.Category.RENDER, false, false, false);
        this.setInstance();
    }

    public static PhobosChams getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PhobosChams();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

