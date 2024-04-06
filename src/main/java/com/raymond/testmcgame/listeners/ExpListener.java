package com.raymond.testmcgame.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExpListener implements Listener {
    @EventHandler
    public void onExpGain(PlayerPickupExperienceEvent event) {
        event.setCancelled(true);
    }
}
