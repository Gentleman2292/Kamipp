package ssl.wastaken.kamipp.features.modules.misc;

import com.google.common.eventbus.Subscribe;
import ssl.wastaken.kamipp.event.events.PacketEvent;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.mixin.mixins.accessors.ISPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate
        extends Module {

    public NoRotate() {
        super("NoRotate", "stop rotating.", Module.Category.MISC, true, false, false);
    }

    @Subscribe
    public void onPacket(PacketEvent.Receive event) {
        if (NoRotate.fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            ISPacketPlayerPosLook packet = (ISPacketPlayerPosLook)event.getPacket();
            packet.setPitch(this.mc.player.rotationPitch);
            packet.setYaw(this.mc.player.rotationYaw);
        }
    }
}

