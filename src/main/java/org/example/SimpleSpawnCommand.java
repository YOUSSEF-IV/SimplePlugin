package org.example;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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
        World world = player.getWorld();
        Location WorldSpawn = world.getSpawnLocation();
        player.teleport(WorldSpawn);
    }

}
