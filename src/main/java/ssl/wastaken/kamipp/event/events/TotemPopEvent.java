package ssl.wastaken.kamipp.event.events;

import ssl.wastaken.kamipp.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public class TotemPopEvent
        extends EventStage {
    private final EntityPlayer entity;

    public TotemPopEvent(EntityPlayer entity) {
        this.entity = entity;
    }

    public EntityPlayer getEntity() {
        return this.entity;
    }
}

