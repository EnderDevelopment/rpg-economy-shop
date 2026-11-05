package com.enderdevelopment.plugin.commands;

import com.enderdevelopment.plugin.rp.RpManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RpCommand implements CommandExecutor {

    private final RpManager rpManager;

    public RpCommand(RpManager rpManager) {
        this.rpManager = rpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.").color(NamedTextColor.RED));
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(Component.text("Usage: /rp <name> | /rp clear").color(NamedTextColor.RED));
            return true;
        }
        String tag = String.join(" ", args);
        rpManager.setRpName(player.getUniqueId(), tag);
        if (tag.equalsIgnoreCase("clear") || tag.equalsIgnoreCase("none")) {
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            player.sendMessage(Component.text("Your roleplay name has been cleared.").color(NamedTextColor.GREEN));
        } else {
            player.setDisplayName(tag);
            player.setPlayerListName(tag);
            player.sendMessage(Component.text("Your roleplay name is now: " + tag).color(NamedTextColor.GOLD));
        }
        return true;
    }
}
