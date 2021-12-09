package ssl.wastaken.kamipp.mixin.mixins;

import ssl.wastaken.kamipp.event.events.RenderItemEvent;
import ssl.wastaken.kamipp.features.modules.render.NoRender;
import ssl.wastaken.kamipp.features.modules.render.ViewModel;
import ssl.wastaken.kamipp.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemRenderer.class})
public abstract class MixinItemRenderer {
    private boolean injection = true;

    @Shadow
    public abstract void renderItemInFirstPerson(AbstractClientPlayer var1, float var2, float var3, EnumHand var4, float var5, ItemStack var6, float var7);

    @Inject(method = {"transformSideFirstPerson"}, at = {@At(value = "HEAD")}, cancellable = true)
    public
    void transformSideFirstPerson (EnumHandSide hand , float p_187459_2_ , CallbackInfo cancel) {
        RenderItemEvent event = new RenderItemEvent(
                0 , 0 , 0 ,
                0 , 0 , 0 ,
                0.0 , 0.0 , 1.0 ,
                0.0 , 0.0 , 0.0 ,
                1.0 , 1.0 , 1.0 , 1.0 ,
                1.0 , 1.0
        );
        MinecraftForge.EVENT_BUS.post(event);
        if (ViewModel.getInstance().isEnabled()) {
            boolean bob = ViewModel.getInstance().isDisabled() || ViewModel.getInstance().doBob.getValue();
            int i = hand == EnumHandSide.RIGHT ? 1 : - 1;
            GlStateManager.translate((float) i * 0.56F, -0.52F + (bob ? p_187459_2_ : 0) * -0.6F, -0.72F);
            if (hand == EnumHandSide.RIGHT) {
                GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
                RenderUtil.rotationHelper((float)event.getMainRotX(), (float)event.getMainRotY() ,(float)event.getMainRotZ());
            } else {
                GlStateManager.translate(event.getOffX(), event.getOffY(), event.getOffZ());
                RenderUtil.rotationHelper((float)event.getOffRotX() , (float)event.getOffRotY(), (float)event.getOffRotZ());
            }
            cancel.cancel();
        }
    }

    @Inject(method={"renderFireInFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderFireInFirstPersonHook(CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().fire.getValue().booleanValue()) {
            info.cancel();
        }
    }

    @Inject(method = {"transformEatFirstPerson"}, at = {@At(value = "HEAD")}, cancellable = true)
    private
    void transformEatFirstPerson (float p_187454_1_ , EnumHandSide hand , ItemStack stack , CallbackInfo cancel) {
        if (ViewModel.getInstance().isEnabled()) {
            if (!ViewModel.getInstance().noEatAnimation.getValue()) {
                float f = (float)Minecraft.getMinecraft().player.getItemInUseCount() - p_187454_1_ + 1.0F;
                float f1 = f / (float)stack.getMaxItemUseDuration();
                float f3;
                if (f1 < 0.8F) {
                    f3 = MathHelper.abs (MathHelper.cos (f / 4.0F * 3.1415927F) * 0.1F);
                    GlStateManager.translate (0.0F , f3 , 0.0F);
                }
                f3 = 1.0F - (float) Math.pow (f1 , 27.0D);
                int i = hand == EnumHandSide.RIGHT ? 1 : -1;
                GlStateManager.translate ( f3 * 0.6F * (float) i * ViewModel.getInstance().eatX.getValue(), f3 * 0.5F * - ViewModel.getInstance ( ).eatY.getValue ( ) , 0.0F );
                GlStateManager.rotate((float) i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate((float) i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
            }
            cancel.cancel();
        }
    }

    @Inject(method={"renderSuffocationOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderSuffocationOverlay(CallbackInfo ci) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().blocks.getValue().booleanValue()) {
            ci.cancel();
        }
    }
}

