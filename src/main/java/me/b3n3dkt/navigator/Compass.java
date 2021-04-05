package me.b3n3dkt.navigator;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.b3n3dkt.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Compass implements Listener {

    public static int playercountFB = 0;
    public static int playercountCB = 0;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Navigator")) {
                Inventory inv = Bukkit.createInventory(null, InventoryType.BREWING, "§6Navigator");
                ItemStack fb = new ItemStack(Material.WOOD_AXE);
                ItemMeta fbmeta = fb.getItemMeta();
                fbmeta.setLore(Arrays.asList("§8*Klicke zum Teleportieren*"));
                fbmeta.setDisplayName("§e§lFreebuild");
                fb.setItemMeta(fbmeta);

                ItemStack cb = new ItemStack(Material.DIAMOND_BLOCK);
                ItemMeta cbmeta = cb.getItemMeta();
                cbmeta.setLore(Arrays.asList("§8*Klicke zum Teleportieren*"));
                cbmeta.setDisplayName("§e§lCitybuild");
                cb.setItemMeta(cbmeta);

                ItemStack spawn = new ItemStack(Material.BEACON);
                ItemMeta spawnmeta = spawn.getItemMeta();
                spawnmeta.setDisplayName("§e§lSpawn");
                spawn.setItemMeta(spawnmeta);

                ItemStack dsword = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta dswordItemMeta = dsword.getItemMeta();
                dswordItemMeta.setDisplayName("§aSkyPvP");
                dsword.setItemMeta(dswordItemMeta);

                inv.setItem(0, fb);

                inv.setItem(3, spawn);
                inv.setItem(1, dsword);
                inv.setItem(2, cb);
                p.openInventory(inv);
            }
            else if (e.getItem().getItemMeta().getDisplayName().equals("§6Spieler Verstecken")) {
                Inventory inv = Bukkit.createInventory(null, 27, "§6Spieler Verstecken");

                ItemStack glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
                ItemMeta glasmeta = glas.getItemMeta();
                glasmeta.setDisplayName("§e§l");
                glas.setItemMeta(glasmeta);

                DyeColor pink = DyeColor.PINK;

                ItemStack ipink = new ItemStack(Material.INK_SACK, 1, (short) 5);
                ItemMeta pinkmeta = ipink.getItemMeta();
                pinkmeta.setDisplayName("§5§lNur Teammitglieder");
                ipink.setItemMeta(pinkmeta);

                DyeColor green = DyeColor.GREEN;

                ItemStack igreen = new ItemStack(Material.INK_SACK, 1, (short) 10);
                ItemMeta greenmeta = igreen.getItemMeta();
                greenmeta.setDisplayName("§a§lAlle Spieler");
                igreen.setItemMeta(greenmeta);

                DyeColor red = DyeColor.RED;

                ItemStack ired = new ItemStack(Material.INK_SACK, 1, (short) 1);
                ItemMeta redmeta = ired.getItemMeta();
                redmeta.setDisplayName("§4§lKeine Spieler");
                ired.setItemMeta(redmeta);

                p.openInventory(inv);

                for (int i = 0; i < 27; i++) {
                    inv.setItem(i, glas);
                }
                inv.setItem(11, igreen);
                inv.setItem(13, ipink);
                inv.setItem(15, ired);

                p.updateInventory();
            }

        }
        catch (Exception exception) {}
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        try {
            if (e.getView().getTitle().equalsIgnoreCase("§6Navigator")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getType() == Material.WOOD_AXE) {
                    File file = new File("plugins//Lobby//spawns.yml");
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    String w = cfg.getString("fb.World");
                    double x = cfg.getDouble("fb.X");
                    double y = cfg.getDouble("fb.Y");
                    double z = cfg.getDouble("fb.Z");
                    double yaw = cfg.getDouble("fb.Yaw");
                    double pitch = cfg.getDouble("fb.Pitch");
                    Location loc = new Location(Bukkit.getWorld(w), x, y, z);
                    loc.setYaw((float)yaw);
                    loc.setPitch((float)pitch);
                    p.teleport(loc);
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast dich zu Freebuild teleportiert!");
                    p.closeInventory();
                }
                else if (e.getCurrentItem().getType() == Material.BEACON) {
                    File file = new File("plugins//Lobby//spawns.yml");
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    String w = cfg.getString("Spawn.World");
                    double x = cfg.getDouble("Spawn.X");
                    double y = cfg.getDouble("Spawn.Y");
                    double z = cfg.getDouble("Spawn.Z");
                    double yaw = cfg.getDouble("Spawn.Yaw");
                    double pitch = cfg.getDouble("Spawn.Pitch");
                    Location loc = new Location(Bukkit.getWorld(w), x, y, z);
                    loc.setYaw((float)yaw);
                    loc.setPitch((float)pitch);
                    p.teleport(loc);
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast dich zu Spawn teleportiert!");
                    p.closeInventory();
                }
                else if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                    File file = new File("plugins//Lobby//spawns.yml");
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    String w = cfg.getString("cb.World");
                    double x = cfg.getDouble("cb.X");
                    double y = cfg.getDouble("cb.Y");
                    double z = cfg.getDouble("cb.Z");
                    double yaw = cfg.getDouble("cb.Yaw");
                    double pitch = cfg.getDouble("cb.Pitch");
                    Location loc = new Location(Bukkit.getWorld(w), x, y, z);
                    loc.setYaw((float)yaw);
                    loc.setPitch((float)pitch);
                    p.teleport(loc);
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast dich zu Citybuild teleportiert!");
                    p.closeInventory();
                }else if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                    File file = new File("plugins//Lobby//spawns.yml");
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    String w = cfg.getString("skypvp.World");
                    double x = cfg.getDouble("skypvp.X");
                    double y = cfg.getDouble("skypvp.Y");
                    double z = cfg.getDouble("skypvp.Z");
                    double yaw = cfg.getDouble("skypvp.Yaw");
                    double pitch = cfg.getDouble("skypvp.Pitch");
                    Location loc = new Location(Bukkit.getWorld(w), x, y, z);
                    loc.setYaw((float)yaw);
                    loc.setPitch((float)pitch);
                    p.teleport(loc);
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§aDu hast dich zu SkyPvP teleportiert!");
                    p.closeInventory();
                }

            } else if (e.getView().getTitle().equals("§6Spieler Verstecken")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lAlle Spieler")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.showPlayer(p);
                        p.showPlayer(all);
                    }
                    p.closeInventory();
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du siehst nun wieder §a§lAlle Spieler§7!");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§5§lNur Teammitglieder")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.showPlayer(p);
                        p.showPlayer(all);
                        if (!all.hasPermission("skyrasor.lobby.teammitglied")) {

                            all.hidePlayer(p);
                            p.hidePlayer(all);
                        }
                    }

                    p.closeInventory();
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du siehst nun §5§lnur Teammitglieder§7!");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lKeine Spieler")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.hidePlayer(p);
                        p.hidePlayer(all);
                    }
                    p.closeInventory();
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du siehst nun §4§lkeine Spieler§7!");
                }
            }
        } catch (Exception exception) {}
    }

    private void connectToServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                out.writeUTF(server);
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.sendPluginMessage(Lobby.getMain(), "BungeeCord", b.toByteArray());
        } catch (org.bukkit.plugin.messaging.ChannelNotRegisteredException e) {
            Bukkit.getLogger().warning(" ERROR - Usage of bungeecord connect effects is not possible. Your server is not having bungeecord support (Bungeecord channel is not registered in your minecraft server)!");
        }
    }

}
