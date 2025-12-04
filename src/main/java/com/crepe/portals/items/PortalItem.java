package com.crepe.portals.items;

import com.crepe.portals.DynamicPortals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PortalItem {

    public static ItemStack getPortalCore() {
        ItemStack item = new ItemStack(Material.BEACON); // Usamos un Faro como base visual
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Núcleo de Portal Dimensional");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Ponlo sobre una base de 3x1");
        lore.add(ChatColor.GRAY + "de End Stone Bricks.");
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static void registerRecipe(DynamicPortals plugin) {
        // Crafteo: Wither Skull en el medio, rodeado de cristal y obsidiana
        NamespacedKey key = new NamespacedKey(plugin, "portal_core");
        ShapedRecipe recipe = new ShapedRecipe(key, getPortalCore());

        recipe.shape("GGG", "OWO", "OOO");


        recipe.setIngredient('G', Material.TINTED_GLASS);
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL); // El item difícil

        Bukkit.addRecipe(recipe);
    }
}