package com.github.sirokuri_.onsen;

import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Onsen extends JavaPlugin implements Listener {

    public BukkitRunnable task = null;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new OnsenTipMessage(this), this);
        Bukkit.getPluginManager().registerEvents(new OnsenRequest(this), this);
        getCommand("onsen").setExecutor(new OnsenCommands(this));
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
    }
}
