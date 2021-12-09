package ssl.wastaken.kamipp.features.modules.movement;

import ssl.wastaken.kamipp.event.events.MoveEvent;
import ssl.wastaken.kamipp.event.events.PacketEvent;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.util.PlayerUtils;
import ssl.wastaken.kamipp.util.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ssl.wastaken.kamipp.util.NullUtils;

public class PhaseWalk
        extends Module {
    Timer timer = new Timer();
    public Setting<Boolean> edgeEnable = this.register(new Setting<Boolean>("Edge Enable",false));
    public Setting<String> mode = this.register(new Setting<String>("Mode", "Clip", "Clip", "Smooth"));
    public Setting<Number> delay = this.register(new Setting<Number>("Delay",(200), (0), 1000));
    public Setting<Number> attempts = this.register(new Setting<Number>("Attempts", (5),(0), 10));
    public Setting<Boolean> cancelPlayer = this.register(new Setting<Boolean>("Cancel", true));
    public Setting<Mode> handleTeleport = this.register(new Setting<>("Teleport", Mode.All));
    public Setting<Double> limitAmount = this.register(new Setting<Number>("Limit Amount",0.3, 0, 1));
    public Setting<Number> speed = this.register(new Setting<Number>("Speed", 3, 1, 10));
    public Setting<Boolean> autoSpeed = this.register(new Setting<Boolean>("Auto Speed",true));
    boolean cancel = false;
    int teleportID = 0;

    public PhaseWalk() {
        super("Packetfly", "Uses packets to fly!", Module.Category.MOVEMENT, true, false, false);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (NullUtils.nullCheck()) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayer && this.cancel && this.cancelPlayer.getValue().booleanValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketConfirmTeleport && this.handleTeleport.getValue().equals("Cancel")) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.teleportID = ((SPacketPlayerPosLook)event.getPacket()).getTeleportId();
            if (this.handleTeleport.getValue().equals("All")) {
                mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID - 1));
                mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID));
                mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID + 1));
            }
            if (this.handleTeleport.getValue().equals("Below")) {
                mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID + -1));
            }
            if (this.handleTeleport.getValue().equals("Above")) {
                mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID + 1));
            }
            if (this.handleTeleport.getValue().equals("NoBand")) {
                mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(0.0, 1337.0, 0.0, PhaseWalk.mc.player.onGround));
                mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID + 1));
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (NullUtils.nullCheck()) {
            return;
        }
        PhaseWalk.mc.player.motionX = 0.0;
        PhaseWalk.mc.player.motionY = 0.0;
        PhaseWalk.mc.player.motionZ = 0.0;
        if (this.mode.getValue().equals("Clip")) {
            if (this.shouldPacket()) {
                if (this.timer.isPassed()) {
                    double[] forward = PlayerUtils.forward(this.getSpeed());
                    for (int i = 0; i < this.attempts.getValue().intValue(); ++i) {
                        this.sendPackets(PhaseWalk.mc.player.posX + forward[0], PhaseWalk.mc.player.posY + this.getUpMovement(), PhaseWalk.mc.player.posZ + forward[1]);
                    }
                    this.timer.resetDelay();
                }
            } else {
                this.cancel = false;
            }
        }
    }

    double getUpMovement() {
        return (double)(PhaseWalk.mc.gameSettings.keyBindJump.isKeyDown() ? 1 : (PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown() ? -1 : 0)) * this.getSpeed();
    }

    public void sendPackets(double x, double y, double z) {
        this.cancel = false;
        mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(x, y, z, PhaseWalk.mc.player.onGround));
        mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(0.0, 1337.0, 0.0, PhaseWalk.mc.player.onGround));
        this.cancel = true;
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (NullUtils.nullCheck()) {
            return;
        }
        if (this.shouldPacket()) {
            if (this.mode.getValue().equals("Smooth")) {
                double[] forward = PlayerUtils.forward(this.getSpeed());
                for (int i = 0; i < this.attempts.getValue().intValue(); ++i) {
                    this.sendPackets(PhaseWalk.mc.player.posX + forward[0], PhaseWalk.mc.player.posY + this.getUpMovement(), PhaseWalk.mc.player.posZ + forward[1]);
                }
            }
            event.x = 0.0;
            event.y = 0.0;
            event.z = 0.0;
        }
    }

    double getSpeed() {
        return this.autoSpeed.getValue() != false ? PlayerUtils.getDefaultMoveSpeed() / 10.0 : this.speed.getValue().doubleValue() / 100.0;
    }

    boolean shouldPacket() {
        return this.edgeEnable.getValue() == false || PhaseWalk.mc.player.collidedHorizontally;
    }


    public String getHudInfo() {
        return this.mode.getValue();
    }

    public enum Mode{
        All, Below, Above, NoBand, Last, Cancel, None
    }
}

