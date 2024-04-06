package com.raymond.testmcgame.regions;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RegionManager {
    private static final Logger LOGGER = Logger.getLogger(RegionManager.class.getSimpleName());
    private static RegionManager INSTANCE;
    private static Map<String, Region> regionMap;
    private static Map<String, StoredRegion> storedRegionMap;

    private RegionManager() {
        regionMap = new HashMap<String, Region>();
        storedRegionMap = new HashMap<String, StoredRegion>();
    }

    public boolean add(String regionName, Location pos1, Location pos2) {
        if (regionMap.containsKey(regionName))
            return false;
        regionMap.put(regionName, new Region(regionName, pos1, pos2));
        return true;
    }

    public void remove(String regionName) {
        regionMap.remove(regionName);
    }

    @Nullable
    public Region get(String regionName) {
        if (!(regionMap.containsKey(regionName)))
            return null;
        return regionMap.get(regionName);
    }

    @Nullable
    public ArrayList<Region> getRegionByLocation(Location location) {
        ArrayList<Region> regions = new ArrayList<>();
        for (Region region : regionMap.values()) {
            if (region.getBox().contains(location.toVector())) {
                regions.add(region);
            }
        }
        return regions;
    }

    public static RegionManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RegionManager();

        return INSTANCE;
    }

    public boolean toggle(String regionName, Player player) {
        if (!(regionMap.containsKey(regionName)))
            return false;
        regionMap.get(regionName).changeShown(player);
        player.sendMessage("toggle method returning true, region in regionMap found");
        return true;
    }

    public static Map<String, Region> getRegionMap() {
        return regionMap;
    }

    public static void saveRegionMap() {
        try {
            File directory = new File("regions");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "regionData.json");
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileWriter w = new FileWriter(file)) {

                Gson gson = new Gson();
                ArrayList<StoredRegion> regionArrayList = new ArrayList<>();

                for (Map.Entry<String, Region> entry : regionMap.entrySet()) {
                    StoredRegion storedRegion = new StoredRegion(entry.getKey(), entry.getValue().getPos1(), entry.getValue().getPos2());
                    regionArrayList.add(storedRegion);
                }

                gson.toJson(regionArrayList, w);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void loadRegionData() throws FileNotFoundException {

        File directory = new File("regions");
        if (!directory.exists()) {
            LOGGER.info("region directory failed to load");
            return;
        }

        File file = new File(directory, "regionData.json");
        if (!file.exists()) {
            LOGGER.info("regionData.json failed to load");
            return;
        }

        try (FileReader w = new FileReader(file)) {
            Gson gson = new Gson();

            JsonArray region = gson.fromJson(w, JsonArray.class);
            World world = Bukkit.getWorlds().get(0);

            for (JsonElement e : region) {
                StoredRegion storedRegion = gson.fromJson(e, StoredRegion.class);
                regionMap.put(storedRegion.getName(), new Region(storedRegion.getName(), storedRegion.getPos1().toLocation(world),
                        storedRegion.getPos2().toLocation(world)));
            }


            LOGGER.info("loaded regions/regionData.json");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
