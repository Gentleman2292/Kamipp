package ssl.wastaken.kamipp.event.events;

import javafx.stage.Stage;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventPacket extends Event
{
    public Packet packet;

    public EventPacket(final Stage stage, final Packet packet) {
        this.packet = packet;
    }

    public void setPacket(final Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public static class Send extends EventPacket
    {
        public Send(final Stage stage, final Packet packet) {
            super(stage, packet);
        }
    }

    public static class Receive extends EventPacket
    {
        public Receive(final Stage stage, final Packet packet) {
            super(stage, packet);
        }

        public void setCancelled(boolean b) {
        }
    }
}