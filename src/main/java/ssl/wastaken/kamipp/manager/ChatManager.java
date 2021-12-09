package ssl.wastaken.kamipp.manager;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.Minecraft;

public class ChatManager
{
    public Minecraft mc;
    public GuiNewChat gameChatGUI;
    public static String prefix;
    public static ChatManager INSTANCE;

    public ChatManager() {
        this.mc = Minecraft.getMinecraft();
        ChatManager.INSTANCE = this;
    }

    public void printChatMessage(final String message) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
    }

    public static void sendRawMessage(final String message) {
        Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(message));
    }

}