package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.games.GameManager;
import com.raymond.testmcgame.games.WaitingRoom;
import com.raymond.testmcgame.users.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player running the command");
            return true;
        }

        Player player = (Player) sender;
        UserManager userManager = UserManager.getInstance();
        GameManager gameManager = GameManager.getInstance();


        //if true, player is in game
        if (userManager.get(player.getUniqueId()).getGameState()) {
            sender.sendMessage(ChatColor.RED + "You are already in game!");
            return true;
        }

        if (gameManager.getNumWaiting() >= GameManager.LOBBY_CAPACITY) {
            sender.sendMessage(ChatColor.RED + "Game is full!");
            return true;
        }

        player.teleport(new Location(player.getWorld(), -2494.5, 67, 998.5, -90, 0));

        userManager.get(player.getUniqueId()).changeIsWaiting();

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "You have joined the lobby!");

        return true;
    }
}
