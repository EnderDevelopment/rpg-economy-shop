package com.enderdevelopment.plugin.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    private final ShopManager shopManager;

    public ShopListener(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (event.getView().title() == null || !event.getView().title().toString().contains("Sun Shop")) {
            return;
        }
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) {
            return;
        }
        ItemStack item = event.getCurrentItem();
        if (item.getType() == org.bukkit.Material.PAPER) {
            return;
        }
        shopManager.buyItem(player, item.getType());
    }
}
