package com.crepe.portals.manager;

import com.crepe.portals.enums.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.EnumMap;
import java.util.Map;

public class LanguageManager {

    public enum Lang { ES, EN, FR, PT }

    private Lang currentLang = Lang.ES;
    private final Map<Message, String> messages = new EnumMap<>(Message.class);

    public LanguageManager() {
        reloadMessages();
    }

    public void setLanguage(Lang lang) {
        this.currentLang = lang;
        reloadMessages();
    }

    public Lang getCurrentLang() {
        return currentLang;
    }

    public Component get(Message key) {
        String msg = messages.getOrDefault(key, "<red>MISSING: " + key.name());
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public Component get(Message key, String... placeholders) {
        String msg = messages.getOrDefault(key, "<red>MISSING: " + key.name());

        for (int i = 0; i < placeholders.length; i += 2) {
            if (i + 1 < placeholders.length) {
                msg = msg.replace(placeholders[i], placeholders[i+1]);
            }
        }
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public String getPlain(Message key) {
        String msg = messages.getOrDefault(key, "MISSING");
        return PlainTextComponentSerializer.plainText().serialize(MiniMessage.miniMessage().deserialize(msg));
    }

    private void reloadMessages() {
        messages.clear();
        switch (currentLang) {
            case EN -> loadEnglish();
            case FR -> loadFrench();
            case PT -> loadPortuguese();
            default -> loadSpanish();
        }
    }


    private void loadSpanish() {
        messages.put(Message.PREFIX, "<gray>[<aqua>Portals<gray>] ");

        messages.put(Message.PORTAL_CREATED, "<green>¡Portal Activado! <gray>Usa click derecho.");
        messages.put(Message.PORTAL_DESTROYED, "<red>Portal destruido.");
        messages.put(Message.ERROR_INVALID_BASE, "<red>Estructura inválida. Necesitas base 3x3 de Ladrillos del End.");
        messages.put(Message.ERROR_INTEGRITY_LOST, "<red>¡Estructura dañada! Portal desactivado.");

        messages.put(Message.GUI_TITLE_TRAVEL, "Selecciona Destino");
        messages.put(Message.GUI_TITLE_MANAGER, "Administrar Portales");
        messages.put(Message.GUI_TITLE_EDIT, "Configurando Portal");
        messages.put(Message.GUI_TITLE_ICON, "Selecciona Ícono");

        messages.put(Message.BTN_TRAVEL, "<yellow>Click para viajar");
        messages.put(Message.BTN_ADMIN, "<gold>Administrar Mis Portales");
        messages.put(Message.BTN_EDIT_NAME, "<gold>Cambiar Nombre");
        messages.put(Message.BTN_EDIT_ICON, "<green>Cambiar Ícono");

        messages.put(Message.MSG_RENAMING, "<green>Escribe el nuevo nombre en el chat:");
        messages.put(Message.MSG_ICON_UPDATED, "<green>Ícono actualizado.");
        messages.put(Message.MSG_NAME_UPDATED, "<green>Nombre cambiado correctamente.");

        // Admin
        messages.put(Message.ADMIN_LANG_CHANGED, "<green>Idioma cambiado a: <yellow>");
        messages.put(Message.ADMIN_LIMIT_SET, "<green>Límite establecido para <yellow>%player% <green>en: <aqua>");
        messages.put(Message.ADMIN_DELETED_ALL, "<green>Se eliminaron <aqua>%count% <green>portales de <yellow>%player%");
        messages.put(Message.ADMIN_NO_PERM, "<red>No tienes permiso.");
        messages.put(Message.ADMIN_PLAYER_NOT_FOUND, "<red>Jugador no encontrado.");
    }

    private void loadEnglish() {
        messages.put(Message.PREFIX, "<gray>[<aqua>Portals<gray>] ");

        messages.put(Message.PORTAL_CREATED, "<green>Portal Activated! <gray>Use right click.");
        messages.put(Message.PORTAL_DESTROYED, "<red>Portal destroyed.");
        messages.put(Message.ERROR_INVALID_BASE, "<red>Invalid structure. You need a 3x3 End Stone Bricks base.");
        messages.put(Message.ERROR_INTEGRITY_LOST, "<red>Structure damaged! Portal deactivated.");

        messages.put(Message.GUI_TITLE_TRAVEL, "Select Destination");
        messages.put(Message.GUI_TITLE_MANAGER, "Manage Portals");
        messages.put(Message.GUI_TITLE_EDIT, "Configuring Portal");
        messages.put(Message.GUI_TITLE_ICON, "Select Icon");

        messages.put(Message.BTN_TRAVEL, "<yellow>Click to travel");
        messages.put(Message.BTN_ADMIN, "<gold>Manage My Portals");
        messages.put(Message.BTN_EDIT_NAME, "<gold>Change Name");
        messages.put(Message.BTN_EDIT_ICON, "<green>Change Icon");

        messages.put(Message.MSG_RENAMING, "<green>Type new name in chat:");
        messages.put(Message.MSG_ICON_UPDATED, "<green>Icon updated.");
        messages.put(Message.MSG_NAME_UPDATED, "<green>Name changed successfully.");

        // Admin
        messages.put(Message.ADMIN_LANG_CHANGED, "<green>Language changed to: <yellow>");
        messages.put(Message.ADMIN_LIMIT_SET, "<green>Limit set for <yellow>%player% <green>to: <aqua>");
        messages.put(Message.ADMIN_DELETED_ALL, "<green>Deleted <aqua>%count% <green>portals from <yellow>%player%");
        messages.put(Message.ADMIN_NO_PERM, "<red>No permission.");
        messages.put(Message.ADMIN_PLAYER_NOT_FOUND, "<red>Player not found.");
    }

    private void loadFrench() {
        messages.put(Message.PREFIX, "<gray>[<aqua>Portals<gray>] ");

        messages.put(Message.PORTAL_CREATED, "<green>Portail Activé ! <gray>Utilisez le clic droit.");
        messages.put(Message.PORTAL_DESTROYED, "<red>Portail détruit.");
        messages.put(Message.ERROR_INVALID_BASE, "<red>Structure invalide. Base 3x3 de Briques de l'End requise.");
        messages.put(Message.ERROR_INTEGRITY_LOST, "<red>Structure endommagée ! Portail désactivé.");

        messages.put(Message.GUI_TITLE_TRAVEL, "Choisir une destination");
        messages.put(Message.GUI_TITLE_MANAGER, "Gérer les portails");
        messages.put(Message.GUI_TITLE_EDIT, "Configuration du portail");
        messages.put(Message.GUI_TITLE_ICON, "Choisir une icône");

        messages.put(Message.BTN_TRAVEL, "<yellow>Cliquez pour voyager");
        messages.put(Message.BTN_ADMIN, "<gold>Gérer mes portails");
        messages.put(Message.BTN_EDIT_NAME, "<gold>Changer le nom");
        messages.put(Message.BTN_EDIT_ICON, "<green>Changer l'icône");

        messages.put(Message.MSG_RENAMING, "<green>Écrivez le nouveau nom dans le chat :");
        messages.put(Message.MSG_ICON_UPDATED, "<green>Icône mise à jour.");
        messages.put(Message.MSG_NAME_UPDATED, "<green>Nom changé avec succès.");

        // Admin
        messages.put(Message.ADMIN_LANG_CHANGED, "<green>Langue changée en : <yellow>");
        messages.put(Message.ADMIN_LIMIT_SET, "<green>Limite définie pour <yellow>%player% <green>à : <aqua>");
        messages.put(Message.ADMIN_DELETED_ALL, "<green>Supprimé <aqua>%count% <green>portails de <yellow>%player%");
        messages.put(Message.ADMIN_NO_PERM, "<red>Pas de permission.");
        messages.put(Message.ADMIN_PLAYER_NOT_FOUND, "<red>Joueur introuvable.");
    }

    private void loadPortuguese() {
        messages.put(Message.PREFIX, "<gray>[<aqua>Portals<gray>] ");

        messages.put(Message.PORTAL_CREATED, "<green>Portal Ativado! <gray>Use clique direito.");
        messages.put(Message.PORTAL_DESTROYED, "<red>Portal destruído.");
        messages.put(Message.ERROR_INVALID_BASE, "<red>Estrutura inválida. Necessária base 3x3 de Tijolos de Pedra do End.");
        messages.put(Message.ERROR_INTEGRITY_LOST, "<red>Estrutura danificada! Portal desativado.");

        messages.put(Message.GUI_TITLE_TRAVEL, "Selecionar Destino");
        messages.put(Message.GUI_TITLE_MANAGER, "Gerenciar Portais");
        messages.put(Message.GUI_TITLE_EDIT, "Configurando Portal");
        messages.put(Message.GUI_TITLE_ICON, "Selecionar Ícone");

        messages.put(Message.BTN_TRAVEL, "<yellow>Clique para viajar");
        messages.put(Message.BTN_ADMIN, "<gold>Gerenciar Meus Portais");
        messages.put(Message.BTN_EDIT_NAME, "<gold>Mudar Nome");
        messages.put(Message.BTN_EDIT_ICON, "<green>Mudar Ícone");

        messages.put(Message.MSG_RENAMING, "<green>Digite o novo nome no chat:");
        messages.put(Message.MSG_ICON_UPDATED, "<green>Ícone atualizado.");
        messages.put(Message.MSG_NAME_UPDATED, "<green>Nome alterado com sucesso.");

        // Admin
        messages.put(Message.ADMIN_LANG_CHANGED, "<green>Idioma alterado para: <yellow>");
        messages.put(Message.ADMIN_LIMIT_SET, "<green>Limite definido para <yellow>%player% <green>em: <aqua>");
        messages.put(Message.ADMIN_DELETED_ALL, "<green>Excluídos <aqua>%count% <green>portais de <yellow>%player%");
        messages.put(Message.ADMIN_NO_PERM, "<red>Sem permissão.");
        messages.put(Message.ADMIN_PLAYER_NOT_FOUND, "<red>Jogador não encontrado.");
    }
}