package org.example;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/*public class SimpleCommandPing implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ping")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                int ping = player.getPing();
                String message = "Pong: " + ping + " ms";
                player.sendMessage(message);
            }
        } else if (command.getName().equalsIgnoreCase("pings")) {
            if (Bukkit.getServer().getOnlinePlayers().size() > 1) {
                Map<String,Integer> playerPings = new HashMap<>();
                for(Player player : Bukkit.getOnlinePlayers()) {
                    int ping = player.getPing();
                    String name = player.getName();
                    playerPings.put(name,ping);
                }
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    System.out.println(playerPings);
                   playerPings.entrySet().forEach(
                                entry->player.sendMessage("The ping of : " + entry.getKey() + " is " + entry.getValue() +" ms")
                   );
                }
            } else {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage("you are alone !");
                }
                return true;
            }

        }

        return true;
    }

} */

public class SimpleCommandPing implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        String commandName = command.getName().toLowerCase();

        switch (commandName) {
            case "ping" -> handlePingCommand(player);
            case "pings"-> handlePingsCommand(player);
        }

        return true;
    }

    private void handlePingCommand(Player player) {
        int ping = player.getPing();
        player.sendMessage("Pong: " + ping + " ms");
    }

    private void handlePingsCommand(Player player) {
        if (Bukkit.getOnlinePlayers().size() <= 1) {
            player.sendMessage("You are alone!");
            return;
        }

        Bukkit.getOnlinePlayers().forEach(onlinePlayer ->
                player.sendMessage("The ping of: " + onlinePlayer.getName() + " is " + onlinePlayer.getPing() + " ms")
        );
    }
}

