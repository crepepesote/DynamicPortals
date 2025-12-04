package com.crepe.portals.commands;

import com.crepe.portals.enums.Message;
import com.crepe.portals.manager.LanguageManager;
import com.crepe.portals.manager.PortalManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCmd implements CommandExecutor {

    private final PortalManager portalManager;

    public AdminCmd(PortalManager portalManager) {
        this.portalManager = portalManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("dynamicportals.admin") && !sender.isOp()) {
            sender.sendMessage(portalManager.getLanguageManager().get(Message.ADMIN_NO_PERM));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCmd = args[0].toLowerCase();

        switch (subCmd) {
            case "language" -> {
                if (args.length != 2) {
                    sender.sendMessage(Component.text("Usage: /portaladmin language <ES|EN|FR|PT>").color(NamedTextColor.RED));
                    return true;
                }
                try {
                    LanguageManager.Lang newLang = LanguageManager.Lang.valueOf(args[1].toUpperCase());
                    portalManager.getLanguageManager().setLanguage(newLang);

                    portalManager.saveGlobalConfig();

                    Component msg = portalManager.getLanguageManager().get(Message.ADMIN_LANG_CHANGED)
                            .append(Component.text(newLang.name()).color(NamedTextColor.AQUA));

                    sender.sendMessage(msg);

                } catch (IllegalArgumentException e) {
                    sender.sendMessage(Component.text("Invalid language. Available: ES, EN, FR, PT").color(NamedTextColor.RED));
                }
                return true;
            }

            case "setlimit" -> {
                if (args.length != 3) {
                    sender.sendMessage(Component.text("Usage: /portaladmin setlimit <player> <amount>").color(NamedTextColor.RED));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(portalManager.getLanguageManager().get(Message.ADMIN_PLAYER_NOT_FOUND));
                    return true;
                }

                try {
                    int limit = Integer.parseInt(args[2]);
                    if (limit < 0) {
                        sender.sendMessage(Component.text("Limit must be positive.").color(NamedTextColor.RED));
                        return true;
                    }

                    portalManager.setLimit(target.getUniqueId(), limit);

                    sender.sendMessage(portalManager.getLanguageManager().get(Message.ADMIN_LIMIT_SET,
                                    "%player%", target.getName())
                            .append(Component.text(String.valueOf(limit))));

                } catch (NumberFormatException e) {
                    sender.sendMessage(Component.text("Invalid number.").color(NamedTextColor.RED));
                }
                return true;
            }

            case "deleteall" -> {
                if (args.length != 2) {
                    sender.sendMessage(Component.text("Usage: /portaladmin deleteall <player>").color(NamedTextColor.RED));
                    return true;
                }

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                int count = portalManager.deleteAllPortals(target.getUniqueId());

                String name = target.getName() != null ? target.getName() : "Unknown";
                sender.sendMessage(portalManager.getLanguageManager().get(Message.ADMIN_DELETED_ALL,
                        "%count%", String.valueOf(count),
                        "%player%", name));

                return true;
            }

            default -> sendHelp(sender);
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(Component.text("--- Portal Admin ---").color(NamedTextColor.AQUA));
        sender.sendMessage(Component.text("/portaladmin language <ES|EN|FR|PT>").color(NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/portaladmin setlimit <player> <amount>").color(NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/portaladmin deleteall <player>").color(NamedTextColor.GRAY));
    }
}