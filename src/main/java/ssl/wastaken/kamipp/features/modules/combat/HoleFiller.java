package ssl.wastaken.kamipp.features.modules.combat;

import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.event.events.PacketEvent;
import ssl.wastaken.kamipp.event.events.Render3DEvent;
import ssl.wastaken.kamipp.features.modules.client.Colors;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.features.modules.Module;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ssl.wastaken.kamipp.util.EntityUtil;
import ssl.wastaken.kamipp.util.InventoryUtil;
import ssl.wastaken.kamipp.util.RenderUtil;
import ssl.wastaken.kamipp.util.TestUtil;

import java.util.*;
import java.util.stream.Collectors;

public class HoleFiller
        extends Module {
    private static BlockPos PlayerPos;
    private Setting<Double> range = this.register(new Setting<Double>("Range", 4.5, 0.1, 6.0));
    private Setting<Boolean> smart = this.register(new Setting<Boolean>("Smart", false));
    private Setting<Integer> smartRange = this.register(new Setting<Object>("Smart Range", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(6), v -> this.smart.getValue()));
    private BlockPos render;
    private Entity renderEnt;
    private EntityPlayer closestTarget;
    private int newSlot;
    double d;
    private static boolean isSpoofingAngles;
    private static float yaw;
    private static float pitch;
    private static HoleFiller INSTANCE;

    public HoleFiller() {
        super("HoleFiller", "Fills holes around you.", Module.Category.COMBAT, true, false, true);
        this.setInstance();
    }

    public static HoleFiller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HoleFiller();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        Object packet = event.getPacket();
        if (packet instanceof CPacketPlayer && isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = yaw;
            ((CPacketPlayer)packet).pitch = pitch;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        if (HoleFiller.mc.world == null) {
            return;
        }
        if (this.smart.getValue().booleanValue()) {
            this.findClosestTarget();
        }
        List<BlockPos> blocks = this.findCrystalBlocks();
        BlockPos q = null;
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1 && eChestSot == -1) {
            return;
        }
        int originalSlot = HoleFiller.mc.player.inventory.currentItem;
        for (BlockPos blockPos : blocks) {
            if (!HoleFiller.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) continue;
            if (this.smart.getValue().booleanValue() && this.isInRange(blockPos)) {
                q = blockPos;
                continue;
            }
            q = blockPos;
        }
        this.render = q;
        if (q != null && HoleFiller.mc.player.onGround) {
            HoleFiller.mc.player.inventory.currentItem = obbySlot == -1 ? eChestSot : obbySlot;
            HoleFiller.mc.playerController.updateController();
            this.lookAtPacket((double)q.getX() + 0.5, (double)q.getY() - 0.5, (double)q.getZ() + 0.5, (EntityPlayer)HoleFiller.mc.player);
            TestUtil.placeBlock(this.render);
            if (HoleFiller.mc.player.inventory.currentItem != originalSlot) {
                HoleFiller.mc.player.inventory.currentItem = originalSlot;
                HoleFiller.mc.playerController.updateController();
            }
            HoleFiller.mc.player.swingArm(EnumHand.MAIN_HAND);
            HoleFiller.mc.player.inventory.currentItem = originalSlot;
            HoleFiller.resetRotation();
        }
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (this.render != null) {
            RenderUtil.drawBoxESP(this.render, Colors.INSTANCE.getCurrentColor(), false, Colors.INSTANCE.getCurrentColor(), 2.0f, true, true, 150, true, -0.9, false, false, false, false, 255);
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        HoleFiller.setYawAndPitch((float)v[0], (float)v[1]);
    }

    private boolean IsHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);
        return !(HoleFiller.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || HoleFiller.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR || HoleFiller.mc.world.getBlockState(boost7).getBlock() != Blocks.AIR || HoleFiller.mc.world.getBlockState(boost3).getBlock() != Blocks.OBSIDIAN && HoleFiller.mc.world.getBlockState(boost3).getBlock() != Blocks.BEDROCK || HoleFiller.mc.world.getBlockState(boost4).getBlock() != Blocks.OBSIDIAN && HoleFiller.mc.world.getBlockState(boost4).getBlock() != Blocks.BEDROCK || HoleFiller.mc.world.getBlockState(boost5).getBlock() != Blocks.OBSIDIAN && HoleFiller.mc.world.getBlockState(boost5).getBlock() != Blocks.BEDROCK || HoleFiller.mc.world.getBlockState(boost6).getBlock() != Blocks.OBSIDIAN && HoleFiller.mc.world.getBlockState(boost6).getBlock() != Blocks.BEDROCK || HoleFiller.mc.world.getBlockState(boost8).getBlock() != Blocks.AIR || HoleFiller.mc.world.getBlockState(boost9).getBlock() != Blocks.OBSIDIAN && HoleFiller.mc.world.getBlockState(boost9).getBlock() != Blocks.BEDROCK);
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleFiller.mc.player.posX), Math.floor(HoleFiller.mc.player.posY), Math.floor(HoleFiller.mc.player.posZ));
    }

    public BlockPos getClosestTargetPos() {
        if (this.closestTarget != null) {
            return new BlockPos(Math.floor(this.closestTarget.posX), Math.floor(this.closestTarget.posY), Math.floor(this.closestTarget.posZ));
        }
        return null;
    }

    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)HoleFiller.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target != HoleFiller.mc.player && !Kami.friendManager.isFriend(target.getName()) && EntityUtil.isLiving((Entity)target)) {
                if (target.getHealth() <= 0.0f) {
                    continue;
                }
                if (this.closestTarget == null) {
                    this.closestTarget = target;
                }
                else {
                    if (HoleFiller.mc.player.getDistance((Entity)target) >= HoleFiller.mc.player.getDistance((Entity)this.closestTarget)) {
                        continue;
                    }
                    this.closestTarget = target;
                }
            }
        }
    }

    private boolean isInRange(BlockPos blockPos) {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(HoleFiller.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        return positions.contains((Object)blockPos);
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList positions = NonNullList.create();
        if (this.smart.getValue().booleanValue() && this.closestTarget != null) {
            positions.addAll((Collection)this.getSphere(this.getClosestTargetPos(), this.smartRange.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList()));
        } else if (!this.smart.getValue().booleanValue()) {
            positions.addAll((Collection)this.getSphere(HoleFiller.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
        }
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f2;
                    float f = y;
                    float f3 = f2 = sphere ? (float)cy + r : (float)(cy + h);
                    if (!(f < f2)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = HoleFiller.mc.player.rotationYaw;
            pitch = HoleFiller.mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    @Override
    public void onDisable() {
        this.closestTarget = null;
        this.render = null;
        HoleFiller.resetRotation();
        super.onDisable();
    }

    static {
        INSTANCE = new HoleFiller();
    }
}
