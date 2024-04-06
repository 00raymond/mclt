package com.raymond.testmcgame.listeners;

import com.raymond.testmcgame.users.UserManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn (PlayerRespawnEvent event) {
        UserManager userManager = UserManager.getInstance();

        if (userManager.get(event.getPlayer().getUniqueId()).getGameState()) {
            // respawn at game respawnpoint
            event.setRespawnLocation(new Location(event.getPlayer().getWorld(), -2494.5, 67, 998.5, -90, 0));

        } else {
            // respawn at spawn
            event.setRespawnLocation(new Location(event.getPlayer().getWorld(), -2494.5, 75, 998.5, -90, 0));

        }
    }
}
