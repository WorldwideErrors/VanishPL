package me.ddgbootcamp;

import com.google.common.util.concurrent.Runnables;
import me.ddgbootcamp.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import static me.ddgbootcamp.main.VanishList;

public class VanishCommand implements CommandExecutor {

    public Plugin plugin = main.getPlugin(main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            Location loc = p.getLocation();
            Location spawnloc = p.getLocation().add(0,2,0);
            Location spawnloc2 = p.getLocation().add(0,1,1);
            Location spawnloc3 = p.getLocation().add(0,1,-1);
            Location spawnloc4 = p.getLocation().add(1,1,0);
            Location spawnloc5 = p.getLocation().add(-1,1,0);
            if (VanishList.contains(p)){
                //Unvanish
                for (Player online : Bukkit.getServer().getOnlinePlayers()){
                    online.showPlayer(p);
                    VanishList.remove(p);
                    online.spawnParticle(Particle.REDSTONE,loc,250,2,0,2,new Particle.DustOptions(Color.fromBGR(0,21,174),1));
                }
                p.spawnParticle(Particle.REDSTONE,loc,250,0.5,0,0.5,new Particle.DustOptions(Color.fromBGR(0,21,174),1));
                Bat bat = (Bat)spawnloc.getWorld().spawnEntity(spawnloc, EntityType.BAT);
                Bat bat2 = (Bat)spawnloc2.getWorld().spawnEntity(spawnloc2, EntityType.BAT);
                Bat bat3 = (Bat)spawnloc3.getWorld().spawnEntity(spawnloc3, EntityType.BAT);
                Bat bat4 = (Bat)spawnloc4.getWorld().spawnEntity(spawnloc4, EntityType.BAT);
                Bat bat5 = (Bat)spawnloc5.getWorld().spawnEntity(spawnloc5, EntityType.BAT);

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        bat.setHealth(0);
                        bat2.setHealth(0);
                        bat3.setHealth(0);
                        bat4.setHealth(0);
                        bat5.setHealth(0);
                    }
                }.runTaskLater(plugin,10);
                p.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER,100,1);
                p.sendMessage(Utils.chat("&c[DDG BC] &7Je bent zichtbaar"));
                return true;
            }
            else if (!VanishList.contains(p)){
                //Vanish
                for (Player online : Bukkit.getServer().getOnlinePlayers()){
                    online.hidePlayer(p);
                    VanishList.add(p);
                }
                p.sendMessage(Utils.chat("&c[DDG BC] &7Je bent onzichtbaar"));
                return true;
            }
        }
        return false;
    }

}
