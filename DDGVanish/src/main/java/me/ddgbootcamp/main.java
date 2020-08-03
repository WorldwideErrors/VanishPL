package me.ddgbootcamp;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class main extends JavaPlugin {

    public static Set<Player> VanishList = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("vanish").setExecutor(new VanishCommand());
        getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        VanishList.clear();
    }
}
