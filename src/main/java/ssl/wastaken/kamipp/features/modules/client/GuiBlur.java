package ssl.wastaken.kamipp.features.modules.client;

import ssl.wastaken.kamipp.util.Util;
import ssl.wastaken.kamipp.features.modules.Module;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

public class GuiBlur
        extends Module
        implements Util {
    public GuiBlur() {
        super("GUIBlur", "nigga", Category.CLIENT, true, false, false);
    }

    @Override
    public void onDisable() {
        if (GuiBlur.mc.world != null) {
            GuiBlur.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }

    @Override
    public void onUpdate() {
        if (GuiBlur.mc.world == null) return;
        if (!(ClickGui.getInstance().isEnabled() || GuiBlur.mc.currentScreen instanceof GuiContainer || GuiBlur.mc.currentScreen instanceof GuiChat || GuiBlur.mc.currentScreen instanceof GuiConfirmOpenLink)) {
            if (!(GuiBlur.mc.currentScreen instanceof GuiEditSign)) {
                if (!(GuiBlur.mc.currentScreen instanceof GuiGameOver)) {
                    if (!(GuiBlur.mc.currentScreen instanceof GuiOptions)) {
                        if (!(GuiBlur.mc.currentScreen instanceof GuiIngameMenu)) {
                            if (!(GuiBlur.mc.currentScreen instanceof GuiVideoSettings)) {
                                if (!(GuiBlur.mc.currentScreen instanceof GuiScreenOptionsSounds)) {
                                    if (!(GuiBlur.mc.currentScreen instanceof GuiControls)) {
                                        if (!(GuiBlur.mc.currentScreen instanceof GuiCustomizeSkin)) {
                                            if (!(GuiBlur.mc.currentScreen instanceof GuiModList)) {
                                                if (GuiBlur.mc.entityRenderer.getShaderGroup() == null) return;
                                                GuiBlur.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (GuiBlur.mc.entityRenderer.getShaderGroup() != null) {
                GuiBlur.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            try {
                GuiBlur.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (GuiBlur.mc.entityRenderer.getShaderGroup() == null) return;
        if (GuiBlur.mc.currentScreen != null) return;
        GuiBlur.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
    }
}


