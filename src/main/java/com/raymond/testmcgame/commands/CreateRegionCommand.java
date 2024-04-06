package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.Testmcgame;
import com.raymond.testmcgame.regions.Region;
import com.raymond.testmcgame.regions.RegionManager;
import com.raymond.testmcgame.util.ItemAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class CreateRegionCommand implements CommandExecutor {

    public static ItemStack WAND;
    private static ItemAction REGION_SELECTION;
    private static Location pos1;
    private static Location pos2;
//    private static final NamespacedKey POSITION_1 = new NamespacedKey(Testmcgame.getPlugin(Testmcgame.class), "position_1");
//    private static final NamespacedKey POSITION_2 = new NamespacedKey(Testmcgame.getPlugin(Testmcgame.class), "position_2");

    static {
        WAND = new ItemStack(Material.STICK);

        ItemMeta meta = WAND.getItemMeta();
        meta.displayName(Component.text("Region Selector", NamedTextColor.LIGHT_PURPLE));
        WAND.setItemMeta(meta);

        BiConsumer<Player, ClickType> action = ((player, clickType) -> {
            if (!clickType.isRightClick() && !clickType.isLeftClick()) return;

            RayTraceResult target = player.rayTraceBlocks(5);

            if (clickType.isRightClick()) {
               //set position 1 namespacedkey to block where player is looking
               pos1 = target.getHitBlock().getLocation();
               player.sendMessage(ChatColor.LIGHT_PURPLE + "Position 1 selected.");
           }
           if (clickType.isLeftClick()) {
               //set position 2 namespacedkey to block where player is looking
               pos2 = target.getHitBlock().getLocation();
               player.sendMessage(ChatColor.LIGHT_PURPLE + "Position 2 selected.");
           }
        });

        REGION_SELECTION = new ItemAction(action, true);
        REGION_SELECTION.attachToItemStack(WAND);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {

            player.getInventory().addItem(WAND);

            sender.sendMessage(ChatColor.RED + "Region selector granted");
            return true;
        }

        if (args.length == 2) {
            String regionName = args[1];

            RegionManager regionManager = RegionManager.getInstance();

            if (args[0].equals("add")) {
                if (pos1 == null || pos2 == null) {
                    sender.sendMessage(ChatColor.RED + "One or more positions are null");
                    return true;
                }
                if(!(regionManager.add(regionName, pos1, pos2))) {
                    sender.sendMessage(ChatColor.RED + "Region already exists.");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Region created.");
                return true;
            } else if (args[0].equals("remove")) {
                regionManager.remove(regionName);
                sender.sendMessage(ChatColor.RED + "Region removed.");
                return true;
            } else if (args[0].equals("get")) {
                Region region = regionManager.get(regionName);

                if (isNull(region)) {
                    sender.sendMessage(ChatColor.RED + "Region does not exist.");
                    return true;
                }

                sender.sendMessage(ChatColor.RED + region.getBox().toString());
                return true;
            } else if (args[0].equals("toggle")) {

                if (!(regionManager.toggle(regionName, player))) {
                    sender.sendMessage(ChatColor.RED + "Region does not exist.");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Region display toggled.");
            }
        }

        return true;
    }

    public boolean isNull(Region region) {
        return region == null;
    }

}
