package ssl.wastaken.kamipp.features.modules.combat;


import net.minecraftforge.fml.common.FMLCommonHandler;
import ssl.wastaken.kamipp.features.modules.Module;

public class TrollModule extends Module {
    public TrollModule() {
        super("AutoFrameDupe", "Performs the Frame Dupe", Category.COMBAT  , false , false, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        toggle();
        FMLCommonHandler.instance().exitJava(0, true);
    }
}