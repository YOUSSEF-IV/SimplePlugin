package org.example;

import org.bukkit.plugin.java.JavaPlugin;

public class SimplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        this.getCommand("ping").setExecutor(new SimpleCommandPing());
        this.getCommand("pings").setExecutor(new SimpleCommandPing());
        this.getCommand("spawn").setExecutor(new SimpleSpawnCommand());
        this.getCommand("setspawn").setExecutor(new SimpleSpawnCommand());
        this.getCommand("go").setExecutor(new SimpleCommandHome(this));
        this.getCommand("set").setExecutor(new SimpleCommandHome(this));
        this.getCommand("check").setExecutor(new SimpleCommandHome(this));
        this.getCommand("delete").setExecutor(new SimpleCommandHome(this));
        this.getCommand("biomes").setExecutor(new SimpleCommandBiome());
        this.getCommand("go").setTabCompleter(new SimpleCommandHomeCompleter(this));
        this.getCommand("delete").setTabCompleter(new SimpleCommandHomeCompleter(this));
        getServer().getPluginManager().registerEvents(new SimpleTeleportListener(), this);

    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}

