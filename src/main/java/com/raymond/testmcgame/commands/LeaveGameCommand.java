package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.users.User;
import com.raymond.testmcgame.users.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LeaveGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player running the command");
            return true;
        }

        Player player = (Player) sender;
        UserManager userManager = UserManager.getInstance();

        //if true, player is not in game
        if (!(userManager.get(player.getUniqueId()).getGameState())) {
            sender.sendMessage(ChatColor.RED + "You are not in game!");
            return true;
        }

        player.teleport(new Location(player.getWorld(), -2494.5, 75, 998.5, -90, 0));

        leaveGame(userManager.get(player.getUniqueId()));

        userManager.get(player.getUniqueId()).changeGameState();
        sender.sendMessage(ChatColor.RED + "You have left the game!");

        return true;
    }


    public void leaveGame(User user) {
        // TODO: remove user from waiting room internally not just physically **MOVE TO GAMEMANAGER
    }

}
