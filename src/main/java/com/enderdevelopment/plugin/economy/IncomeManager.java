package com.enderdevelopment.plugin.economy;

import com.enderdevelopment.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Rewards players money based on their total play time.
 * Every configured interval (in minutes) of online time, the player
 * receives a configured reward, capped at a configured maximum per day.
 */
public class IncomeManager {

    private final Main plugin;
    private final EconomyManager economyManager;

    // Map of player -> total accumulated online time (in minutes) since last reward.
    private final Map<UUID, Integer> onlineMinutes = new HashMap<>();
    private final Map<UUID, Double> dailyEarned = new HashMap<>();
    private BukkitTask task;

    public IncomeManager(Main plugin, EconomyManager economyManager) {
        this.plugin = plugin;
        this.economyManager = economyManager;
    }

    public void start() {
        int interval = plugin.getConfig().getInt("income.interval-minutes", 5);
        if (interval <= 0) {
            interval = 5;
        }
        long periodTicks = interval * 60L * 20L;
        task = Bukkit.getScheduler().runTaskTimer(plugin, this::tick, periodTicks, periodTicks);
        plugin.getLogger().info("Income system started (interval: " + interval + " min).");
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    /**
     * Called when a player joins: start accumulating their online time.
     */
    public void onJoin(Player player) {
        onlineMinutes.putIfAbsent(player.getUniqueId(), 0);
    }

    /**
     * Called when a player leaves: reset their tracker.
     */
    public void onQuit(Player player) {
        onlineMinutes.remove(player.getUniqueId());
    }

    private void tick() {
        double reward = plugin.getConfig().getDouble("income.reward", 50.0);
        int interval = plugin.getConfig().getInt("income.interval-minutes", 5);
        double dailyCap = plugin.getConfig().getDouble("income.daily-cap", 1000.0);

        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            int minutes = onlineMinutes.getOrDefault(uuid, 0) + interval;
            onlineMinutes.put(uuid, minutes);

            double earnedSoFar = dailyEarned.getOrDefault(uuid, 0.0);
            if (earnedSoFar >= dailyCap) {
                continue;
            }

            double toGive = Math.min(reward, dailyCap - earnedSoFar);
            economyManager.addBalance(uuid, toGive);
            dailyEarned.put(uuid, earnedSoFar + toGive);

            player.sendMessage(org.bukkit.ChatColor.GOLD
                    + "You earned " + org.bukkit.ChatColor.YELLOW + String.format("%.0f", toGive)
                    + org.bukkit.ChatColor.GOLD + " coins from playing time!");
        }
    }

    public BukkitTask getTask() {
        return task;
    }
}
