package com.crepe.portals.model;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import java.util.UUID;

public class Portal {
    private final UUID id;
    private final UUID ownerId;
    private String name;
    private Material icon;
    private final Location center;
    private final Vector direction;

    public Portal(UUID ownerId, String name, Location center, Vector direction) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
        this.name = name;
        this.center = center;
        this.direction = direction;
        this.icon = Material.ENDER_PEARL;
    }

    public Portal(UUID id, UUID ownerId, String name, Location center, Vector direction) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.center = center;
        this.direction = direction;
        this.icon = Material.ENDER_PEARL;
    }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setIcon(Material icon) { this.icon = icon; }

    // Getters
    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public Material getIcon() { return icon; }
    public Location getLocation() { return center.clone(); }
    public Vector getDirection() { return direction.clone(); }
}