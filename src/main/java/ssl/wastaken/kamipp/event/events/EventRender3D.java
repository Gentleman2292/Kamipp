package ssl.wastaken.kamipp.event.events;


import net.minecraftforge.fml.common.eventhandler.Event;

public class EventRender3D extends Event
{
    public float partialTicks;

    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
