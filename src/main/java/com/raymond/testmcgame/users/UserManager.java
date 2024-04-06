package com.raymond.testmcgame.users;

import com.raymond.testmcgame.Testmcgame;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private static UserManager INSTANCE;
    private final Map<UUID, User> userMap = new HashMap<>();

    private UserManager() { }

    public void add(User user) {
        if (userMap.containsKey(user.getUuid()))
            throw new IllegalArgumentException("User already exists in user manager.");
        userMap.put(user.getUuid(), user);
    }

    public void remove(UUID uuid) {
        if (!userMap.containsKey(uuid))
            Testmcgame.LOGGER.warning("Tried to remove user not in user manager.");
        userMap.remove(uuid);
    }

    public @Nullable User get(UUID uuid) {
        return userMap.get(uuid);
    }

    public @Nullable User get(Player player) {
        return this.get(player.getUniqueId());
    }

    public static UserManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserManager();

        return INSTANCE;
    }

}
