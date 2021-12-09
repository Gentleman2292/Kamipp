package ssl.wastaken.kamipp.features.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import ssl.wastaken.kamipp.event.events.EventBlock;
import ssl.wastaken.kamipp.event.events.EventRender3D;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.util.europautils.*;
import java.awt.Color;

public class PacketMine extends Module
{
    public static Boolean autoSwitch;
    public static Number resetRange;
    public static Enum renderMode;
    public static Boolean statusColor;
    public static Enum statusMode;
    public static Boolean render;
    public static Boolean syncColor;
    public static Color daColor;
    public Color fagColor;
    public BlockPos renderPos;
    public BlockPos breakPos;
    public EnumFacing breakFace;
    public TimerUtils timer;
    public boolean readyToMine;
    public int oldSlot;

    public PacketMine() {
        super("PacketMine", "PacketMine", Category.PLAYER, false, false, true);
        this.renderPos = null;
        this.breakPos = null;
        this.breakFace = null;
        this.timer = new TimerUtils();
        this.readyToMine = false;
        this.oldSlot = -1;
    }

    @Override
    public void onUpdate() {
        if (PacketMine.syncColor.getValue()) {
            this.fagColor = Module.globalColor(255);
        }
        else {
            this.fagColor = PacketMine.daColor.getValue();
        }
        if (PacketMine.mc.player != null) {
            if (PacketMine.mc.world != null) {
                if (this.breakPos != null) {
                    this.oldSlot = PacketMine.mc.player.inventory.currentItem;
                    if (PacketMine.mc.player.getDistanceSq(this.breakPos) > MathUtils.square(PacketMine.resetRange.getValue().doubleValue()) || PacketMine.mc.world.getBlockState(this.breakPos).getBlock() == Blocks.AIR) {
                        this.breakPos = null;
                        this.breakFace = null;
                        this.readyToMine = false;
                        return;
                    }
                    if (PacketMine.render.getValue()) {
                        this.renderPos = this.breakPos;
                    }
                    final float breakTime = PacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)PacketMine.mc.world, this.breakPos) * Float.intBitsToFloat(Float.floatToIntBits(0.26427442f) ^ 0x7F274EFA) * Float.intBitsToFloat(Float.floatToIntBits(0.9389823f) ^ 0x7F706125);
                    if (this.timer.hasReached((long)breakTime)) {
                        this.readyToMine = true;
                    }
                    if (PacketMine.autoSwitch.getValue()) {
                        if (this.timer.hasReached((long)breakTime) && InventoryUtils.getHotbarItemSlot(Items.DIAMOND_PICKAXE) != -1) {
                            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.pickaxeInHotbar()));
                        }
                        PacketMine.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
                        if (this.oldSlot != -1) {
                            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRender3D(final EventRender3D event) {
        if (PacketMine.mc.player == null || PacketMine.mc.world == null) {
            return;
        }
        if (PacketMine.render.getValue()) {
            if (this.breakPos != null) {
                if (PacketMine.mc.world.getBlockState(this.breakPos).getBlock() != Blocks.AIR) {
                    final Color defColor = new Color(this.fagColor.getRed(), this.fagColor.getGreen(), this.fagColor.getBlue(), PacketMine.daColor.getValue().getAlpha());
                    final AxisAlignedBB bb = new AxisAlignedBB(this.breakPos.getX() - PacketMine.mc.getRenderManager().viewerPosX, this.breakPos.getY() - PacketMine.mc.getRenderManager().viewerPosY, this.breakPos.getZ() - PacketMine.mc.getRenderManager().viewerPosZ, this.breakPos.getX() + 1 - PacketMine.mc.getRenderManager().viewerPosX, this.breakPos.getY() + 1 - PacketMine.mc.getRenderManager().viewerPosY, this.breakPos.getZ() + 1 - PacketMine.mc.getRenderManager().viewerPosZ);
                    final float breakTime = PacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)PacketMine.mc.world, this.breakPos) * Float.intBitsToFloat(Float.floatToIntBits(0.87076825f) ^ 0x7EFEEAAB) * Float.intBitsToFloat(Float.floatToIntBits(0.5921152f) ^ 0x7F1794DD);
                    final double progression = this.timer.getTimePassed() * TPSUtils.getTpsFactor() / Float.intBitsToFloat(Float.floatToIntBits(0.03283384f) ^ 0x7E4E7CC7);
                    final double oldMaxY = bb.maxY;
                    final double centerX = bb.minX + (bb.maxX - bb.minX) / Double.longBitsToDouble(Double.doubleToLongBits(0.4677600299948414) ^ 0x7FDDEFC7C3CD0B6FL);
                    final double centerY = bb.minY + (bb.maxY - bb.minY) / Double.longBitsToDouble(Double.doubleToLongBits(0.2265017733140163) ^ 0x7FCCFE02966F5283L);
                    final double centerZ = bb.minZ + (bb.maxZ - bb.minZ) / Double.longBitsToDouble(Double.doubleToLongBits(0.7187776739735182) ^ 0x7FE7003A0959F571L);
                    final double increaseX = progression * ((bb.maxX - centerX) / Double.longBitsToDouble(Double.doubleToLongBits(0.13526251945472825) ^ 0x7FE5504840B76025L));
                    final double increaseY = progression * ((bb.maxY - centerY) / Double.longBitsToDouble(Double.doubleToLongBits(0.1390228780814261) ^ 0x7FE5CB806D60B4E4L));
                    final double increaseZ = progression * ((bb.maxZ - centerZ) / Double.longBitsToDouble(Double.doubleToLongBits(0.2497058873358051) ^ 0x7FEBF65CCDDCEBB7L));
                    final double upY = progression * ((bb.maxY - bb.minY) / Double.longBitsToDouble(Double.doubleToLongBits(1.8254804590156823) ^ 0x7FD9352AFF6F59CDL));
                    final AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - increaseX, centerY - increaseY, centerZ - increaseZ, centerX + increaseX, centerY + increaseY, centerZ + increaseZ);
                    final AxisAlignedBB riseBB = new AxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY + upY, bb.maxZ);
                    final float sGreen = MathHelper.clamp((float)upY, Float.intBitsToFloat(Float.floatToIntBits(2.0187074E38f) ^ 0x7F17DEE4), Float.intBitsToFloat(Float.floatToIntBits(19.36041f) ^ 0x7E1AE21F));
                    final Color color = PacketMine.statusMode.getValue().equals(statusModes.Static) ? new Color(this.timer.hasReached((long)breakTime) ? 0 : 255, this.timer.hasReached((long)breakTime) ? 255 : 0, 0, PacketMine.daColor.getValue().getAlpha()) : new Color(255 - (int)(sGreen * Float.intBitsToFloat(Float.floatToIntBits(0.068660654f) ^ 0x7EF39DF5)), (int)(sGreen * Float.intBitsToFloat(Float.floatToIntBits(0.012068316f) ^ 0x7F3ABA30)), 0, PacketMine.daColor.getValue().getAlpha());
                    final float fadeA = MathHelper.clamp((float)upY, Float.intBitsToFloat(Float.floatToIntBits(7.600084E37f) ^ 0x7E64B4EB), Float.intBitsToFloat(Float.floatToIntBits(16.607412f) ^ 0x7E04DBFB));
                    final float shrinkFactor = MathHelper.clamp((float)increaseX, Float.intBitsToFloat(Float.floatToIntBits(-14.816234f) ^ 0x7EED0F4B), Float.intBitsToFloat(Float.floatToIntBits(9.43287f) ^ 0x7E96ED09));
                    final Color alphaFade = new Color(defColor.getRed(), defColor.getGreen(), defColor.getBlue(), (int)(fadeA * Float.intBitsToFloat(Float.floatToIntBits(0.008138063f) ^ 0x7F7A5583)));
                    if (PacketMine.renderMode.getValue().equals(renderModes.Rise)) {
                        if (riseBB.maxY <= oldMaxY) {
                            RenderUtils.drawFilledBox(riseBB, PacketMine.statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                            RenderUtils.drawBlockOutline(riseBB, PacketMine.statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(22.925123f) ^ 0x7E3766A7));
                        }
                        if (riseBB.maxY >= oldMaxY) {
                            RenderUtils.drawFilledBox(bb, PacketMine.statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                            RenderUtils.drawBlockOutline(bb, PacketMine.statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(5.6391788f) ^ 0x7F347427));
                        }
                    }
                    else if (PacketMine.renderMode.getValue().equals(renderModes.Grow)) {
                        if (axisAlignedBB1.maxY <= oldMaxY) {
                            RenderUtils.drawFilledBox(axisAlignedBB1, PacketMine.statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                            RenderUtils.drawBlockOutline(axisAlignedBB1, PacketMine.statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(7.8420115f) ^ 0x7F7AF1C2));
                        }
                        if (axisAlignedBB1.maxY >= oldMaxY) {
                            RenderUtils.drawFilledBox(bb, PacketMine.statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                            RenderUtils.drawBlockOutline(bb, PacketMine.statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(117.64367f) ^ 0x7D6B498F));
                        }
                    }
                    else if (PacketMine.renderMode.getValue().equals(renderModes.Static)) {
                        RenderUtils.drawFilledBox(bb, PacketMine.statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                        RenderUtils.drawBlockOutline(bb, PacketMine.statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(5.7362022f) ^ 0x7F378EF8));
                    }
                    else if (PacketMine.renderMode.getValue().equals(renderModes.Fade)) {
                        RenderUtils.drawFilledBox(bb, PacketMine.statusColor.getValue() ? new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(fadeA * Float.intBitsToFloat(Float.floatToIntBits(0.012192621f) ^ 0x7F38C38F))).getRGB() : alphaFade.getRGB());
                        RenderUtils.drawBlockOutline(bb, PacketMine.statusColor.getValue() ? new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(fadeA * Float.intBitsToFloat(Float.floatToIntBits(0.07938013f) ^ 0x7EDD920D))) : alphaFade, Float.intBitsToFloat(Float.floatToIntBits(5.6802325f) ^ 0x7F35C477));
                    }
                    else if (PacketMine.renderMode.getValue().equals(renderModes.Animate)) {
                        RenderUtils.drawFilledBox(RenderUtils.fixBB(new AxisAlignedBB(this.breakPos)).shrink((double)shrinkFactor), PacketMine.statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                        RenderUtils.drawBlockOutline(RenderUtils.fixBB(new AxisAlignedBB(this.breakPos)).shrink((double)shrinkFactor), PacketMine.statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(119.3883f) ^ 0x7D6EC6CF));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlock(final EventBlock event) {
        if (PacketMine.mc.player == null || PacketMine.mc.world == null) {
            return;
        }
        if (canBlockBeBroken(event.pos)) {
            if (this.breakPos == null) {
                this.breakPos = event.pos;
                this.breakFace = event.facing;
                this.readyToMine = false;
                this.timer.reset();
            }
            PacketMine.mc.player.swingArm(EnumHand.MAIN_HAND);
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, this.breakFace));
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
            event.setCancelled(true);
        }
    }

    public static boolean canBlockBeBroken(final BlockPos pos) {
        final IBlockState blockState = PacketMine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)PacketMine.mc.world, pos) != Float.intBitsToFloat(Float.floatToIntBits(-6.0870466f) ^ 0x7F42C916);
    }

    public int pickaxeInHotbar() {
        for (int i = 0; i < 9; ++i) {
            if (PacketMine.mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_PICKAXE) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onEnable() {
        this.breakPos = null;
    }

    @Override
    public void onDisable() {
        this.breakPos = null;
    }

    @Override
    public String getHudInfo() {
        String t = "";
        if (this.breakPos == null) {
            t = " [" + ChatFormatting.WHITE + "No Block" + ChatFormatting.GRAY + "]";
        }
        else if (this.breakPos != null && !this.readyToMine) {
            t = " [" + ChatFormatting.RED + "Not Ready" + ChatFormatting.GRAY + "]";
        }
        else if (this.breakPos != null) {
            if (this.readyToMine) {
                t = " [" + ChatFormatting.GREEN + "Ready" + ChatFormatting.GRAY + "]";
            }
        }
        return t;
    }

    static {
        PacketMine.autoSwitch = new ValueBoolean("AutoSwitch", "AutoSwitch", "", true);
        PacketMine.resetRange = new ValueNumber("ResetRange", "ResetRange", "", Double.longBitsToDouble(Double.doubleToLongBits(0.17561330259400176) ^ 0x7FE27A7F27B12004L), Double.longBitsToDouble(Double.doubleToLongBits(1.8612638262609325) ^ 0x7FE9C7BC93F04BE7L), Double.longBitsToDouble(Double.doubleToLongBits(1.7505843828671883) ^ 0x7FB50264C514D8BFL));
        PacketMine.renderMode = new ValueEnum("RenderMode", "RenderMode", "", renderModes.Grow);
        PacketMine.statusColor = new ValueBoolean("StatusColor", "StatusColor", "", false);
        PacketMine.statusMode = new ValueEnum("StatusMode", "StatusMode", "", statusModes.Static);
        PacketMine.render = new ValueBoolean("Render", "Render", "", false);
        PacketMine.syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
        PacketMine.daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    }

    public enum renderModes
    {
        Grow,
        Rise,
        Fade,
        Animate,
        Static;

        public static renderModes[] $VALUES;

        static {
            renderModes.$VALUES = new renderModes[] { renderModes.Grow, renderModes.Rise, renderModes.Fade, renderModes.Animate, renderModes.Static };
        }
    }

    public enum statusModes
    {
        Static,
        Smooth;

        public static statusModes[] $VALUES;

        static {
            statusModes.$VALUES = new statusModes[] { statusModes.Static, statusModes.Smooth };
        }
    }
}
