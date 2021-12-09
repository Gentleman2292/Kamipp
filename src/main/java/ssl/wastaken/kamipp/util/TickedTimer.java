package ssl.wastaken.kamipp.util;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ssl.wastaken.kamipp.Kami;

public class TickedTimer implements Listenable {
    private int ticks = 0;
    public void reset() {ticks = 0;};
    public boolean passed(int ticks) {return this.ticks >= ticks;}
    public void start(){ticks = 0; Kami.EVENT_BUS.subscribe(this);}
    public void stop() {Kami.EVENT_BUS.unsubscribe(this);}

    @EventHandler
    private Listener<TickEvent.ClientTickEvent> tickEventListener = new Listener<>(event -> {
        ticks++;
    });

    public TickedTimer(){ start();}

    @Override protected void finalize() { Kami.EVENT_BUS.unsubscribe(this);}
}