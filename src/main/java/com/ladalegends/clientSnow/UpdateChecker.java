package com.ladalegends.clientSnow;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final String projectId;
    private String latestVersion;

    public UpdateChecker(JavaPlugin plugin, String projectId) {
        this.plugin = plugin;
        this.projectId = projectId;
    }

    public void checkForUpdates() {
        Bukkit.getAsyncScheduler().runNow(plugin, (task) -> {
            try {
                URL url = new URL("https://api.modrinth.com/v2/project/" + projectId + "/version");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "LadaLegends/ClientSnow/" + plugin.getDescription().getVersion());

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                        JsonArray versions = JsonParser.parseReader(reader).getAsJsonArray();
                        if (versions.size() > 0) {
                            String remote = versions.get(0).getAsJsonObject().get("version_number").getAsString();
                            if (!plugin.getDescription().getVersion().equalsIgnoreCase(remote)) {
                                this.latestVersion = remote;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Update check failed: " + e.getMessage());
            }
        });
    }

    public String getLatestVersion() { return latestVersion; }
    public boolean isUpdateAvailable() { return latestVersion != null; }
}