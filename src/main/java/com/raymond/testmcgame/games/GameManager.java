package com.raymond.testmcgame.games;

import com.raymond.testmcgame.regions.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private WaitingRoom waitingRoom;
    private static GameManager INSTANCE;

    private ScoreboardManager sbm;
    private static int numWaiting;
    public static final int LOBBY_CAPACITY = 8;
    public static final int START_THRESHOLD = 4;
    private static Scoreboard board;
    private static ArrayList<Team> teams = new ArrayList<>();

    private static HashMap<String, Team> playerTeams = new HashMap<>();
    private static ArrayList<Player> waitingPlayers = new ArrayList<>();
    private static ArrayList<Player> ingamePlayers = new ArrayList<>();

    public GameManager() {
        numWaiting = 0;

        sbm = Bukkit.getScoreboardManager();
        board = sbm.getNewScoreboard();

        Team green = board.registerNewTeam("Green");
        Team purple = board.registerNewTeam("Purple");

        for (Team team : board.getTeams()) {
            applyTeamSettings(team);
        }

        teams.add(green);
        teams.add(purple);

    }

    public static GameManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GameManager();
        return INSTANCE;
    }

    public void addPlayerToWaitingRoom(Player player) {
        waitingPlayers.add(player);
        numWaiting++;

        if (numWaiting >= START_THRESHOLD) {
            // start countdown
            CountdownRunnable countdown = new CountdownRunnable(10, START_THRESHOLD);
        }
    }

    public void removePlayerFromWaitingRoom(Player player) {
        waitingPlayers.remove(player);
        numWaiting--;
    }

    public static int getNumWaiting() {
        return numWaiting;
    }

    public Scoreboard getScoreboard() {
        return board;
    }

    public void startGame() {
        // Start the game
        // Add all players split 50/50 (or closest to) to the teams
        // Teleport players to their respective spawn points
        // Start the game timer
        // begin gameplay timer (2min or first team to 20 kills)
    }

    public void endGame() {
        // End the game
        // Display the winning team
        // Show highest kill players
        // remove players from their teams and send to spawn
        // Reset the game
    }

    public HashMap<String, Team> getPlayerTeams() {
        return playerTeams;
    }

    public void applyTeamSettings(Team team) {
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    public void addPlayerToTeam(String playerName, String teamName) {
        Team team = playerTeams.get(playerName);
        if (team != null) {
            team.removeEntry(playerName);
        }
        team = board.getTeam(teamName);

        if (team == null) { return; }

        team.addEntry(playerName);
        playerTeams.put(playerName, team);
    }

    public void removePlayerTeam(String playerName) {
        Team team = playerTeams.get(playerName);
        if (team != null) {
            team.removeEntry(playerName);
            playerTeams.remove(playerName);
        }
    }

    public static void messageWaitingRoom(String msg) {
        for (Player player : waitingPlayers) {
            player.sendMessage(msg);
        }
    }

}
