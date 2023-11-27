package org.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        String CommandName = command.getName().toLowerCase();
        switch (CommandName){
            case "spawn" -> handlespawncommand(player);
            case "setspawn"  -> handlesetspawncommand(player);
        }
       return true;
    }
    void handlesetspawncommand(Player player) {
        player.getWorld().setSpawnLocation(player.getLocation());
        player.sendMessage("Spawn Location set");
    }

    void handlespawncommand(Player player) {
        player.teleport(player.getWorld().getSpawnLocation());
        player.sendMessage("you're at spawn");
    }

}
