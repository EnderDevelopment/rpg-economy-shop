package com.enderdevelopment.plugin.shop;

import com.enderdevelopment.plugin.Main;
import com.enderdevelopment.plugin.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {

    private final Main plugin;
    private final EconomyManager economyManager;

    public ShopManager(Main plugin, EconomyManager economyManager) {
        this.plugin = plugin;
        this.economyManager = economyManager;
    }

    public void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("Sun Shop").color(NamedTextColor.GOLD));

        setShopItem(inv, 10, new ItemStack(Material.APPLE, 1), "Apple", 5);
        setShopItem(inv, 11, new ItemStack(Material.BREAD, 1), "Bread", 10);
        setShopItem(inv, 12, new ItemStack(Material.DIAMOND, 1), "Diamond", 500);
        setShopItem(inv, 13, new ItemStack(Material.IRON_INGOT, 1), "Iron Ingot", 100);
        setShopItem(inv, 14, new ItemStack(Material.GOLD_INGOT, 1), "Gold Ingot", 150);
        setShopItem(inv, 15, new ItemStack(Material.EMERALD, 1), "Emerald", 200);
        setShopItem(inv, 16, new ItemStack(Material.NETHERITE_INGOT, 1), "Netherite Ingot", 1500);

        player.openInventory(inv);
    }

    private void setShopItem(Inventory inv, int slot, ItemStack item, String name, double price) {
        ItemStack display = item.clone();
        ItemMeta meta = display.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name).color(NamedTextColor.GOLD));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Price: " + String.format("%.0f", price) + " coins").color(NamedTextColor.YELLOW));
            lore.add(Component.text("Click to buy!").color(NamedTextColor.GRAY));
            meta.lore(lore);
            display.setItemMeta(meta);
        }
        inv.setItem(slot, display);
    }

    public double priceFor(Material material) {
        switch (material) {
            case APPLE: return 5;
            case BREAD: return 10;
            case DIAMOND: return 500;
            case IRON_INGOT: return 100;
            case GOLD_INGOT: return 150;
            case EMERALD: return 200;
            case NETHERITE_INGOT: return 1500;
            default: return -1;
        }
    }

    public boolean buyItem(Player player, Material material) {
        double price = priceFor(material);
        if (price < 0) {
            return false;
        }
        if (!economyManager.hasAtLeast(player.getUniqueId(), price)) {
            player.sendMessage(Component.text("You do not have enough money!").color(NamedTextColor.RED));
            return false;
        }
        economyManager.subtractBalance(player.getUniqueId(), price);
        player.getInventory().addItem(new ItemStack(material, 1));
        player.sendMessage(Component.text("You bought " + material.toString().toLowerCase() + " for " +
            String.format("%.0f", price) + " coins.").color(NamedTextColor.GREEN));
        return true;
    }
}
