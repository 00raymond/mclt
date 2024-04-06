package com.raymond.testmcgame.commands;

import com.raymond.testmcgame.util.ItemAction;
import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class WeaponCommand implements CommandExecutor {

    private static ItemStack GUN;
    private static ItemAction GUN_ACTION;
    private static final double TRACE_DISTANCE = 50;
    private static final int GUN_COOLDOWN_TICKS = 10;
    private static final Sound GUN_SHOOT_SOUND = Sound.BLOCK_CONDUIT_DEACTIVATE;

    static {
        GUN = new ItemStack(Material.STONE_HOE);

        ItemMeta meta = GUN.getItemMeta();
        meta.displayName(Component.text("Laser Gun", NamedTextColor.LIGHT_PURPLE));
        GUN.setItemMeta(meta);

        BiConsumer<Player, ClickType> action = (player, clickType) -> {

            if (clickType.isRightClick()) {

                if (player.getCooldown(GUN.getType()) > 0) return;
                player.setCooldown(GUN.getType(), GUN_COOLDOWN_TICKS);

                double distance;

                RayTraceResult tracedBlocks = player.rayTraceBlocks(TRACE_DISTANCE);
                RayTraceResult tracedEntity = player.getWorld().rayTraceEntities(player.getEyeLocation(),
                        player.getLocation().getDirection(), TRACE_DISTANCE, entity -> {
                    if (!(entity instanceof LivingEntity livingEntity)) { return false; }
                    return !livingEntity.getUniqueId().equals(player.getUniqueId());
                });

                // logic to draw particle line even if no entity exists in ray trace
                if (tracedEntity == null || tracedEntity.getHitEntity() == null) {

                    if (tracedBlocks == null || tracedBlocks.getHitBlock() == null) {
                        distance = TRACE_DISTANCE;
                    } else {
                        distance = tracedBlocks.getHitPosition().subtract(player.getEyeLocation().toVector()).length();
                    }

                    drawParticles(distance, player);
                    player.getWorld().playSound(player.getEyeLocation(), GUN_SHOOT_SOUND, 1f, 4f);
                    return;
                }

                LivingEntity livingEntity = (LivingEntity) tracedEntity.getHitEntity();

                double entityDistance = tracedEntity.getHitPosition().subtract(player.getEyeLocation().toVector()).length();

                Double blocksDistance = null;
                // if blocks exist in ray trace
                if (tracedBlocks != null && tracedBlocks.getHitBlock() != null) {
                    blocksDistance = tracedBlocks.getHitPosition().subtract(player.getEyeLocation().toVector()).length();
                }

                if (blocksDistance == null || entityDistance < blocksDistance) {
                    // check where on the entity that the rayTrace hit, check relative to the living entity location where the
                    // hit position is.
                    double shotLocation = tracedEntity.getHitPosition().getY() - (livingEntity.getLocation().getY() + 1.62);
                    if (shotLocation > 0) {
                        // headshot
                        livingEntity.damage(10D, player);
                        player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f);
                        player.getWorld().playSound(player.getEyeLocation(), GUN_SHOOT_SOUND, 1f, 4f);
                    } else {
                        livingEntity.damage(5D, player);
                        player.getWorld().playSound(player.getEyeLocation(), GUN_SHOOT_SOUND, 1f, 4f);
                    }
                }

                distance = blocksDistance == null ? entityDistance : entityDistance < blocksDistance ? entityDistance : blocksDistance;

                drawParticles(distance, player);

            }

        };

        GUN_ACTION = new ItemAction(action, false);

        GUN_ACTION.attachToItemStack(GUN);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.getInventory().addItem(GUN);

            sender.sendMessage(ChatColor.RED + "Weapon granted");
            return true;
        } else {
            sender.sendMessage("Command can only be run by a player.");
            return true;
        }
    }

    public static void drawParticles(double distance, Player player) {
        for (float f = 0.5f; f <= distance; f += 0.2f) {
            Location location = player.getEyeLocation().clone().add(player.getLocation().getDirection().clone().multiply(f));
            player.getWorld().spawnParticle(Particle.REDSTONE, location, 0, new Particle.DustOptions(Color.PURPLE, 0.2f));
        }
    }
}
