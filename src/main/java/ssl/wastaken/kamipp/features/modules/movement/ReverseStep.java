package ssl.wastaken.kamipp.features.modules.movement;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.features.setting.Setting;
import net.minecraft.entity.Entity;

public class ReverseStep
        extends Module {
    private final Setting<Integer> speed = this.register(new Setting<Integer>("Speed", 8, 1, 20));
    private final Setting<Boolean> inliquid = this.register(new Setting<Boolean>("Liquid", false));
    private static ReverseStep INSTANCE = new ReverseStep();

    public ReverseStep() {
        super("ReverseStep", "always on the bottom.", Module.Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    public static ReverseStep getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReverseStep();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (ReverseStep.nullCheck()) {
            return;
        }
        if (ReverseStep.mc.player.isSneaking() || ReverseStep.mc.player.isDead || ReverseStep.mc.player.collidedHorizontally || !ReverseStep.mc.player.onGround || ReverseStep.mc.player.isInWater() && this.inliquid.getValue() == false || ReverseStep.mc.player.isInLava() && this.inliquid.getValue() == false || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown() || Kami.moduleManager.isModuleEnabled("Burrow") || ReverseStep.mc.player.noClip || Kami.moduleManager.isModuleEnabled("Packetfly") || Kami.moduleManager.isModuleEnabled("Strafe")) {
            return;
        }
        for (double y = 0.0; y < 90.5; y += 0.01) {
            if (ReverseStep.mc.world.getCollisionBoxes((Entity)ReverseStep.mc.player, ReverseStep.mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) continue;
            ReverseStep.mc.player.motionY = (float)(-this.speed.getValue().intValue()) / 10.0f;
            break;
        }
    }
}




