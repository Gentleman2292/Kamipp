package ssl.wastaken.kamipp.mixin.mixins.accessors.shaders;

import ssl.wastaken.kamipp.util.europautils.RenderUtils;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

public class RedShader extends FramebufferShader
{
    public static RedShader RED_SHADER;
    public float time;

    public RedShader() {
        super("red.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform2f(this.getUniform("resolution"), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight());
        GL20.glUniform1f(this.getUniform("time"), this.time);
        this.time += Float.intBitsToFloat(Float.floatToIntBits(626.72473f) ^ 0x7F5835C4) * RenderUtils.deltaTime;
    }

    static {
        RedShader.RED_SHADER = new RedShader();
    }
}
