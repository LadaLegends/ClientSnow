![banner](https://cdn.modrinth.com/data/cached_images/716a57ee89f219972c4fb7240b6bc65810df8951.png)

![minecraft 1.21+](https://img.shields.io/badge/minecraft-1.21%2B-green) ![paper required](https://img.shields.io/badge/paper-required-red) ![license MIT](https://img.shields.io/badge/license-MIT-blue) 

<details>
<summary>Screenshot</summary>

![ccreenshot1](https://cdn.modrinth.com/data/cached_images/eff21912f43dffff1b3cf54d9bb1fa5b22703752_0.webp)

</details>

## About
**Enhance your server's atmosphere with lightweight, client-side snowfall.**

ClientSnow is a high-performance particle plugin designed for modern Minecraft servers. Unlike traditional weather systems, this plugin handles particle spawning asynchronously.

## Features
- **Asynchronous Engine**: Built on Paper's AsyncScheduler, offloading all particle logic to separate threads to maintain a perfect 20.0 TPS even on high-population servers.
- **Minimal RAM Footprint**: The plugin uses primitive-based math and avoids object allocation in the main loop, making it virtually invisible to the Garbage Collector.
- **Zero Lag Architecture**: All calculations and particle spawning happen in separate threads.
- **Player Control**: Users can toggle snow for themselves with /clientsnow enable|disable.
- **Smart Checks**: The plugin automatically detects if a player is under a roof or in a non-overworld dimension.
- **Fully Customizable**: Change particle types (Snowflake, Ash, etc.), density, and radius in a flash.
- **Smart hints**: when typing /clientsnow, only those subcommands that the user has access to will appear.
- **Volumetric Depth**: Instead of spawning a "flat layer" above the player, particles are distributed in a 3D cylinder, creating a true sense of being in a snowstorm.

## Installation
1. Download the .jar file and drop it into your plugins folder.
2. Restart your server to generate the configuration files.

## Commands & Permissions

| Command             | Description            | Permission        | Default |
|---------------------|------------------------|--------------------|---------|
| /clientsnow enable  | Turn on snow effects.  | clientsnow.toggle | true    |
| /clientsnow disable | Turn off snow effects. | clientsnow.toggle | true    |
| /clientsnow reload  | Reload configuration.  | clientsnow.admin  | OP      |

<details>
<summary>And also...</summary>

### Configuration
The plugin is fully localized. You can customize each message and use HEX colors in the format &#000000.

<details>
<summary>config.yml</summary>

```
# ==========================================
#         ClientSnow Configuration
# ==========================================

system:
  # Should anonymous statistics be sent to bstats.org
  metrics: true
  # Check for new versions at startup
  update-checker: true

snow-settings:
  # Global toggle for the system
  enabled: true
  # Particle type. Options: SNOWFLAKE, SNOW_SHOVEL, WHITE_ASH
  particle-type: "SNOWFLAKE"
  # Radius around the player to spawn particles
  radius: 20.0
  # Base height above player head where snow starts
  spawn-height: 8.0
  # Particles per update cycle per player.
  # Higher values = denser snow but more client GPU load.
  count-per-tick: 25

requirements:
  # If true, snow only spawns if sky is visible (simple check)
  outdoor-only: true

optimization:
  # Task execution rate in ticks (1 tick = 50ms).
  # Increase to 2 or 4 to reduce server load slightly, though animation might feel less fluid.
  update-rate: 2
```

</details>


<details>
<summary>messages.yml</summary>

```
# ==========================================
#         ClientSnow Localization
# ==========================================

# Prefix before all messages (supports HEX)
prefix: "&#47ceff☃ "

reload-success: "&fConfiguration reloaded successfully."

snow-disabled: "&fSnow visibility has been &cdisabled&f."
snow-enabled: "&fSnow visibility has been &#47ceffenabled&f."

no-permission: "&fYou do not have permission to execute this command."
usage: "&fUsage: &7/clientsnow <enable|disable|reload>"

update-available: "&fNew update available: &#47ceffv%version%"
update-link: "&fDownload: &#47ceffhttps://modrinth.com/plugin/clientsnow"
```

</details>



### Statistics
We use bStats to track anonymous data such as the number of servers and players using the plugin. This helps us improve the project! You can disable this in the config if needed.

</details>
