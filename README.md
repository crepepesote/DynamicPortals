# Portal Plugin Documentation

Designed as a technical demonstration of **Vector Math**, **GUI Management**, and **NMS-free** implementations using modern Paper APIs.

## 📋 Features

- **Physical Structures:** Build portals in the world using 3x3 multiblock structures.
- **Vector Math:** Smooth teleportation that preserves player momentum and relative orientation (Entry Vector -> Exit Vector transformation).
- **Interactive GUIs:** Manage destinations, rename portals, and change icons using a chest interface.
- **Persistence:** JSON/Flat-file storage for portal data.
- **Custom Items:** Unique "Dimensional Core" item with custom recipes.

## 🛠️ Installation

1. Download the latest `.jar` from the [Releases] tab.
2. Drop the file into your server's `/plugins` folder.
3. Restart the server.
4. **Requirements:** Java 21 and PaperMC 1.21.8+.

## 🎮 Usage Guide

### 1. Crafting the Core

To start, you need to craft the **Dimensional Core**.

- **Ingredients:** 3 Tinted Glass, 5 Obsidian, 1 Nether Star.
- **Recipe:**
```text
  [Glass]     [Glass]       [Glass]
  [Obsidian]  [Nether Star] [Obsidian]
  [Obsidian]  [Obsidian]    [Obsidian]
```

### 2. Building a Portal

1. Build a 3x3 flat base of End Stone Bricks on the ground.
2. Place the Dimensional Core (Beacon) in the exact center of the base.
3. The portal structure will activate automatically.

### 3. Linking & Management

- Use `/portal` to open the main menu.
- **Manager:** View all your active portals.
- **Teleport:** Click on a destination to travel.
- **Edit:** Rename your portals or change their display icons.