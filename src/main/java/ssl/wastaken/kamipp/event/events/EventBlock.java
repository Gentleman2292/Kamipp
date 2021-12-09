package ssl.wastaken.kamipp.event.events;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import ssl.wastaken.kamipp.event.EventStage;

public class EventBlock extends EventStage
{
    public BlockPos pos;
    public EnumFacing facing;

    public EventBlock(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}
