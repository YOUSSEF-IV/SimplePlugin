package org.example;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleCommandBiome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        String commandName = command.getName().toLowerCase();
        Player player = (Player) sender;

        switch (commandName) {
            case "biomes" -> handlebiomesCommand(player);
            case "setbiomes" -> handlesetbiomesCommand(player);
        }

        return true;
    }

    private void handlesetbiomesCommand(Player player) {
    }

    private void handlebiomesCommand(Player player) {
           Biome biome = getBiomes(player);
           player.sendMessage("le biome est :"+ biome);
    }

    private Biome getBiomes(Player player){
        return player.getWorld().getBiome(player.getLocation());
    }


}
