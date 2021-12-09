package ssl.wastaken.kamipp.features.modules.player;

import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;

public class LiquidTweaks
        extends Module {
    private static LiquidTweaks INSTANCE = new LiquidTweaks();
    private final Setting<Boolean> vertical = this.register(new Setting<Boolean>("Vertical", false));
    private final Setting<Boolean> weird = this.register(new Setting<Boolean>("Weird", false));
    public Setting<Boolean> solid = this.register(new Setting<Boolean>("Solid", false));

    public LiquidTweaks() {
        super("LiquidTweaks", "Changes Liquids interaction", Module.Category.PLAYER, false, false, false);
        this.setInstance();
    }

    public static LiquidTweaks getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LiquidTweaks();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (this.mc.player.isInLava() && !this.weird.getValue() && this.vertical.getValue() && !this.mc.player.collidedVertically && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.mc.player.motionY -= 0.06553;
        }
    }
}

