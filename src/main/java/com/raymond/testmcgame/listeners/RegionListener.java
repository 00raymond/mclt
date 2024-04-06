package com.raymond.testmcgame.listeners;

import com.raymond.testmcgame.regions.Region;
import com.raymond.testmcgame.regions.RegionManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class RegionListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onRegionEnter(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location to = event.getTo();
        Location from = event.getFrom();

        RegionManager regionManager = RegionManager.getInstance();
        ArrayList<Region> regionsTo = regionManager.getRegionByLocation(to);
        ArrayList<Region> regionsFrom = regionManager.getRegionByLocation(from);

        if (regionsFrom == regionsTo){
            return;
        }

        ArrayList<Region> commonRegions = new ArrayList<>();

        // get in common regions
        for (Region region : regionsFrom) {
            if (regionsTo.contains(region)) {
                commonRegions.add(region);
            }
        }

        // remove old regionsFrom effects (not in common)
        for (Region region : regionsFrom) {
            if (!regionsTo.contains(region)) {
                region.onExit(player);
            }
        }

        // apply new regionsTo effects
        for (Region region : regionsTo) {
            if (!commonRegions.contains(region)) {
                region.onEnter(player);
            }
        }

    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onRegionExit() {

    }


}
