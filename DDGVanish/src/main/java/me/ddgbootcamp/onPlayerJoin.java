package me.ddgbootcamp;

import me.ddgbootcamp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.ddgbootcamp.main.VanishList;

public class onPlayerJoin implements Listener {
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        Player join = e.getPlayer();
        //Alle online spelers ophalen
        for (Player online : Bukkit.getServer().getOnlinePlayers()){
            //Alle online spelers die in de vanishlist staan hiden
            if (VanishList.contains(online)){
                join.hidePlayer(online);
            }
        }
    }
}
