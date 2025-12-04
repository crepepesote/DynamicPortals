package com.crepe.portals.tasks;

import com.crepe.portals.manager.PortalManager;
import com.crepe.portals.model.Portal;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class PortalEffectTask extends BukkitRunnable {

    private final PortalManager portalManager;

    public PortalEffectTask(PortalManager portalManager) {
        this.portalManager = portalManager;
    }

    @Override
    public void run() {
        for (Portal portal : portalManager.getAllPortals()) {
            Location loc = portal.getLocation().clone().add(0.5, 0.5, 0.5);

            loc.getWorld().spawnParticle(Particle.PORTAL, loc, 10, 0.2, 0.2, 0.2, 0.5);

            for (double y = 1; y < 5; y += 0.5) {
                Location beamLoc = loc.clone().add(0, y, 0);
                Particle.DustOptions dust = new Particle.DustOptions(Color.AQUA, 1.5f);
                loc.getWorld().spawnParticle(Particle.DUST, beamLoc, 1, 0.1, 0.1, 0.1, dust);
            }
        }
    }
}