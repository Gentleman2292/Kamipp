package ssl.wastaken.kamipp.mixin.mixins;

import java.net.UnknownHostException;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.modules.player.AntiDDoS;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ServerPinger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ServerPinger.class})
public class MixinServerPinger {
    @Inject(method={"ping"}, at={@At(value="HEAD")}, cancellable=true)
    public void pingHook(ServerData server, CallbackInfo info) throws UnknownHostException {
        if (AntiDDoS.getInstance().shouldntPing(server.serverIP)) {
            Kami.LOGGER.info("AntiDDoS preventing Ping to: " + server.serverIP);
            info.cancel();
        }
    }

    @Inject(method={"tryCompatibilityPing"}, at={@At(value="HEAD")}, cancellable=true)
    public void tryCompatibilityPingHook(ServerData server, CallbackInfo info) {
        if (AntiDDoS.getInstance().shouldntPing(server.serverIP)) {
            Kami.LOGGER.info("AntiDDoS preventing Compatibility Ping to: " + server.serverIP);
            info.cancel();
        }
    }
}

