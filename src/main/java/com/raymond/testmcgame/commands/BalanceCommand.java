package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.users.User;
import com.raymond.testmcgame.users.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console does not have a balance.");
            return true;
        }

        Player player = (Player) sender;
        User user = UserManager.getInstance().get(player);
        if (user == null) {
            player.sendMessage(ChatColor.RED + "Error retrieving data.");
            return true;
        }

        player.sendMessage("Balance: " + ChatColor.GREEN + user.getBalance());

        return true;
    }

}
