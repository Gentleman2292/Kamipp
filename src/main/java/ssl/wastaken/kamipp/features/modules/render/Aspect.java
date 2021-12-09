package ssl.wastaken.kamipp.features.modules.render;

import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.event.events.PerspectiveEvent;
import ssl.wastaken.kamipp.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public
class Aspect
        extends Module {
    public Setting<Float> aspect = this.register(new Setting<>("Alpha", 1.0f, 0.1f, 5.0f));

    public
    Aspect() {
        super ("Aspect", "Cool.", Module.Category.RENDER, true, false, false);
    }

    @SubscribeEvent
    public
    void onPerspectiveEvent(PerspectiveEvent perspectiveEvent) {
        perspectiveEvent.setAspect(this.aspect.getValue());
    }
}




