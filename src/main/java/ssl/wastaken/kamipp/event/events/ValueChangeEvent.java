package ssl.wastaken.kamipp.event.events;

import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.event.EventStage;

public class ValueChangeEvent
        extends EventStage {
    public Setting setting;
    public Object value;

    public ValueChangeEvent(Setting setting, Object value) {
        this.setting = setting;
        this.value = value;
    }
}

