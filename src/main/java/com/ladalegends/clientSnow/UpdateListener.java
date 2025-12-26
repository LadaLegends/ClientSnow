package com.ladalegends.clientSnow;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
    private final ClientSnow plugin;
    private final UpdateChecker checker;

    public UpdateListener(ClientSnow plugin, UpdateChecker checker) {
        this.plugin = plugin;
        this.checker = checker;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!checker.isUpdateAvailable()) return;

        var player = event.getPlayer();
        if (player.hasPermission("clientsnow.admin")) {
            var mm = MiniMessage.miniMessage();
            String ver = checker.getLatestVersion();

            String msg = plugin.getRawMessage("update-available").replace("%version%", ver);
            String link = plugin.getRawMessage("update-link");

            // Отправляем с префиксом
            player.sendMessage(plugin.getMessage("prefix").append(mm.deserialize(msg)));
            player.sendMessage(mm.deserialize(link));
        }
    }
}