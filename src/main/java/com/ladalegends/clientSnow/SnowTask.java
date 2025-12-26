package com.ladalegends.clientSnow;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.concurrent.ThreadLocalRandom;

public class SnowTask {
    private final ClientSnow plugin;
    private boolean enabled, outdoorOnly;
    private double radius, heightBase;
    private int count;
    private Particle particle;
    private String permission;

    public SnowTask(ClientSnow plugin) { this.plugin = plugin; reload(); }

    public void reload() {
        var c = plugin.getConfig();
        this.enabled = c.getBoolean("snow-settings.enabled", true);
        this.radius = c.getDouble("snow-settings.radius", 20.0);
        this.heightBase = c.getDouble("snow-settings.spawn-height", 8.0);
        this.count = c.getInt("snow-settings.count-per-tick", 25);
        this.outdoorOnly = c.getBoolean("requirements.outdoor-only", true);
        try {
            this.particle = Particle.valueOf(c.getString("snow-settings.particle-type", "SNOWFLAKE").toUpperCase());
        } catch (Exception e) { this.particle = Particle.SNOWFLAKE; }
    }

    public void tick() {
        if (!enabled) return;
        final ThreadLocalRandom rnd = ThreadLocalRandom.current();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.getDisabledPlayers().contains(player.getUniqueId())) continue;
            if (permission != null && !player.hasPermission(permission)) continue;
            if (player.getWorld().getEnvironment() != World.Environment.NORMAL) continue;

            Location loc = player.getLocation();
            if (outdoorOnly) {
                if (!loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) continue;
                if (loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()) > loc.getY() + 2) continue;
            }

            for (int i = 0; i < count; i++) {
                double x = loc.getX() + (rnd.nextDouble() * radius * 2) - radius;
                double z = loc.getZ() + (rnd.nextDouble() * radius * 2) - radius;
                double y = loc.getY() + (rnd.nextDouble() * (heightBase + 10));
                player.spawnParticle(particle, x, y, z, 1, 0, 0, 0, 0);
            }
        }
    }
}