package com.enderdevelopment.plugin.listener;

import com.enderdevelopment.plugin.economy.IncomeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class IncomeListener implements Listener {

    private final IncomeManager incomeManager;

    public IncomeListener(IncomeManager incomeManager) {
        this.incomeManager = incomeManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        incomeManager.onJoin(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        incomeManager.onQuit(event.getPlayer());
    }
}
