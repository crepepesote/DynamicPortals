package com.crepe.portals.manager;

import com.crepe.portals.DynamicPortals;
import com.crepe.portals.model.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PortalManager {

    private final DynamicPortals plugin; // Referencia al plugin
    private final Map<UUID, Portal> portalsById = new HashMap<>();
    private final Map<UUID, Portal> editingSession = new WeakHashMap<>();
    private final Map<UUID, Integer> playerLimits = new HashMap<>();
    private final int DEFAULT_LIMIT = 3;

    private final LanguageManager languageManager;


    private File portalsFile;
    private FileConfiguration portalsConfig;

    public PortalManager(DynamicPortals plugin) {
        this.plugin = plugin;
        this.languageManager = new LanguageManager();
    }

    public void loadData() {
        plugin.reloadConfig();
        String langCode = plugin.getConfig().getString("language", "ES");
        try {
            languageManager.setLanguage(LanguageManager.Lang.valueOf(langCode));
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Idioma en config.yml inválido, usando ES.");
            languageManager.setLanguage(LanguageManager.Lang.ES);
        }

        portalsFile = new File(plugin.getDataFolder(), "portals.yml");
        if (!portalsFile.exists()) {
            try {
                portalsFile.getParentFile().mkdirs();
                portalsFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("No se pudo crear portals.yml");
            }
        }
        portalsConfig = YamlConfiguration.loadConfiguration(portalsFile);

        if (portalsConfig.contains("portals")) {
            ConfigurationSection sec = portalsConfig.getConfigurationSection("portals");
            for (String key : sec.getKeys(false)) {
                try {
                    UUID id = UUID.fromString(key);
                    UUID ownerId = UUID.fromString(sec.getString(key + ".owner"));
                    String name = sec.getString(key + ".name");
                    Material icon = Material.valueOf(sec.getString(key + ".icon"));

                    String worldName = sec.getString(key + ".loc.world");
                    double x = sec.getDouble(key + ".loc.x");
                    double y = sec.getDouble(key + ".loc.y");
                    double z = sec.getDouble(key + ".loc.z");
                    World world = Bukkit.getWorld(worldName);

                    if (world == null) continue;
                    Location loc = new Location(world, x, y, z);

                    double dirX = sec.getDouble(key + ".dir.x");
                    double dirY = sec.getDouble(key + ".dir.y");
                    double dirZ = sec.getDouble(key + ".dir.z");
                    Vector direction = new Vector(dirX, dirY, dirZ);

                    Portal p = new Portal(id, ownerId, name, loc, direction);
                    p.setIcon(icon);
                    portalsById.put(p.getId(), p);

                } catch (Exception e) {
                    plugin.getLogger().severe("Error cargando portal " + key);
                }
            }
        }

        if (portalsConfig.contains("limits")) {
            ConfigurationSection limitSec = portalsConfig.getConfigurationSection("limits");
            for (String uuidStr : limitSec.getKeys(false)) {
                playerLimits.put(UUID.fromString(uuidStr), limitSec.getInt(uuidStr));
            }
        }
    }

    public void saveData() {
        if (portalsFile == null || portalsConfig == null) return;

        portalsConfig.set("portals", null);
        portalsConfig.set("limits", null);

        for (Portal p : portalsById.values()) {
            String path = "portals." + p.getId().toString();
            portalsConfig.set(path + ".owner", p.getOwnerId().toString());
            portalsConfig.set(path + ".name", p.getName());
            portalsConfig.set(path + ".icon", p.getIcon().name());

            portalsConfig.set(path + ".loc.world", p.getLocation().getWorld().getName());
            portalsConfig.set(path + ".loc.x", p.getLocation().getX());
            portalsConfig.set(path + ".loc.y", p.getLocation().getY());
            portalsConfig.set(path + ".loc.z", p.getLocation().getZ());

            portalsConfig.set(path + ".dir.x", p.getDirection().getX());
            portalsConfig.set(path + ".dir.y", p.getDirection().getY());
            portalsConfig.set(path + ".dir.z", p.getDirection().getZ());
        }

        for (Map.Entry<UUID, Integer> entry : playerLimits.entrySet()) {
            portalsConfig.set("limits." + entry.getKey().toString(), entry.getValue());
        }

        try {
            portalsConfig.save(portalsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGlobalConfig() {
        plugin.getConfig().set("language", languageManager.getCurrentLang().name());
        plugin.saveConfig();
    }

    public void setLimit(UUID playerId, int limit) { playerLimits.put(playerId, limit); }
    public int getLimit(UUID playerId) { return playerLimits.getOrDefault(playerId, DEFAULT_LIMIT); }
    public boolean canCreatePortal(Player player) {
        int currentCount = getPortalsByOwner(player.getUniqueId()).size();
        return currentCount < getLimit(player.getUniqueId());
    }
    public int deleteAllPortals(UUID ownerId) {
        List<Portal> toDelete = getPortalsByOwner(ownerId);
        int count = toDelete.size();
        for (Portal p : toDelete) {
            removePortal(p.getId());
            if (p.getLocation().getWorld() != null) p.getLocation().getBlock().breakNaturally();
        }
        return count;
    }
    public void createPortal(Portal portal) { portalsById.put(portal.getId(), portal); }
    public void removePortal(UUID id) { portalsById.remove(id); }
    public Portal getPortal(UUID id) { return portalsById.get(id); }
    public Portal getPortalAt(Location loc) {
        for (Portal p : portalsById.values()) {
            if (p.getLocation().getWorld().equals(loc.getWorld()) && p.getLocation().distanceSquared(loc) < 0.5) return p;
        }
        return null;
    }
    public List<Portal> getPortalsByOwner(UUID ownerId) {
        List<Portal> list = new ArrayList<>();
        for (Portal p : portalsById.values()) {
            if (p.getOwnerId().equals(ownerId)) list.add(p);
        }
        return list;
    }
    public Collection<Portal> getAllPortals() { return new ArrayList<>(portalsById.values()); }
    public void setEditingPortal(Player player, Portal portal) { editingSession.put(player.getUniqueId(), portal); }
    public Portal getEditingPortal(Player player) { return editingSession.get(player.getUniqueId()); }
    public LanguageManager getLanguageManager() { return languageManager; }
    public boolean checkIntegrity(Portal portal) {
        Location coreLoc = portal.getLocation();
        if (coreLoc.getWorld() == null) return false;
        Block centerBase = coreLoc.getBlock().getRelative(org.bukkit.block.BlockFace.DOWN);
        if (centerBase.getType() != org.bukkit.Material.END_STONE_BRICKS) return false;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (centerBase.getRelative(x, 0, z).getType() != org.bukkit.Material.END_STONE_BRICKS) return false;
            }
        }
        return true;
    }
}