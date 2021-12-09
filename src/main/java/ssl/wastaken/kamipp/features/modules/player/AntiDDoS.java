package ssl.wastaken.kamipp.features.modules.player;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.event.events.ClientEvent;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.command.Command;
import ssl.wastaken.kamipp.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AntiDDoS
        extends Module {
    private static AntiDDoS instance;
    public final Setting<Boolean> full = this.register(new Setting<Boolean>("Full", false));
    private final Map<String, Setting> servers = new ConcurrentHashMap<String, Setting>();
    public Setting<String> newIP = this.register(new Setting<Object>("NewServer", "Add Server...", v -> this.full.getValue() == false));
    public Setting<Boolean> showServer = this.register(new Setting<Object>("ShowServers", Boolean.valueOf(false), v -> this.full.getValue() == false));

    public AntiDDoS() {
        super("AntiDDoS", "Prevents DDoS attacks", Module.Category.PLAYER, false, false, true);
        instance = this;
    }

    public static AntiDDoS getInstance() {
        if (instance == null) {
            instance = new AntiDDoS();
        }
        return instance;
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (Kami.configManager.loadingConfig || Kami.configManager.savingConfig) {
            return;
        }
        if (event.getStage() == 2 && event.getSetting() != null && event.getSetting().getFeature() != null && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.newIP) && !this.shouldntPing(this.newIP.getPlannedValue()) && !event.getSetting().getPlannedValue().equals(event.getSetting().getDefaultValue())) {
                Setting setting = this.register(new Setting<Boolean>(this.newIP.getPlannedValue(), Boolean.valueOf(true), v -> this.showServer.getValue() != false && this.full.getValue() == false));
                this.registerServer(setting);
                Command.sendMessage("<AntiDDoS> Added new Server: " + this.newIP.getPlannedValue());
                event.setCanceled(true);
            } else {
                Setting setting = event.getSetting();
                if (setting.equals(this.enabled) || setting.equals(this.drawn) || setting.equals(this.bind) || setting.equals(this.newIP) || setting.equals(this.showServer) || setting.equals(this.full)) {
                    return;
                }
                if (setting.getValue() instanceof Boolean && !((Boolean) setting.getPlannedValue()).booleanValue()) {
                    this.servers.remove(setting.getName().toLowerCase());
                    this.unregister(setting);
                    event.setCanceled(true);
                }
            }
        }
    }

    public void registerServer(Setting setting) {
        this.servers.put(setting.getName().toLowerCase(), setting);
    }

    public boolean shouldntPing(String ip) {
        return !this.isOff() && (this.full.getValue() != false || this.servers.get(ip.toLowerCase()) != null);
    }
}
