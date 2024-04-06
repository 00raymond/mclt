package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        // check if there exists an arg
        if (args.length == 1) {

            if (!(sender.isOp())) return true;

            String target = args[0];
            Player player = Bukkit.getPlayer(target);

            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player not online.");
                return true;
            }
            if (player.isOnline()) {
                player.teleport(new Location(player.getWorld(), -2494.5, 75, 998.5, -90, 0));
                sender.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.RED + " teleported to spawn.");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Player not online.");
                return true;
            }
        }



        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player running the command");
            return true;
        }

        Player player = (Player) sender;
        UserManager userManager = UserManager.getInstance();

        // if user is in game
        if (userManager.get(player.getUniqueId()).getGameState()) {
            sender.sendMessage(ChatColor.RED + "Moved to spawn. You have left the game!");
            userManager.get(player.getUniqueId()).changeGameState();

            //TODO: logic to quit user from game internally MOVE TO GAMEMANAGER

        } else {
            sender.sendMessage(ChatColor.RED + "Moved to spawn.");
        }

        player.teleport(new Location(player.getWorld(), -2494.5, 75, 998.5, -90, 0));

        return true;
    }
}
