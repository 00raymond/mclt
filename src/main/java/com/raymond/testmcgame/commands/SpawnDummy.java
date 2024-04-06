package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.Testmcgame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class SpawnDummy implements CommandExecutor {

    private static final NamespacedKey TARGET_DUMMY_KEY = new NamespacedKey(Testmcgame.getPlugin(Testmcgame.class), "target_dummy");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Must be a player running the command");
            return true;
        }

        player.getWorld().spawn(player.getLocation(), Zombie.class, zombie -> {
            zombie.setAI(false);
            zombie.setCollidable(false);
            zombie.customName(Component.text("Target Practice", NamedTextColor.GREEN));
            zombie.setCustomNameVisible(true);
            zombie.getPersistentDataContainer().set(TARGET_DUMMY_KEY, PersistentDataType.BOOLEAN, true);
            zombie.setSilent(true);
        });

        return true;
    }
}
