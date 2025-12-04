package com.crepe.portals.listeners;

import com.crepe.portals.enums.Message;
import com.crepe.portals.gui.PortalGUI;
import com.crepe.portals.manager.PortalManager;
import com.crepe.portals.model.Portal;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GUIListener implements Listener {

    private final PortalManager portalManager;
    private final Set<UUID> playersRenaming = new HashSet<>();

    public GUIListener(PortalManager portalManager) {
        this.portalManager = portalManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;

        String currentTitle = PlainTextComponentSerializer.plainText().serialize(event.getView().title());

        String tTravel = portalManager.getLanguageManager().getPlain(Message.GUI_TITLE_TRAVEL);
        String tManager = portalManager.getLanguageManager().getPlain(Message.GUI_TITLE_MANAGER);
        String tEdit = portalManager.getLanguageManager().getPlain(Message.GUI_TITLE_EDIT);
        String tIcon = portalManager.getLanguageManager().getPlain(Message.GUI_TITLE_ICON);

        if (!currentTitle.equals(tTravel) && !currentTitle.equals(tManager) &&
                !currentTitle.equals(tEdit) && !currentTitle.equals(tIcon)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        if (clicked.getType() == Material.AIR) return;

        if (currentTitle.equals(tTravel)) {
            if (clicked.getType() == Material.COMPARATOR) {
                PortalGUI.openManagerMenu(player, portalManager);
                return;
            }
            UUID targetId = getUUIDFromLore(clicked);
            if (targetId != null) {
                Portal target = portalManager.getPortal(targetId);
                if (target != null) {
                    player.closeInventory();
                    player.teleport(target.getLocation().clone().add(0.5, 0, 0.5));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }
            }
        }

        if (currentTitle.equals(tManager)) {
            UUID targetId = getUUIDFromLore(clicked);
            if (targetId != null) {
                Portal target = portalManager.getPortal(targetId);
                if (target != null) {
                    portalManager.setEditingPortal(player, target);
                    PortalGUI.openEditMenu(player, target, portalManager);
                }
            }
        }

        if (currentTitle.equals(tEdit)) {
            if (clicked.getType() == Material.NAME_TAG) {
                player.closeInventory();
                playersRenaming.add(player.getUniqueId());
                player.sendMessage(portalManager.getLanguageManager().get(Message.MSG_RENAMING));
            } else {
                PortalGUI.openIconSelector(player, portalManager);
            }
        }

        if (currentTitle.equals(tIcon)) {
            Portal session = portalManager.getEditingPortal(player);
            if (session != null) {
                session.setIcon(clicked.getType());
                player.sendMessage(portalManager.getLanguageManager().get(Message.MSG_ICON_UPDATED));
                PortalGUI.openEditMenu(player, session, portalManager);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (playersRenaming.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);

            String newName = PlainTextComponentSerializer.plainText().serialize(event.message());

            Portal session = portalManager.getEditingPortal(event.getPlayer());

            if (session != null) {
                session.setName(newName);
                event.getPlayer().sendMessage(portalManager.getLanguageManager().get(Message.MSG_NAME_UPDATED));
                playersRenaming.remove(event.getPlayer().getUniqueId());

                event.getPlayer().getServer().getScheduler().runTask(
                        com.crepe.portals.DynamicPortals.getPlugin(com.crepe.portals.DynamicPortals.class),
                        () -> PortalGUI.openEditMenu(event.getPlayer(), session, portalManager)
                );
            }
        }
    }

    private UUID getUUIDFromLore(ItemStack item) {
        List<Component> lore = item.lore();
        if (lore == null) return null;
        for (Component c : lore) {
            String line = PlainTextComponentSerializer.plainText().serialize(c);
            if (line.contains("ID:")) {
                try {
                    String uuidString = line.replace("ID:", "").trim();
                    return UUID.fromString(uuidString);
                } catch (IllegalArgumentException e) {
                }
            }
        }
        return null;
    }
}