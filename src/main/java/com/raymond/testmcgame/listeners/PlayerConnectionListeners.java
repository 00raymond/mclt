package com.raymond.testmcgame.listeners;

import com.raymond.testmcgame.games.GameManager;
import com.raymond.testmcgame.users.User;
import com.raymond.testmcgame.users.UserManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListeners implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        UserManager.getInstance().add(new User(event.getPlayer().getUniqueId(), 0));
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), -2494.5, 75, 998.5, -90, 0));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UserManager userManager = UserManager.getInstance();
        User user = userManager.get(player.getUniqueId());
        GameManager gameManager = GameManager.getInstance();

        if (user == null) return;

        if (user.getGameState() && (gameManager.getPlayerTeams().containsKey(player.getName()))) {
            user.changeGameState();
            gameManager.removePlayerTeam(player.getName());
        }

        // todo: save stats (add previous)

        userManager.remove(event.getPlayer().getUniqueId());
    }
}
