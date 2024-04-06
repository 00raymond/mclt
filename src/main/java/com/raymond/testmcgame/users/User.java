package com.raymond.testmcgame.users;

import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private int balance;
    private boolean inGame;
    private boolean isWaiting;

    public User(@NotNull UUID uuid, int balance) {
        this.uuid = uuid;
        this.balance = balance;
        this.inGame = false;
        this.isWaiting = false;
    }

    public final @NotNull UUID getUuid() {
        return uuid;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void changeGameState() {
        inGame = !inGame;
    }

    public boolean getGameState() {
        return inGame;
    }

    public boolean getIsWaiting() {
        return isWaiting;
    }

    public void changeIsWaiting() {
        isWaiting = !isWaiting;
    }

}
