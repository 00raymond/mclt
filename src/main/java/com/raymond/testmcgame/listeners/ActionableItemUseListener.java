package com.raymond.testmcgame.listeners;

import com.raymond.testmcgame.util.ItemAction;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ActionableItemUseListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onItemUse(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY || event.getAction() == Action.PHYSICAL || event.getItem() == null)
            return;

        ItemAction itemAction = ItemAction.getFromItemStack(event.getItem());
        if (itemAction == null)
            return;

        boolean right = (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR);
        ClickType clickType = event.getPlayer().isSneaking() ?
                (right ?  ClickType.SHIFT_RIGHT : ClickType.SHIFT_LEFT) :
                (right ? ClickType.RIGHT : ClickType.LEFT);

        if (event.getItem().equals(event.getPlayer().getInventory().getItemInMainHand())) {
            if (event.getHand() != EquipmentSlot.HAND)
                return;

            event.setCancelled(true);
            itemAction.use(event.getPlayer(), clickType);
        }

        else if (event.getItem().equals(event.getPlayer().getInventory().getItemInOffHand())) {
            if (event.getHand() != EquipmentSlot.OFF_HAND)
                return;

            event.setCancelled(true);
            itemAction.use(event.getPlayer(), clickType);
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame))
            return;

        ItemAction itemAction = ItemAction.getFromItemStack(event.getPlayer().getInventory().getItem(event.getHand()));
        if (itemAction != null)
            event.setCancelled(true);
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
    public void onItemDrop(PlayerDropItemEvent event) {
        ItemAction itemAction = ItemAction.getFromItemStack(event.getItemDrop().getItemStack());
        if (itemAction == null)
            return;

        event.setCancelled(true);
        itemAction.use(event.getPlayer(), ClickType.DROP);
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null)
            return;

        ItemAction itemAction = ItemAction.getFromItemStack(event.getCurrentItem());
        if (itemAction == null)
            return;

        if (itemAction.isMovable()) {
            return;
        }

        event.setCancelled(true);
        itemAction.use((Player) event.getWhoClicked(), event.getClick());
    }
}