package com.raymond.testmcgame.regions;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class StoredRegion {

    private String name;
    private Vector pos1;
    private Vector pos2;

    public StoredRegion (String name, Location pos1, Location pos2) {
        this.name = name;
        this.pos1 = pos1.toVector();
        this.pos2 = pos2.toVector();
    }

    public String getName() {
        return name;
    }

    public Vector getPos1() {
        return pos1;
    }

    public Vector getPos2() {
        return pos2;
    }
}
