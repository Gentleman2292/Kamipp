package ssl.wastaken.kamipp;

import me.zero.alpine.bus.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import ssl.wastaken.kamipp.features.modules.client.RPC;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import ssl.wastaken.kamipp.manager.*;

import java.awt.*;
import java.net.URI;

@Mod(modid = "Kami", name = "Kami++", version = "6.9")
public class Kami {
    public static final String MODID = "Kami";
    public static final String MODNAME = "Kami++";
    public static final String MODVER = "6.9";
    public static final Logger LOGGER = LogManager.getLogger("Kami++");
    private static String name = "Kami++";
    public static ModuleManager moduleManager;
    public static SpeedManager speedManager;
    public static PositionManager positionManager;
    public static RotationManager rotationManager;
    public static CommandManager commandManager;
    public static ConfigManager configManager;
    public static EventManager eventManager;
    public static FileManager fileManager;
    public static FriendManager friendManager;
    public static TextManager textManager;
    public static ColorManager colorManager;
    public static ServerManager serverManager;
    public static PotionManager potionManager;
    public static InventoryManager inventoryManager;
    public static TimerManager timerManager;
    public static PacketManager packetManager;
    public static ReloadManager reloadManager;
    public static TotemPopManager totemPopManager;
    public static HoleManager holeManager;
    public static SafetyManager safetyManager;
    public static FontManager fontManager;
    @Mod.Instance
    public static Kami INSTANCE;
    private static boolean unloaded;

    public static String getName() {
        return name;
    }

    public static void setName(String newName) {
        name = newName;
    }

    static {
        unloaded = false;
    }

    public static void load() {
        LOGGER.info("\n\nLoading Kami++ 6.9");
        unloaded = false;
        if (reloadManager != null) {
            reloadManager.unload();
            reloadManager = null;
        }
        totemPopManager = new TotemPopManager();
        timerManager = new TimerManager();
        packetManager = new PacketManager();
        serverManager = new ServerManager();
        colorManager = new ColorManager();
        textManager = new TextManager();
        moduleManager = new ModuleManager();
        speedManager = new SpeedManager();
        rotationManager = new RotationManager();
        positionManager = new PositionManager();
        commandManager = new CommandManager();
        eventManager = new EventManager();
        configManager = new ConfigManager();
        fileManager = new FileManager();
        friendManager = new FriendManager();
        potionManager = new PotionManager();
        inventoryManager = new InventoryManager();
        holeManager = new HoleManager();
        safetyManager = new SafetyManager();
        fontManager = new FontManager();
        LOGGER.info("Initialized Managers");
        moduleManager.init();
        LOGGER.info("Modules loaded.");
        configManager.init();
        eventManager.init();
        LOGGER.info("EventManager loaded.");
        textManager.init(true);
        moduleManager.onLoad();
        totemPopManager.init();
        timerManager.init();
        if (moduleManager.getModuleByClass(RPC.class).isEnabled()) {
            DiscordPresence.start();
        }
        LOGGER.info("Kami++ initialized!\n");
    }

    public static void unload(boolean unload) {
        LOGGER.info("\n\nUnloading Kami++ 6.9");
        if (unload) {
            reloadManager = new ReloadManager();
            reloadManager.init(commandManager != null ? commandManager.getPrefix() : ".");
        }
        Kami.onUnload();
        eventManager = null;
        holeManager = null;
        timerManager = null;
        moduleManager = null;
        totemPopManager = null;
        serverManager = null;
        colorManager = null;
        textManager = null;
        speedManager = null;
        rotationManager = null;
        positionManager = null;
        commandManager = null;
        configManager = null;
        fileManager = null;
        friendManager = null;
        potionManager = null;
        inventoryManager = null;
        safetyManager = null;
        fontManager = null;
        LOGGER.info("Kami++ unloaded!\n");
    }

    public static void reload() {
        Kami.unload(false);
        Kami.load();
    }

    public static void onUnload() {
        if (!unloaded) {
            eventManager.onUnload();
            moduleManager.onUnload();
            configManager.saveConfig(Kami.configManager.config.replaceFirst("Kami/", ""));
            moduleManager.onUnloadPost();
            timerManager.unload();
            unloaded = true;
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("OMG!! I LOVE NATERRK SO VERY MUCH");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("Kami++ - v0.0.1b");
        Kami.load();
    }

    @Mod.EventHandler
    public void postinit(final FMLPostInitializationEvent event) {
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://discord.gg/z8Re8xd67h"));
            }
            catch (Throwable t) {}
        }
        Minecraft.getMinecraft();
    }
    public static final EventBus EVENT_BUS = (EventBus) new EventManager();
}

