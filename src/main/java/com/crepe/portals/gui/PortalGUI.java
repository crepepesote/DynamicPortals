package com.crepe.portals.gui;

import com.crepe.portals.enums.Message;
import com.crepe.portals.manager.PortalManager;
import com.crepe.portals.model.Portal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PortalGUI {

    public static void openTeleportMenu(Player player, PortalManager manager, Portal currentPortal) {
        String title = manager.getLanguageManager().getPlain(Message.GUI_TITLE_TRAVEL);
        Inventory inv = Bukkit.createInventory(null, 27, Component.text(title));

        List<Portal> myPortals = manager.getPortalsByOwner(player.getUniqueId());

        for (Portal target : myPortals) {
            if (target.getId().equals(currentPortal.getId())) continue;

            ItemStack icon = new ItemStack(target.getIcon());
            ItemMeta meta = icon.getItemMeta();
            meta.displayName(Component.text(target.getName()).color(NamedTextColor.AQUA));

            meta.lore(List.of(
                    manager.getLanguageManager().get(Message.BTN_TRAVEL),
                    Component.text("ID:" + target.getId().toString()).color(NamedTextColor.BLACK)
            ));
            icon.setItemMeta(meta);
            inv.addItem(icon);
        }

        ItemStack settings = new ItemStack(Material.COMPARATOR);
        ItemMeta settingsMeta = settings.getItemMeta();
        // Nombre del botón traducido
        settingsMeta.displayName(manager.getLanguageManager().get(Message.BTN_ADMIN));
        settings.setItemMeta(settingsMeta);
        inv.setItem(26, settings);

        player.openInventory(inv);
    }

    public static void openManagerMenu(Player player, PortalManager manager) {
        String title = manager.getLanguageManager().getPlain(Message.GUI_TITLE_MANAGER);
        Inventory inv = Bukkit.createInventory(null, 27, Component.text(title));

        for (Portal p : manager.getPortalsByOwner(player.getUniqueId())) {
            ItemStack icon = new ItemStack(p.getIcon());
            ItemMeta meta = icon.getItemMeta();
            meta.displayName(Component.text(p.getName()).color(NamedTextColor.GOLD));
            meta.lore(List.of(
                    Component.text("ID:" + p.getId().toString()).color(NamedTextColor.BLACK)
            ));
            icon.setItemMeta(meta);
            inv.addItem(icon);
        }
        player.openInventory(inv);
    }

    public static void openEditMenu(Player player, Portal portal, PortalManager manager) {
        String title = manager.getLanguageManager().getPlain(Message.GUI_TITLE_EDIT);
        Inventory inv = Bukkit.createInventory(null, 9, Component.text(title));

        ItemStack nameBtn = new ItemStack(Material.NAME_TAG);
        ItemMeta nameMeta = nameBtn.getItemMeta();
        nameMeta.displayName(manager.getLanguageManager().get(Message.BTN_EDIT_NAME));
        nameBtn.setItemMeta(nameMeta);
        inv.setItem(3, nameBtn);

        ItemStack iconBtn = new ItemStack(portal.getIcon());
        ItemMeta iconMeta = iconBtn.getItemMeta();
        iconMeta.displayName(manager.getLanguageManager().get(Message.BTN_EDIT_ICON));
        iconBtn.setItemMeta(iconMeta);
        inv.setItem(5, iconBtn);

        player.openInventory(inv);
    }

    public static void openIconSelector(Player player, PortalManager manager) {
        String title = manager.getLanguageManager().getPlain(Message.GUI_TITLE_ICON);
        Inventory inv = Bukkit.createInventory(null, 27, Component.text(title));

        Material[] icons = {
                Material.GRASS_BLOCK, Material.COBBLESTONE, Material.OAK_LOG, Material.BRICKS,
                Material.DIAMOND_ORE, Material.GOLD_INGOT, Material.IRON_SWORD, Material.SHIELD,
                Material.NETHERRACK, Material.MAGMA_CREAM, Material.ENDER_EYE, Material.OBSIDIAN,
                Material.BOOKSHELF, Material.CAKE, Material.LAVA_BUCKET, Material.TNT
        };
        for (Material mat : icons) {
            inv.addItem(new ItemStack(mat));
        }
        player.openInventory(inv);
    }
}