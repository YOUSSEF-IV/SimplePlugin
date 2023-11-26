package org.example;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


    public class SimpleCommandHome implements CommandExecutor {
        private File dataFile;
        private FileConfiguration config;
        private JavaPlugin plugin;

        public SimpleCommandHome(JavaPlugin plugin) {
            this.plugin = plugin;

            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }

            dataFile = new File(plugin.getDataFolder(), "homesManager.yml");
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            config = YamlConfiguration.loadConfiguration(dataFile);
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                return true;
            }
            String commandName = command.getName().toLowerCase();
            Player player = (Player) sender;


            if(commandName.equals("check")){
                handlecheckCommand(player);
                return true;
            }


            if (args.length == 0) {
                sender.sendMessage("Please specify a home name.");
                return true;
            } else if (args.length > 1) {
                sender.sendMessage("Too many args!");
                return true;
            }

            String NP = args[0];

            switch (commandName) {
                case "set" -> handlesetCommand(player, NP);
                case "go" -> handlehomeCommand(player, NP);
                case "delete" -> handledeleteCommand(player,NP);
            }
            return true;
        }


        private void handlesetCommand(Player player, String NP) {
            var config = getYamlConfig();

            boolean aliasUsed = false;

            if (config.contains("players." + player.getUniqueId().toString() + ".homes")) {
                for (String key : config.getConfigurationSection("players." + player.getUniqueId().toString() + ".homes").getKeys(false)) {
                    String existingNP = config.getString("players." + player.getUniqueId().toString() + ".homes." + key + ".NP");
                    if (existingNP != null && existingNP.equals(NP)) {
                        aliasUsed = true;
                        break;
                    }
                }
            }

            if (aliasUsed) {
                player.sendMessage("This alias is already used.");
            } else {
                String path = "players." + player.getUniqueId().toString() + ".homes." + NP;
                config.set(path + ".name", player.getName());
                config.set(path + ".world", player.getWorld().getName());
                config.set(path + ".x", player.getLocation().getX());
                config.set(path + ".y", player.getLocation().getY());
                config.set(path + ".z", player.getLocation().getZ());
                config.set(path + ".NP", NP);
                try {
                    config.save(dataFile);
                    player.sendMessage(NP + " set successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                    player.sendMessage("Error saving " + NP + ".");
                }
            }
        }

        private void handlehomeCommand(Player player, String NP) {
            var config = getYamlConfig();

            if (config.contains("players." + player.getUniqueId().toString() + ".homes")) {
                for (String homeKey : config.getConfigurationSection("players." + player.getUniqueId().toString() + ".homes").getKeys(false)) {
                    if (config.getString("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".NP").equals(NP)) {
                        World world = Bukkit.getWorld(config.getString("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".world"));
                        double x = config.getDouble("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".x");
                        double y = config.getDouble("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".y");
                        double z = config.getDouble("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".z");
                        Location home = new Location(world, x, y, z);
                        player.teleport(home);
                        return;
                    }
                }
            }
            player.sendMessage(NP + " not found.");
        }

    private void handlecheckCommand(Player player) {
        var config = getYamlConfig();
        if (config.contains("players." + player.getUniqueId().toString() + ".homes")) {
            for (String homeKey : config.getConfigurationSection("players." + player.getUniqueId().toString() + ".homes").getKeys(false)) {
                String homeName = config.getString("players." + player.getUniqueId().toString() + ".homes." + homeKey + ".NP");
                if (homeName != null) {
                    player.sendMessage(homeName);
                }
           }
       }
    }

    private void handledeleteCommand(Player player,String toDelete) {
        var config = getYamlConfig();
        String path = "players." + player.getUniqueId().toString() + ".homes." + toDelete;
        if (config.contains(path)) {
            config.set(path, null);
            try {
                config.save(dataFile);
                player.sendMessage("Home '" + toDelete + "' deleted successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                player.sendMessage("Error while deleting home.");
            }
        } else {
            player.sendMessage("Home '" + toDelete + "' not found.");
        }
    }


  private FileConfiguration getYamlConfig() {
      File dataFile = new File(plugin.getDataFolder(), "homesManager.yml");
      return YamlConfiguration.loadConfiguration(dataFile);
  }
}




