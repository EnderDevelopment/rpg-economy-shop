package com.enderdevelopment.plugin.commands;

import com.enderdevelopment.plugin.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetMoneyCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public SetMoneyCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("enderrp.admin")) {
            sender.sendMessage(Component.text("You do not have permission.").color(NamedTextColor.RED));
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /setmoney <player> <amount>").color(NamedTextColor.RED));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("Player not found.").color(NamedTextColor.RED));
            return true;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Invalid amount.").color(NamedTextColor.RED));
            return true;
        }
        if (amount < 0) {
            sender.sendMessage(Component.text("Amount must be non-negative.").color(NamedTextColor.RED));
            return true;
        }
        economyManager.setBalance(target.getUniqueId(), amount);
        sender.sendMessage(Component.text(target.getName() + "'s balance set to " + String.format("%.0f", amount) + " coins.")
            .color(NamedTextColor.GREEN));
        return true;
    }
}
