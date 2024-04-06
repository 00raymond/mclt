package com.raymond.testmcgame.games;

public class WaitingRoom {
    private static final int MAX_PLAYERS = 16;
    private int countdown;
    private int currPlayers;

    public WaitingRoom() {
        this.currPlayers = 0;
        countdown = 30;
    }

    public void playerJoin() {
        currPlayers++;

        // check if we have enough players to start the game, then call startGame in game manager

    }

    public void playerLeft() {
        currPlayers--;
    }

}
