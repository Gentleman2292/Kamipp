package ssl.wastaken.kamipp.features.modules.misc;

import ssl.wastaken.kamipp.event.events.PacketEvent;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BuildHeight
        extends Module {
    private final Setting<Integer> height = this.register(new Setting<Integer>("Height", 255, 0, 255));

    public BuildHeight() {
        super("BuildHeight", "Allows you to place at build height", Module.Category.MISC, true, false, false);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        CPacketPlayerTryUseItemOnBlock packet;
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && (packet = event.getPacket()).getPos().getY() >= this.height.getValue() && packet.getDirection() == EnumFacing.UP) {
            packet.placedBlockDirection = EnumFacing.DOWN;
        }
    }
}

