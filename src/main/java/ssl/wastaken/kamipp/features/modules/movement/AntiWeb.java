package ssl.wastaken.kamipp.features.modules.movement;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.event.events.BlockCollisionBoundingBoxEvent;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AntiWeb
        extends Module {
    public Setting<Boolean> disableBB = this.register(new Setting<Boolean>("AddBB", true));
    public Setting<Double> bbOffset = this.register(new Setting<Double>("BoxOffset", 0.04, -1.0, 1.0));
    public Setting<Boolean> onGround = this.register(new Setting<Boolean>("OnGround", true));
    public Setting<Double> motionX = this.register(new Setting<Double>("MotionX", 0.8, -1.0, 5.0));
    public Setting<Double> motionY = this.register(new Setting<Double>("MotionY", -0.05, 0.0, 10.0));

    public AntiWeb() {
        super("AntiWeb", "Modifies movement in webs.", Module.Category.MOVEMENT, true, false, false);
    }

    @SubscribeEvent
    public void bbEvent(BlockCollisionBoundingBoxEvent event) {
        if (AntiWeb.nullCheck()) {
            return;
        }
        if (AntiWeb.mc.world.getBlockState(event.getPos()).getBlock() instanceof BlockWeb && this.disableBB.getValue().booleanValue()) {
            event.setCanceled(true);
            event.setBoundingBox(Block.FULL_BLOCK_AABB.contract(0.0, this.bbOffset.getValue().doubleValue(), 0.0));
        }
    }

    @Override
    public void onUpdate() {
        if (AntiWeb.mc.player.isInWeb && !Kami.moduleManager.isModuleEnabled("Step")) {
            if (Keyboard.isKeyDown((int)AntiWeb.mc.gameSettings.keyBindSneak.keyCode)) {
                AntiWeb.mc.player.isInWeb = true;
                AntiWeb.mc.player.motionY *= this.motionY.getValue().doubleValue();
            } else if (this.onGround.getValue().booleanValue()) {
                AntiWeb.mc.player.onGround = false;
            }
            if (Keyboard.isKeyDown((int)AntiWeb.mc.gameSettings.keyBindForward.keyCode) || Keyboard.isKeyDown((int)AntiWeb.mc.gameSettings.keyBindBack.keyCode) || Keyboard.isKeyDown((int)AntiWeb.mc.gameSettings.keyBindLeft.keyCode) || Keyboard.isKeyDown((int)AntiWeb.mc.gameSettings.keyBindRight.keyCode)) {
                AntiWeb.mc.player.isInWeb = false;
                AntiWeb.mc.player.motionX *= this.motionX.getValue().doubleValue();
                AntiWeb.mc.player.motionZ *= this.motionX.getValue().doubleValue();
            }
        }
    }
}
