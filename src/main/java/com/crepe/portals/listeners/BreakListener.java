package com.crepe.portals.listeners;

import com.crepe.portals.enums.Message;
import com.crepe.portals.items.CustomRecipe;
import com.crepe.portals.manager.PortalManager;
import com.crepe.portals.model.Portal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

    private final PortalManager portalManager;

    public BreakListener(PortalManager portalManager) {
        this.portalManager = portalManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.BEACON) {
            Portal p = portalManager.getPortalAt(block.getLocation());
            if (p != null) {
                portalManager.removePortal(p.getId());
                event.getPlayer().sendMessage(portalManager.getLanguageManager().get(Message.PORTAL_DESTROYED));

                event.setDropItems(false);

                if (event.isDropItems()) {
                    block.getWorld().dropItemNaturally(block.getLocation(), CustomRecipe.getPortalCoreItem());
                } else {
                    block.getWorld().dropItemNaturally(block.getLocation(), CustomRecipe.getPortalCoreItem());
                }
            }
            return;
        }

        if (block.getType() == Material.END_STONE_BRICKS) {
            for (Portal p : portalManager.getAllPortals()) {
                if (isPartOfBase3x3(p, block)) {
                    portalManager.removePortal(p.getId());
                    event.getPlayer().sendMessage(portalManager.getLanguageManager().get(Message.ERROR_INTEGRITY_LOST));

                    Block beaconBlock = p.getLocation().getBlock();
                    beaconBlock.setType(Material.AIR);

                    beaconBlock.getWorld().dropItemNaturally(beaconBlock.getLocation(), CustomRecipe.getPortalCoreItem());
                }
            }
        }
    }

    private boolean isPartOfBase3x3(Portal p, Block brokenBlock) {
        Block centerBase = p.getLocation().getBlock().getRelative(org.bukkit.block.BlockFace.DOWN);
        int diffX = Math.abs(centerBase.getX() - brokenBlock.getX());
        int diffZ = Math.abs(centerBase.getZ() - brokenBlock.getZ());
        int diffY = Math.abs(centerBase.getY() - brokenBlock.getY());
        return diffX <= 1 && diffZ <= 1 && diffY == 0;
    }
}