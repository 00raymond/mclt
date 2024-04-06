package com.raymond.testmcgame.listeners;

import com.raymond.testmcgame.Testmcgame;
import com.raymond.testmcgame.users.UserManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TargetDummyListener implements Listener {

    private static final NamespacedKey TARGET_DUMMY_KEY = new NamespacedKey(Testmcgame.getPlugin(Testmcgame.class), "target_dummy");

    @EventHandler
    public void onDummyDamage(EntityDamageByEntityEvent e) {

        boolean isTargetDummy = e.getEntity().getPersistentDataContainer().has(TARGET_DUMMY_KEY);

        if (isTargetDummy)
            e.setDamage(0);
    }
}
