package me.ddgbootcamp;

import me.ddgbootcamp.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ddgbootcamp.main.VanishList;

public class VanishCommand implements CommandExecutor {

    public Plugin plugin = main.getPlugin(main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && sender.hasPermission("vanish.use")){
            //Variabelen voor de onderstaande acties.
            Player p = (Player) sender;
            Location loc = p.getLocation();
            Location spawnloc = p.getLocation().add(0,2,0);
            Location spawnloc2 = p.getLocation().add(0,1,1);
            Location spawnloc3 = p.getLocation().add(0,1,-1);
            Location spawnloc4 = p.getLocation().add(1,1,0);
            Location spawnloc5 = p.getLocation().add(-1,1,0);

            //Unvanishen van de speler als de speler vanish is
            if (VanishList.contains(p)){
                for (Player online : Bukkit.getServer().getOnlinePlayers()){
                    online.showPlayer(p); //Maak de speler weer zichtbaar voor alle spelers
                    VanishList.remove(p); //Gooi de speler uit de lijst met vanished-spelers
                    //Spawn de particles voor alle andere spelers die online zijn.
                    online.spawnParticle(Particle.REDSTONE,loc,250,0.5,0,0.5,new Particle.DustOptions(Color.fromBGR(0,21,174),1));
                }
                //Spawn de particles voor degene die uit vanish komt
                p.spawnParticle(Particle.REDSTONE,loc,250,0.5,0,0.5,new Particle.DustOptions(Color.fromBGR(0,21,174),1));

                //Bats spawnen rond je als je uit vanish gaat
                Bat bat = (Bat)spawnloc.getWorld().spawnEntity(spawnloc, EntityType.BAT);
                Bat bat2 = (Bat)spawnloc2.getWorld().spawnEntity(spawnloc2, EntityType.BAT);
                Bat bat3 = (Bat)spawnloc3.getWorld().spawnEntity(spawnloc3, EntityType.BAT);
                Bat bat4 = (Bat)spawnloc4.getWorld().spawnEntity(spawnloc4, EntityType.BAT);
                Bat bat5 = (Bat)spawnloc5.getWorld().spawnEntity(spawnloc5, EntityType.BAT);

                //Delayed event, zodat de bats na een tijd weer dood gaan.
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
                p.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER,100,1);  //Bliksemsound afspelen voor de speler die uit vanish gaat
                p.sendMessage(Utils.chat("&c[DDG BC] &7Je bent weer zichtbaar!"));      //Speler duidelijk maken dat hij weer zichtbaar is
                return true;
            }
            //Vanishen van de speler als de speler niet vanished is
            else if (!VanishList.contains(p)){
                for (Player online : Bukkit.getServer().getOnlinePlayers()){
                    online.hidePlayer(p);   //Maak de speler onzichtbaar voor alle spelers
                    VanishList.add(p);      //Gooi de speler in de lijst met vanished-spelers
                }
                p.sendMessage(Utils.chat("&c[DDG BC] &7Je bent van de radar verdwenen!"));  //Speler duidelijk maken dat hij onzichtbaar is
                return true;
            }
        }else if (sender instanceof Player){
            //Maak de speler duidelijk dat hij geen permissies heeft om het vanish commando te gebruiken
            sender.sendMessage(Utils.chat("&c[DDG BC] &7Je hebt geen permissies om dit uit te voeren!"));
        }
        return false;
    }

}
