package me.b3n3dkt.settings;

import me.b3n3dkt.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Settings implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Einstellungen")) {
                p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cNoch nicht verfügbar...");
            }
        } catch (Exception exception) {}
    }
}
