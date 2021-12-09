package ssl.wastaken.kamipp.util;


import ssl.wastaken.kamipp.mixin.mixins.accessors.IMinecraft;

public class NullUtils
        implements IMinecraft {
    public static boolean nullCheck() {
        return NullUtils.mc.player == null || NullUtils.mc.world == null || NullUtils.mc.playerController == null;
    }
}
