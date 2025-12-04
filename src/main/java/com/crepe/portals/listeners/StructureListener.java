package com.crepe.portals.listeners;

import com.crepe.portals.enums.Message;
import com.crepe.portals.gui.PortalGUI;
import com.crepe.portals.items.CustomRecipe;
import com.crepe.portals.manager.PortalManager;
import com.crepe.portals.model.Portal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class StructureListener implements Listener {

    private final PortalManager portalManager;

    public StructureListener(PortalManager portalManager) {
        this.portalManager = portalManager;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!CustomRecipe.isPortalCore(event.getItemInHand())) return;

        if (!portalManager.canCreatePortal(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Component.text("¡Límite de portales alcanzado!").color(NamedTextColor.RED));
            event.getPlayer().playSound(event.getBlock().getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
            return;
        }

        Block core = event.getBlockPlaced();
        Block baseCenter = core.getRelative(BlockFace.DOWN);

        boolean isValid = true;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (baseCenter.getRelative(x, 0, z).getType() != Material.END_STONE_BRICKS) {
                    isValid = false;
                    break;
                }
            }
        }

        if (!isValid) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(portalManager.getLanguageManager().get(Message.ERROR_INVALID_BASE));
            return;
        }

        String name = "Portal #" + (portalManager.getPortalsByOwner(event.getPlayer().getUniqueId()).size() + 1);
        Portal newPortal = new Portal(
                event.getPlayer().getUniqueId(),
                name,
                core.getLocation(),
                event.getPlayer().getLocation().getDirection()
        );

        portalManager.createPortal(newPortal);

        event.getPlayer().sendMessage(portalManager.getLanguageManager().get(Message.PORTAL_CREATED));
        event.getPlayer().playSound(core.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        Portal portal = portalManager.getPortalAt(event.getClickedBlock().getLocation());
        if (portal == null) return;

        event.setCancelled(true);

        if (!portalManager.checkIntegrity(portal)) {
            event.getPlayer().sendMessage(portalManager.getLanguageManager().get(Message.ERROR_INTEGRITY_LOST));
            portalManager.removePortal(portal.getId());
            return;
        }

        PortalGUI.openTeleportMenu(event.getPlayer(), portalManager, portal);
        event.getPlayer().playSound(portal.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1f, 2f);
    }
}