package com.crepe.portals.items;

import com.crepe.portals.DynamicPortals;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CustomRecipe {

    public static ItemStack getPortalCoreItem() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Dimensional Core")
                .color(NamedTextColor.AQUA)
                .decoration(TextDecoration.ITALIC, false)); // Quitar cursiva por defecto

        meta.lore(List.of(
                Component.text("Place on a 3x3 base").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text("of End Stone Bricks.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
        ));

        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return item;
    }

    public static void register(DynamicPortals plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "portal_core");

        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, getPortalCoreItem());
        recipe.shape("GGG", "ONO", "OOO");
        recipe.setIngredient('G', Material.TINTED_GLASS);
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('N', Material.NETHER_STAR);

        Bukkit.addRecipe(recipe);
    }

    public static boolean isPortalCore(ItemStack item) {
        if (item == null || item.getType() != Material.BEACON) return false;
        if (!item.hasItemMeta()) return false;

        String name = net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText()
                .serialize(item.getItemMeta().displayName());

        return name.contains("Dimensional Core") || name.contains("Núcleo Dimensional"); // Compatibilidad con viejos
    }
}