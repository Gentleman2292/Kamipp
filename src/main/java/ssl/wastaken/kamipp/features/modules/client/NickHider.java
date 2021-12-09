package ssl.wastaken.kamipp.features.modules.client;

import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;

public class NickHider
        extends Module {
    private static NickHider instance;
    public final Setting<Boolean> changeOwn = this.register(new Setting<Boolean>("MyName", true));
    public final Setting<String> ownName = this.register(new Setting<Object>("Name", "Name here...", v -> this.changeOwn.getValue()));
    private static StringBuffer name = null;

    public NickHider() {
        super("NickHider", "Hides your nick", Module.Category.CLIENT, false, false, false);
        instance = this;
    }

    public static NickHider getInstance() {
        if (instance == null) {
            instance = new NickHider();
        }
        return instance;
    }

    public static String getPlayerName() {
        if (NickHider.fullNullCheck()) {
            return mc.getSession().getUsername();
        }
        String name = PlayerName();
        if (name == null || name.isEmpty()) {
            return mc.getSession().getUsername();
        }
        return name;
    }
    public static String PlayerName() {
        if (name == null) {
            return null;
        }
        return name.toString();
    }
}


