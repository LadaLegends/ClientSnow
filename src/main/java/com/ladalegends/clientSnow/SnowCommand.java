package com.ladalegends.clientSnow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SnowCommand implements CommandExecutor, TabCompleter {

    private final ClientSnow plugin;

    public SnowCommand(ClientSnow plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getMessage("usage"));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload" -> handleReload(sender);
            case "enable", "disable" -> handleToggle(sender, subCommand.equals("disable"));
            default -> sender.sendMessage(plugin.getMessage("usage"));
        }

        return true;
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("clientsnow.admin")) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return;
        }
        plugin.reloadPlugin();
        sender.sendMessage(plugin.getMessage("reload-success"));
    }

    private void handleToggle(CommandSender sender, boolean disable) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("usage")); // Или сообщение "Только для игроков"
            return;
        }

        UUID uuid = player.getUniqueId();
        if (disable) {
            plugin.getDisabledPlayers().add(uuid);
            player.sendMessage(plugin.getMessage("snow-disabled"));
        } else {
            plugin.getDisabledPlayers().remove(uuid);
            player.sendMessage(plugin.getMessage("snow-enabled"));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) return Collections.emptyList();

        List<String> suggestions = new ArrayList<>();
        suggestions.add("enable");
        suggestions.add("disable");

        if (sender.hasPermission("clientsnow.admin")) {
            suggestions.add("reload");
        }

        return suggestions;
    }
}