package com.raymond.testmcgame.games;

import com.raymond.testmcgame.Testmcgame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CountdownRunnable {
    private int countdown;
    private int taskId;
    private final int MINIMUM_PLAYERS;

    public CountdownRunnable(int countdown, int minimumPlayers) {
        this.countdown = countdown;
        this.MINIMUM_PLAYERS = minimumPlayers;
    }

    public void start() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Testmcgame.getPlugin(Testmcgame.class), () -> {

            if (GameManager.getNumWaiting() < MINIMUM_PLAYERS) {
                // send message to players in waiting room
                GameManager.messageWaitingRoom(ChatColor.RED + "Not enough players! Countdown stopped.");
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }

            if (countdown == 0) {
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }

            countdown--;
        }, 0, 20);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
