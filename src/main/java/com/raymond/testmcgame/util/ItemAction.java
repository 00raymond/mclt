package com.raymond.testmcgame.util;

import com.raymond.testmcgame.Testmcgame;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ItemAction {

    private static int incrementalIndex = 0;

    private static final NamespacedKey KEY = new NamespacedKey(Testmcgame.getPlugin(Testmcgame.class), "ItemAction");
    private static final Map<Integer, ItemAction> actions = new HashMap<>();

    private final int index;
    private final BiConsumer<Player, ClickType> action;
    private boolean movable;

    public ItemAction(BiConsumer<Player, ClickType> action, boolean movable)  {
        this.index = incrementalIndex++;
        this.action = action;
        this.movable = movable;

        actions.put(index, this);
    }

    public boolean isMovable() {
        return movable;
    }

    public void use(Player player, ClickType clickType) {
        action.accept(player, clickType);
    }

    public void attachToItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return;

        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, index);
        itemStack.setItemMeta(meta);
    }

    public static @Nullable ItemAction getFromItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
            return null;

        if (!meta.getPersistentDataContainer().has(KEY, PersistentDataType.INTEGER))
            return null;

        return actions.get(meta.getPersistentDataContainer().get(KEY, PersistentDataType.INTEGER));
    }

    public static @NotNull ItemStack build(@NotNull ItemStack itemStack, @NotNull ItemAction action) {
        action.attachToItemStack(itemStack);
        return itemStack;
    }
}
