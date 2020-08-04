package me.ddgbootcamp;

import me.ddgbootcamp.utils.Utils;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ddgbootcamp.main.VanishList;

public class VanishCommand implements CommandExecutor {

    public Plugin plugin = main.getPlugin(main.class);
    //Er wordt een bossbar aangemaakt, voor extra duidelijkheid dat je vanished bent
    BossBar bossbar = Bukkit.getServer().createBossBar(Utils.chat("&6&lJe bent onzichtbaar"),BarColor.RED,BarStyle.SOLID);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && sender.hasPermission("vanish.use")){
            //Variabelen voor de onderstaande acties.
            Player player = (Player) sender;
            Location playerlocation = player.getLocation();
            Location spawnloc1 = player.getLocation().add(0,2,0);
            Location spawnloc2 = player.getLocation().add(0,1,1);
            Location spawnloc3 = player.getLocation().add(0,1,-1);
            Location spawnloc4 = player.getLocation().add(1,1,0);
            Location spawnloc5 = player.getLocation().add(-1,1,0);

            //Unvanishen van de speler als de speler vanish is
            if (VanishList.contains(player)){
                for (Player online : Bukkit.getServer().getOnlinePlayers()){
                    online.showPlayer(player); //Maak de speler weer zichtbaar voor alle spelers
                    VanishList.remove(player); //Gooi de speler uit de lijst met vanished-spelers
                    //Spawn de particles voor alle spelers die online zijn.
                    online.spawnParticle(Particle.REDSTONE,playerlocation,250,0.5,0,0.5,
                            new Particle.DustOptions(Color.fromBGR(0,21,174),1));
                }

                //Bats spawnen rond je als je uit vanish gaat
                Bat bat1 = (Bat)spawnloc1.getWorld().spawnEntity(spawnloc1, EntityType.BAT);
                Bat bat2 = (Bat)spawnloc2.getWorld().spawnEntity(spawnloc2, EntityType.BAT);
                Bat bat3 = (Bat)spawnloc3.getWorld().spawnEntity(spawnloc3, EntityType.BAT);
                Bat bat4 = (Bat)spawnloc4.getWorld().spawnEntity(spawnloc4, EntityType.BAT);
                Bat bat5 = (Bat)spawnloc5.getWorld().spawnEntity(spawnloc5, EntityType.BAT);
                

                //Delayed event, zodat de bats na een tijd weer dood gaan.
                new BukkitRunnable(){

                    @Override
                    public void run() {
                        //De bats worden naar 0 HP gebracht, zodat ze gekilled worden
                        bat1.setHealth(0);
                        bat2.setHealth(0);
                        bat3.setHealth(0);
                        bat4.setHealth(0);
                        bat5.setHealth(0);
                    }
                }.runTaskLater(plugin,15);
                player.playSound(playerlocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER,100,1);  //Bliksemsound afspelen voor de speler die uit vanish gaat
                player.sendMessage(Utils.chat("&c[DDG BC] &7Je bent weer zichtbaar!"));      //Speler duidelijk maken dat hij weer zichtbaar is
                bossbar.removePlayer(player);    //Speler wordt verwijderd uit de bossbar
                player.setCollidable(true);      //Speler is weer te duwen
                return true;
            }

            //Vanishen van de speler als de speler niet vanished is
            else if (!VanishList.contains(player)){
                for (Player online : Bukkit.getServer().getOnlinePlayers()){
                    online.hidePlayer(player);   //Maak de speler onzichtbaar voor alle spelers
                    VanishList.add(player);      //Gooi de speler in de lijst met vanished-spelers
                    //speler gaat op in rook particles
                    online.spawnParticle(Particle.CLOUD, playerlocation,250,0.1,-1,0.1,0.00005);
                }
                player.sendMessage(Utils.chat("&c[DDG BC] &7Je bent van de radar verdwenen!"));  //Speler duidelijk maken dat hij onzichtbaar is
                player.setCollidable(false);    //Speler is niet meer te duwen
                bossbar.addPlayer(player);      //Speler wordt toegevoegd aan de bossbar
                return true;

            }
        }else if (sender instanceof Player){
            //Maak de speler duidelijk dat hij geen permissies heeft om het vanish commando te gebruiken
            sender.sendMessage(Utils.chat("&c[DDG BC] &7Je hebt geen permissies om dit uit te voeren!"));
        }
        return false;
    }

}
