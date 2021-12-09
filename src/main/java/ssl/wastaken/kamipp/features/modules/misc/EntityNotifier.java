package ssl.wastaken.kamipp.features.modules.misc;

import ssl.wastaken.kamipp.features.command.Command;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.features.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.init.SoundEvents;

import java.util.HashSet;
import java.util.Set;

public class EntityNotifier
        extends Module {
    private final Set<Entity> ghasts = new HashSet<Entity>();
    private final Set<Entity> donkeys = new HashSet<Entity>();
    private final Set<Entity> mules = new HashSet<Entity>();
    private final Set<Entity> llamas = new HashSet<Entity>();
    public Setting<Boolean> Chat = this.register(new Setting<Boolean>("Chat", true));
    public Setting<Boolean> Sound = this.register(new Setting<Boolean>("Sound", false));
    public Setting<Boolean> Ghasts = this.register(new Setting<Boolean>("Ghasts", true));
    public Setting<Boolean> Donkeys = this.register(new Setting<Boolean>("Donkeys", true));
    public Setting<Boolean> Mules = this.register(new Setting<Boolean>("Mules", true));
    public Setting<Boolean> Llamas = this.register(new Setting<Boolean>("Llamas", true));

    public EntityNotifier() {
        super("EntityNotifier", "Helps you find certain things", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        this.ghasts.clear();
        this.donkeys.clear();
        this.mules.clear();
        this.llamas.clear();
    }

    @Override
    public void onUpdate() {
        if (this.Ghasts.getValue().booleanValue()) {
            for (Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (!(entity instanceof EntityGhast) || this.ghasts.contains((Object)entity)) continue;
                if (this.Chat.getValue().booleanValue()) {
                    Command.sendMessage("Ghast Detected at: " + Math.round(entity.getPosition().getX()) + "x, " + Math.round(entity.getPosition().getY()) + "y, " + Math.round(entity.getPosition().getZ()) + "z.");
                }
                this.ghasts.add(entity);
                if (!this.Sound.getValue().booleanValue()) continue;
                EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            }
        }
        if (this.Donkeys.getValue().booleanValue()) {
            for (Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (!(entity instanceof EntityDonkey) || this.donkeys.contains((Object)entity)) continue;
                if (this.Chat.getValue().booleanValue()) {
                    Command.sendMessage("Donkey Detected at: " + Math.round(entity.getPosition().getX()) + "x, " + Math.round(entity.getPosition().getY()) + "y, " + Math.round(entity.getPosition().getZ()) + "z.");
                }
                this.donkeys.add(entity);
                if (!this.Sound.getValue().booleanValue()) continue;
                EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            }
        }
        if (this.Mules.getValue().booleanValue()) {
            for (Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (!(entity instanceof EntityMule) || this.mules.contains((Object)entity)) continue;
                if (this.Chat.getValue().booleanValue()) {
                    Command.sendMessage("Mule Detected at: " + Math.round(entity.getPosition().getX()) + "x, " + Math.round(entity.getPosition().getY()) + "y, " + Math.round(entity.getPosition().getZ()) + "z.");
                }
                this.mules.add(entity);
                if (!this.Sound.getValue().booleanValue()) continue;
                EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            }
        }
        if (this.Llamas.getValue().booleanValue()) {
            for (Entity entity : EntityNotifier.mc.world.getLoadedEntityList()) {
                if (!(entity instanceof EntityLlama) || this.llamas.contains((Object)entity)) continue;
                if (this.Chat.getValue().booleanValue()) {
                    Command.sendMessage("Llama Detected at: " + Math.round(entity.getPosition().getX()) + "x, " + Math.round(entity.getPosition().getY()) + "y, " + Math.round(entity.getPosition().getZ()) + "z.");
                }
                this.llamas.add(entity);
                if (!this.Sound.getValue().booleanValue()) continue;
                EntityNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            }
        }
    }
}