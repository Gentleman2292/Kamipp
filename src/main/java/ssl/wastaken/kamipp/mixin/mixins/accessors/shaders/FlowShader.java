package ssl.wastaken.kamipp.mixin.mixins.accessors.shaders;

import ssl.wastaken.kamipp.util.europautils.RenderUtils;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

public class FlowShader extends FramebufferShader
{
    public static FlowShader FLOW_SHADER;
    public float time;

    public FlowShader() {
        super("flow.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform2f(this.getUniform("resolution"), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight());
        GL20.glUniform1f(this.getUniform("time"), Float.intBitsToFloat(Float.floatToIntBits(12.494699f) ^ 0x7EC7EA49));
        this.time += Float.intBitsToFloat(Float.floatToIntBits(24055.986f) ^ 0x7DFF745F) * RenderUtils.deltaTime;
    }

    static {
        FlowShader.FLOW_SHADER = new FlowShader();
    }
}
