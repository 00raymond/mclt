package com.raymond.testmcgame.regions;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

public class RegionRunnable extends BukkitRunnable {

    private Region region;
    private Player player;
    public RegionRunnable(Region region, Player player) {
        this.region = region;
        this.player = player;
    }

    @Override
    public void run() {

        BoundingBox box = region.getBox();

        for (double x = box.getMin().getX(); x <= box.getMax().getX(); x+=0.2) {
            for (double y = box.getMin().getY(); y <= box.getMax().getY(); y+=0.2) {
                for (double z = box.getMin().getZ(); z <= box.getMax().getZ(); z+=0.2) {

                    int count = 0;
                    if (x == box.getMin().getX() || x == box.getMax().getX())
                        count++;
                    if (y == box.getMin().getY() || y == box.getMax().getY())
                        count++;
                    if (z == box.getMin().getZ() || z == box.getMax().getZ())
                        count++;

                    if (count >= 2) {
                        player.spawnParticle(Particle.REDSTONE, x, y, z, 0, new Particle.DustOptions(Color.RED, 1));
                    }
                }
            }
        }
    }
}
