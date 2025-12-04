# DynamicPortals

DynamicPortals is a robust Spigot/Paper plugin that allows players to create, manage, and customize their own teleportation waypoints using a physical multi-block structure. It features a fully interactive GUI, multi-language support, and admin management tools.

## Features

- **Custom Crafting Recipe:** Create the "Dimensional Core" using rare materials.
- **Multi-Block Structure:** Requires a specific 3x3 base to function.
- **Interactive GUIs:**
  - **Travel Menu:** Teleport to your other portals.
  - **Manager Menu:** Delete or edit your portals.
  - **Icon Selector:** Customize how your portal looks in the menu.
- **Renaming System:** Rename portals directly via chat integration.
- **Admin Tools:** Manage limits, delete player data, and change plugin language on the fly.
- **Multi-Language:** Supports English (EN), Spanish (ES), French (FR), and Portuguese (PT).

## Installation

1. Drop `DynamicPortals.jar` into your server's `plugins` folder.
2. Restart the server.
3. The `config.yml` and data files will generate automatically.

## Gameplay & Usage

### 1. Crafting the Core

To create a portal, you first need the **Dimensional Core**.

**Recipe:**
```text
[Tinted Glass] [Tinted Glass] [Tinted Glass]
[Obsidian]     [Nether Star]  [Obsidian]
[Obsidian]     [Obsidian]     [Obsidian]
```

> **Note:** The item must be the custom "Dimensional Core", a standard Beacon will not work.

### 2. Building the Portal

1. Build a 3x3 platform using **End Stone Bricks** on the ground.
2. Place the **Dimensional Core** in the exact center on top of the platform.
3. If successful, you will hear a beacon activation sound and receive a confirmation message.

### 3. Using the Portal

- **Right-Click** the Portal Core to open the **Travel Menu**.
- Select a destination to teleport instantly.
- Click the **Comparator** icon to manage your existing portals (Edit Icon/Name or Delete).

## Admin Commands & Permissions

All administrative commands require the permission node: `dynamicportals.admin` (or OP).

| Command | Description |
|---------|-------------|
| `/portaladmin language <ES\|EN\|FR\|PT>` | Changes the plugin's global language instantly. |
| `/portaladmin setlimit <player> <amount>` | Sets the max number of portals a player can own. |
| `/portaladmin deleteall <player>` | Forcefully deletes all portals owned by a specific player. |

## ⚙️ Technical Details

- **Data Persistence:** Portals are saved automatically on server shutdown.
- **Integrity Checks:** The plugin prevents portal creation if the base is invalid and destroys the portal if the base blocks are broken.
- **Async Chat:** Portal renaming utilizes Paper/Spigot async chat events for performance.

## 📦 Project Structure

- `com.crepe.portals` - Main plugin logic.
- `com.crepe.portals.commands` - Admin command handling.
- `com.crepe.portals.items` - Custom recipe registration.
- `com.crepe.portals.listeners` - Event handling (Block placement, breaking, GUI interaction).
- `com.crepe.portals.manager` - Logic for languages and portal data.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Developed by:** Crepe777  
**Package:** `me.crepe.myGameCore` / `com.crepe.portals`  
**GitHub:** [@crepepesote](https://github.com/crepepesote)

---

For issues, suggestions, or contributions, please open an issue on the [GitHub repository](https://github.com/crepepesote/DynamicPortals).
