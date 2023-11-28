package org.example;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SimpleTeleportListener implements Listener {
    @EventHandler
     public void onTeleport(PlayerTeleportEvent event){
        World world = event.getPlayer().getWorld();
        Location newPos = event.getTo();

        Location checkLocation1 = new Location(world, newPos.getX(), newPos.getY() + 1, newPos.getZ());
        Location checkLocation2 = new Location(world, newPos.getX(), newPos.getY() + 2, newPos.getZ());

        if(!world.getBlockAt(checkLocation1).getType().isAir() || !world.getBlockAt(checkLocation2).getType().isAir()) {
            event.getPlayer().sendMessage("Obstructed Place");
            event.setCancelled(true);
        } else {
            event.getPlayer().sendMessage("Teleportation Success");
        }
     }
}
