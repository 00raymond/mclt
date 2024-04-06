package com.raymond.testmcgame.regions;

import com.raymond.testmcgame.Testmcgame;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;

import java.io.Serial;
import java.io.Serializable;

public class Region implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private BoundingBox box;
    private transient BukkitTask task;
    private Location pos1;
    private Location pos2;

    // create rectangular region, save to file.
    public Region(String name, Location pos1, Location pos2) {
        box = BoundingBox.of(pos1, pos2);
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public BoundingBox getBox() {
        return box;
    }

    public void changeShown(Player player) {
        if (task == null || (task != null && task.isCancelled())) {
            task = new RegionRunnable(this, player).runTaskTimer(Testmcgame.getPlugin(Testmcgame.class), 0, 5);
            return;
        }

        task.cancel();
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void onEnter(Player player) {
        player.sendMessage("You have entered " + name);
    }

    public void onExit(Player player) {
        player.sendMessage("You have exited " + name);
    }

}
