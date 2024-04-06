package com.raymond.testmcgame;

import com.raymond.testmcgame.commands.*;
import com.raymond.testmcgame.listeners.*;
import com.raymond.testmcgame.regions.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

public final class Testmcgame extends JavaPlugin {

    public static Logger LOGGER = Logger.getLogger("MCGAME");

    @Override
    public void onEnable() {

        try {
            RegionManager.getInstance().loadRegionData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        // Plugin startup login
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ActionableItemUseListener(), this);
        Bukkit.getPluginManager().registerEvents(new TargetDummyListener(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new HungerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExpListener(), this);
        Bukkit.getPluginManager().registerEvents(new RegionListener(), this);

        this.getCommand("balance").setExecutor(new BalanceCommand());
        this.getCommand("getweapon").setExecutor(new WeaponCommand());
        this.getCommand("joingame").setExecutor(new JoinGameCommand());
        this.getCommand("spawndummy").setExecutor(new SpawnDummy());
        this.getCommand("leavegame").setExecutor(new LeaveGameCommand());
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("createregion").setExecutor(new CreateRegionCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        RegionManager.saveRegionMap();


    }
}
