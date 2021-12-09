package ssl.wastaken.kamipp.mixin.mixins.accessors.shaders;

import ssl.wastaken.kamipp.util.europautils.RenderUtils;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

public class AquaShader extends FramebufferShader
{
    public static AquaShader AQUA_SHADER;
    public float time;

    public AquaShader() {
        super("aqua.frag");
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
        this.time += Float.intBitsToFloat(Float.floatToIntBits(1015.0615f) ^ 0x7F395856) * RenderUtils.deltaTime;
    }

    static {
        AquaShader.AQUA_SHADER = new AquaShader();
    }
}