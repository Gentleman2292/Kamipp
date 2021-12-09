package ssl.wastaken.kamipp.features.modules.misc;

import ssl.wastaken.kamipp.event.events.ChorusEvent;
import ssl.wastaken.kamipp.event.events.Render3DEvent;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.util.ColorUtil;
import ssl.wastaken.kamipp.util.RenderUtil;
import ssl.wastaken.kamipp.util.Timer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ChorusPredict
        extends Module {
    private final Setting<Integer> time = this.register(new Setting<Integer>("Duration", 500, 50, 3000));
    private final Setting<Boolean> box = this.register(new Setting<Boolean>("Box", true));
    private final Setting<Boolean> outline = this.register(new Setting<Boolean>("Outline", true));
    private final Setting<Integer> boxR = this.register(new Setting<Object>("BoxR", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.box.getCurrentState()));
    private final Setting<Integer> boxG = this.register(new Setting<Object>("BoxG", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.box.getCurrentState()));
    private final Setting<Integer> boxB = this.register(new Setting<Object>("BoxB", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.box.getCurrentState()));
    private final Setting<Integer> boxA = this.register(new Setting<Object>("BoxA", Integer.valueOf(120), Integer.valueOf(0), Integer.valueOf(255), v -> this.box.getCurrentState()));
    private final Setting<Float> lineWidth = this.register(new Setting<Object>("LineWidth", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f), v -> this.outline.getCurrentState()));
    private final Setting<Integer> outlineR = this.register(new Setting<Object>("OutlineR", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.outline.getCurrentState()));
    private final Setting<Integer> outlineG = this.register(new Setting<Object>("OutlineG", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.outline.getCurrentState()));
    private final Setting<Integer> outlineB = this.register(new Setting<Object>("OutlineB", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.outline.getCurrentState()));
    private final Setting<Integer> outlineA = this.register(new Setting<Object>("OutlineA", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.outline.getCurrentState()));
    private final Timer timer = new Timer();
    private double x;
    private double y;
    private double z;

    public ChorusPredict() {
        super("ChorusPredict", "", Module.Category.MISC, true, false, false);
    }

    @SubscribeEvent
    public void onChorus(ChorusEvent event) {
        this.x = event.getChorusX();
        this.y = event.getChorusY();
        this.z = event.getChorusZ();
        this.timer.reset();
    }

    @Override
    public void onRender3D(Render3DEvent render3DEvent) {
        if (this.timer.passedMs(this.time.getCurrentState().intValue())) {
            return;
        }
        AxisAlignedBB pos = RenderUtil.interpolateAxis(new AxisAlignedBB(this.x - 0.3, this.y, this.z - 0.3, this.x + 0.3, this.y + 1.8, this.z + 0.3));
        if (this.outline.getCurrentState().booleanValue()) {
            RenderUtil.drawBlockOutline(pos, new Color(this.outlineR.getCurrentState(), this.outlineG.getCurrentState(), this.outlineB.getCurrentState(), this.outlineA.getCurrentState()), this.lineWidth.getCurrentState().floatValue());
        }
        if (this.box.getCurrentState().booleanValue()) {
            RenderUtil.drawFilledBox(pos, ColorUtil.toRGBA(this.boxR.getCurrentState(), this.boxG.getCurrentState(), this.boxB.getCurrentState(), this.boxA.getCurrentState()));
        }
    }
}