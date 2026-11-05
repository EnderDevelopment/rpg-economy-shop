package com.enderdevelopment.plugin.commands;

import com.enderdevelopment.plugin.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public MoneyCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.").color(NamedTextColor.RED));
            return true;
        }
        double balance = economyManager.getBalance(player.getUniqueId());
        player.sendMessage(Component.text("Your balance: " + String.format("%.0f", balance) + " coins.")
            .color(NamedTextColor.GOLD));
        return true;
    }
}
