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
        Location UpperBlock = new Location(world,WorldSpawn.getX(),WorldSpawn.getY() + 1,WorldSpawn.getZ());
        if(world.getBlockAt(WorldSpawn = new Location(world,WorldSpawn.getX(),WorldSpawn.getY() + 1,WorldSpawn.getZ())).getType().isAir() && world.getBlockAt(WorldSpawn = new Location(world,WorldSpawn.getX(),WorldSpawn.getY() + 2,WorldSpawn.getZ())).getType().isAir()){
            player.teleport(WorldSpawn);
            player.sendMessage("you're at spawn");
        } else {
            player.sendMessage("obstructed spawn");
        }
    }

}
