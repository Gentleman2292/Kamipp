package ssl.wastaken.kamipp.features.gui.components.items.buttons;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.modules.client.ClickGui;
import ssl.wastaken.kamipp.features.modules.client.HUD;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.util.ColorUtil;
import ssl.wastaken.kamipp.util.MathUtil;
import ssl.wastaken.kamipp.util.RenderUtil;
import ssl.wastaken.kamipp.features.gui.KamippGui;
import ssl.wastaken.kamipp.util.Util;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class UnlimitedSlider
        extends Button {
    public Setting setting;

    public UnlimitedSlider(Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (ClickGui.getInstance().rainbowRolling.getValue().booleanValue()) {
            int color = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight)), Kami.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            int color1 = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight)), Kami.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            RenderUtil.drawGradientRect((float)((int)this.x), (float)((int)this.y), (float)this.width + 7.4f, (float)this.height, color, color1);
        } else {
            RenderUtil.drawRect(this.x, this.y, this.x + (float)this.width + 7.4f, this.y + (float)this.height - 0.5f, !this.isHovering(mouseX, mouseY) ? Kami.colorManager.getColorWithAlpha(Kami.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()) : Kami.colorManager.getColorWithAlpha(Kami.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()));
        }
        Kami.textManager.drawStringWithShadow(" - " + this.setting.getName() + " \u00a77" + this.setting.getValue() + "\u00a7r +", this.x + 2.3f, this.y - 1.7f - (float) KamippGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            Util.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.BLOCK_NOTE_HARP, (float)1.0f));
            if (this.isRight(mouseX)) {
                if (this.setting.getValue() instanceof Double) {
                    this.setting.setValue((Double)this.setting.getValue() + 1.0);
                } else if (this.setting.getValue() instanceof Float) {
                    this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() + 1.0f));
                } else if (this.setting.getValue() instanceof Integer) {
                    this.setting.setValue((Integer)this.setting.getValue() + 1);
                }
            } else if (this.setting.getValue() instanceof Double) {
                this.setting.setValue((Double)this.setting.getValue() - 1.0);
            } else if (this.setting.getValue() instanceof Float) {
                this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() - 1.0f));
            } else if (this.setting.getValue() instanceof Integer) {
                this.setting.setValue((Integer)this.setting.getValue() - 1);
            }
        }
    }

    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
    }

    @Override
    public boolean getState() {
        return true;
    }

    public boolean isRight(int x) {
        return (float)x > this.x + ((float)this.width + 7.4f) / 2.0f;
    }
}

