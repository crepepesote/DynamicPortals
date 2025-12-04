package com.crepe.portals;

import com.crepe.portals.commands.AdminCmd;
import com.crepe.portals.items.CustomRecipe;
import com.crepe.portals.listeners.BreakListener;
import com.crepe.portals.listeners.GUIListener;
import com.crepe.portals.listeners.StructureListener;
import com.crepe.portals.manager.PortalManager;
import com.crepe.portals.tasks.PortalEffectTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicPortals extends JavaPlugin {

    private PortalManager portalManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.portalManager = new PortalManager(this);

        portalManager.loadData();

        CustomRecipe.register(this);

        getServer().getPluginManager().registerEvents(new StructureListener(portalManager), this);
        getServer().getPluginManager().registerEvents(new GUIListener(portalManager), this);
        getServer().getPluginManager().registerEvents(new BreakListener(portalManager), this);

        getCommand("portaladmin").setExecutor(new AdminCmd(portalManager));

        new PortalEffectTask(portalManager).runTaskTimer(this, 0L, 10L);

        getLogger().info("DynamicPortals connected.");
    }

    @Override
    public void onDisable() {
        if (portalManager != null) {
            portalManager.saveData();
            getLogger().info("Data Saved.");
        }
    }
}