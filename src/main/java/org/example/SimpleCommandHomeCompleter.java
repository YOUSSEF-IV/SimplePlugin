package org.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleCommandHomeCompleter implements TabCompleter {
    private File dataFile;
    private FileConfiguration config;
    private JavaPlugin plugin;

    public SimpleCommandHomeCompleter(JavaPlugin plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        dataFile = new File(plugin.getDataFolder(), "homesManager.yml");
        if (!dataFile.exists()) {
            throw new IllegalCallerException("you can't create the file from this class");
        }
        config = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        Player player = (Player) sender;
        List<String> completions = new ArrayList<>();

        var config = getYamlConfig();
        if (command.getName().equalsIgnoreCase("delete") || command.getName().equalsIgnoreCase("go")) {
            if (args.length == 1 && config.contains("players." + player.getUniqueId().toString() + ".homes")) {
                for (String homeKey : config.getConfigurationSection("players." + player.getUniqueId().toString() + ".homes").getKeys(false)) {
                    String homeName = config.getString("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".NP");
                    if (homeName != null && homeName.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(homeName);
                    }
                }
            }
        }

        return completions;
    }

    private FileConfiguration getYamlConfig() {
        File dataFile = new File(plugin.getDataFolder(), "homesManager.yml");
        return YamlConfiguration.loadConfiguration(dataFile);
    }
}
