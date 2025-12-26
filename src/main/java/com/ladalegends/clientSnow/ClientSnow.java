package com.ladalegends.clientSnow;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class ClientSnow extends JavaPlugin {

    private static final int BSTATS_ID = 28542; // Замените на ваш ID с bstats.org
    private static final String MODRINTH_SLUG = "clientsnow"; // Ваш slug на Modrinth

    private SnowTask snowTask;
    private YamlConfiguration messages;
    private UpdateChecker updateChecker;
    private final Set<UUID> disabledPlayers = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadMessages();

        SnowCommand cmd = new SnowCommand(this);
        var command = getCommand("clientsnow");
        if (command != null) {
            command.setExecutor(cmd);
            command.setTabCompleter(cmd);
        }

        this.snowTask = new SnowTask(this);
        long rate = getConfig().getLong("optimization.update-rate", 2L);

        Bukkit.getAsyncScheduler().runAtFixedRate(this, (task) -> {
            if (snowTask != null) snowTask.tick();
        }, 1, rate * 50L, TimeUnit.MILLISECONDS);

        if (getConfig().getBoolean("system.metrics", true)) {
            new Metrics(this, BSTATS_ID);
        }

        if (getConfig().getBoolean("system.update-checker", true)) {
            this.updateChecker = new UpdateChecker(this, MODRINTH_SLUG);
            this.updateChecker.checkForUpdates();
            getServer().getPluginManager().registerEvents(new UpdateListener(this, updateChecker), this);
        }

        getLogger().info("ClientSnow Enabled.");
    }

    public void reloadPlugin() {
        reloadConfig();
        loadMessages();
        if (snowTask != null) snowTask.reload();
    }

    private void loadMessages() {
        File file = new File(getDataFolder(), "messages.yml");
        if (!file.exists()) saveResource("messages.yml", false);
        this.messages = YamlConfiguration.loadConfiguration(file);
    }

    public Component getMessage(String key) {
        String prefix = messages.getString("prefix", "");
        String text = messages.getString(key, "Missing: " + key);
        String combined = prefix + text;
        return combined.contains("&")
                ? LegacyComponentSerializer.legacyAmpersand().deserialize(combined)
                : MiniMessage.miniMessage().deserialize(combined);
    }

    public String getRawMessage(String key) { return messages.getString(key, key); }
    public Set<UUID> getDisabledPlayers() { return disabledPlayers; }
}