package me.b3n3dkt.navigator;

import java.io.File;
import java.io.IOException;

import me.b3n3dkt.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class Locations implements Listener {

    @EventHandler
    public void onPlayer(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        if (e.getMessage().equalsIgnoreCase("/setfb")) {
            if (p.hasPermission("skyrasor.command.setspawns")) {
                File file = new File("plugins//Lobby//spawns.yml");
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.set("fb.X", Double.valueOf(p.getLocation().getX()));
                cfg.set("fb.Y", Double.valueOf(p.getLocation().getY()));
                cfg.set("fb.Z", Double.valueOf(p.getLocation().getZ()));
                cfg.set("fb.Yaw", Double.valueOf(p.getLocation().getYaw()));
                cfg.set("fb.Pitch", Double.valueOf(p.getLocation().getPitch()));
                cfg.set("fb.World", p.getLocation().getWorld().getName());
                try {
                    cfg.save(file);
                } catch (IOException e1) {
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    return;
                }
                p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast den Spawn für §8'§eFreeBuild§8' §aerfolgreich gesetzt!");
            } else {

                p.sendMessage(Lobby.getNoperm());
            }

        } else if (e.getMessage().equalsIgnoreCase("/setspawn")) {

            if (p.hasPermission("lobby.admin")) {
                File file = new File("plugins//Lobby//spawns.yml");
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.set("Spawn.X", Double.valueOf(p.getLocation().getX()));
                cfg.set("Spawn.Y", Double.valueOf(p.getLocation().getY()));
                cfg.set("Spawn.Z", Double.valueOf(p.getLocation().getZ()));
                cfg.set("Spawn.Yaw", Double.valueOf(p.getLocation().getYaw()));
                cfg.set("Spawn.Pitch", Double.valueOf(p.getLocation().getPitch()));
                cfg.set("Spawn.World", p.getLocation().getWorld().getName());
                try {
                    cfg.save(file);
                } catch (IOException e1) {
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    return;
                }
                p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast den Spawn für §8'§eSpawn§8' §aerfolgreich gesetzt!");
            } else {

                p.sendMessage(Lobby.getNoperm());
            }

        } else if (e.getMessage().equalsIgnoreCase("/setcb")) {

            if (p.hasPermission("lobby.admin")) {
                File file = new File("plugins//Lobby//spawns.yml");
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.set("cb.X", Double.valueOf(p.getLocation().getX()));
                cfg.set("cb.Y", Double.valueOf(p.getLocation().getY()));
                cfg.set("cb.Z", Double.valueOf(p.getLocation().getZ()));
                cfg.set("cb.Yaw", Double.valueOf(p.getLocation().getYaw()));
                cfg.set("cb.Pitch", Double.valueOf(p.getLocation().getPitch()));
                cfg.set("cb.World", p.getLocation().getWorld().getName());
                try {
                    cfg.save(file);
                } catch (IOException e1) {
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    return;
                }
                p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast den Spawn für §8'§eCitybuild§8' §aerfolgreich gesetzt!");
            } else {

                p.sendMessage(Lobby.getNoperm());
            }
        }else if (e.getMessage().equalsIgnoreCase("/setskypvp")) {

            if (p.hasPermission("lobby.admin")) {
                File file = new File("plugins//Lobby//spawns.yml");
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.set("skypvp.X", Double.valueOf(p.getLocation().getX()));
                cfg.set("skypvp.Y", Double.valueOf(p.getLocation().getY()));
                cfg.set("skypvp.Z", Double.valueOf(p.getLocation().getZ()));
                cfg.set("skypvp.Yaw", Double.valueOf(p.getLocation().getYaw()));
                cfg.set("skypvp.Pitch", Double.valueOf(p.getLocation().getPitch()));
                cfg.set("skypvp.World", p.getLocation().getWorld().getName());
                try {
                    cfg.save(file);
                } catch (IOException e1) {
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(Lobby.getPrefix()) + "§cEin fehler ist aufgetreten! Starte den Server neu!");
                    return;
                }
                p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast den Spawn für §8'§aSkyPvP§8' §aerfolgreich gesetzt!");
            } else {

                p.sendMessage(Lobby.getNoperm());
            }
        }
    }
}
