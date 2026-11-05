package com.enderdevelopment.plugin.listener;

import com.enderdevelopment.plugin.rp.RpManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final RpManager rpManager;

    public JoinListener(RpManager rpManager) {
        this.rpManager = rpManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (rpManager.hasRpName(event.getPlayer().getUniqueId())) {
            event.getPlayer().setDisplayName(
                rpManager.displayName(event.getPlayer().getUniqueId(), event.getPlayer().getName())
            );
            event.getPlayer().setPlayerListName(
                rpManager.displayName(event.getPlayer().getUniqueId(), event.getPlayer().getName())
            );
        }
    }
}
