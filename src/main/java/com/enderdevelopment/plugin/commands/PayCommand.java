package com.enderdevelopment.plugin.commands;

import com.enderdevelopment.plugin.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public PayCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.").color(NamedTextColor.RED));
            return true;
        }
        if (args.length < 2) {
            player.sendMessage(Component.text("Usage: /pay <player> <amount>").color(NamedTextColor.RED));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(Component.text("Player not found.").color(NamedTextColor.RED));
            return true;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text("Invalid amount.").color(NamedTextColor.RED));
            return true;
        }
        if (amount <= 0) {
            player.sendMessage(Component.text("Amount must be positive.").color(NamedTextColor.RED));
            return true;
        }
        if (!economyManager.hasAtLeast(player.getUniqueId(), amount)) {
            player.sendMessage(Component.text("You do not have enough money.").color(NamedTextColor.RED));
            return true;
        }
        economyManager.subtractBalance(player.getUniqueId(), amount);
        economyManager.addBalance(target.getUniqueId(), amount);
        player.sendMessage(Component.text("You paid " + target.getName() + " " + String.format("%.0f", amount) + " coins.")
            .color(NamedTextColor.GREEN));
        target.sendMessage(Component.text(player.getName() + " paid you " + String.format("%.0f", amount) + " coins.")
            .color(NamedTextColor.GREEN));
        return true;
    }
}
