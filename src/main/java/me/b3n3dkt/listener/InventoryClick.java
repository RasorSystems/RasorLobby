package me.b3n3dkt.listener;

import me.b3n3dkt.Lobby;
import me.b3n3dkt.mysql.MySQL;
import me.b3n3dkt.utils.Belohnungen;
import me.b3n3dkt.utils.Rang;
import me.b3n3dkt.utils.Score;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.persistence.Lob;
import java.util.Arrays;

public class InventoryClick implements Listener {
    
    @EventHandler
    public void onHandle(InventoryClickEvent e){
        Score sb = new Score((Player) e.getWhoClicked());
        ItemStack tskull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta tmeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        tmeta.setDisplayName("§6§lTägliche Belohnungen");
        tmeta.setOwner("MHF_Chest");
        tskull.setItemMeta(tmeta);
        ItemStack wskull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta wmeta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        wmeta.setDisplayName("§6§lWöchentliche Belohnungen");
        wmeta.setOwner("MHF_Chest");
        wskull.setItemMeta(wmeta);
        ItemStack mskull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta mmeta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        mmeta.setDisplayName("§6§lMonatliche Belohnungen");
        mmeta.setOwner("MHF_Chest");
        mskull.setItemMeta(mmeta);
        Player p = (Player)e.getWhoClicked();
        String uuid = p.getUniqueId().toString();
        Score scoreBoard = new Score(p);
        if(e.getInventory() == null){
            return;
        }
        if(e.getInventory().getTitle() == null){
            return;
        }
        if(e.getCurrentItem() == null){
            return;
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§6§lBelohnungen")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lTägliche Belohnungen")) {
                    if (Belohnungen.usedTägliche(uuid)) {
                        if (Belohnungen.getEndTägliche(uuid) <= System.currentTimeMillis()) {
                            Belohnungen.unsetUsedTäglich(uuid);
                            Belohnungen.setUsedTäglich(uuid, p.getName(), 86400L);
                            p.closeInventory();
                            MySQL.setcoins(uuid, MySQL.getcoins(uuid) + 250.0D);
                            scoreBoard.update();
                        } else {
                            p.closeInventory();
                            p.sendMessage(Lobby.getPrefix() + "§7Du kannst die Täglichen Belohnungen erst wieder in §r" + Belohnungen.getRemainingTimeTägliche(uuid) + " §7abholen!");
                        }
                    } else {
                        Belohnungen.setUsedTäglich(uuid, p.getName(), 86400L);
                        p.closeInventory();
                        MySQL.setcoins(uuid, MySQL.getcoins(uuid) + 250.0D);
                        scoreBoard.update();
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lWöchentliche Belohnungen")) {
                    if (Belohnungen.usedWöchentliche(uuid)) {
                        if (Belohnungen.getEndWöchentliche(uuid) <= System.currentTimeMillis()) {
                            Belohnungen.unsetUsedWöchentlich(uuid);
                            Belohnungen.setUsedWöchentlich(uuid, p.getName(), 604800L);
                            p.closeInventory();
                            MySQL.setcoins(uuid, MySQL.getcoins(uuid) + 800.0D);
                            scoreBoard.update();
                        } else {
                            p.closeInventory();
                            p.sendMessage(Lobby.getPrefix() + "§7Du kannst die Wöchentlichen Belohnungen erst wieder in §r" + Belohnungen.getRemainingTimeWöchentliche(uuid) + " §7abholen!");
                        }
                    } else {
                        Belohnungen.setUsedWöchentlich(uuid, p.getName(), 604800L);
                        p.closeInventory();
                        MySQL.setcoins(uuid, MySQL.getcoins(uuid) + 800.0D);
                        scoreBoard.update();
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lMonatliche Belohnungen")) {
                    if (Belohnungen.usedMonatliche(uuid)) {
                        if (Belohnungen.getEndMonatliche(uuid) <= System.currentTimeMillis()) {
                            Belohnungen.unsetUsedMonatlich(uuid);
                            Belohnungen.setUsedMontalich(uuid, p.getName(), 2419200L);
                            p.closeInventory();
                            MySQL.setcoins(uuid, MySQL.getcoins(uuid) + 2000.0D);
                            scoreBoard.update();
                        } else {
                            p.closeInventory();
                            p.sendMessage(Lobby.getPrefix() + "§7Du kannst die Monatlichen Belohnungen erst wieder in §r" + Belohnungen.getRemainingTimeMonatliche(uuid) + " §7abholen!");
                        }
                    } else {
                        Belohnungen.setUsedMontalich(uuid, p.getName(), 2419200L);
                        p.closeInventory();
                        MySQL.setcoins(uuid, MySQL.getcoins(uuid) + 2000.0D);
                        scoreBoard.update();
                    }
                }
            }
        }else if (e.getInventory().getTitle().equals("§6§lRang-Shop")) {
            e.setCancelled(true);
            Inventory inv;
            ItemStack glas;
            ItemMeta glasmeta;
            ItemStack greenglas;
            ItemMeta greenglasmeta;
            ItemStack redglas;
            ItemMeta redglasmeta;
            int i;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Premium")) {
                e.setCancelled(true);
                if (MySQL.getcoins(uuid) >= 12000.0D) {
                    if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) > 21) {
                        inv = Bukkit.createInventory(p, 27, "§7Rang §6Premium §7kaufen?");
                        p.closeInventory();
                        p.openInventory(inv);
                        glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                        glasmeta = glas.getItemMeta();
                        glasmeta.setDisplayName("§e§l");
                        glas.setItemMeta(glasmeta);
                        greenglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                        greenglasmeta = greenglas.getItemMeta();
                        greenglasmeta.setDisplayName("§aJa");
                        greenglasmeta.setLore(Arrays.asList(" ", "§7Kosten: §a12.000$"));
                        greenglas.setItemMeta(greenglasmeta);
                        redglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                        redglasmeta = redglas.getItemMeta();
                        redglasmeta.setDisplayName("§cNein");
                        redglas.setItemMeta(redglasmeta);

                        for(i = 0; i < 27; ++i) {
                            inv.setItem(i, glas);
                        }

                        inv.setItem(11, greenglas);
                        inv.setItem(15, redglas);
                        p.updateInventory();
                    } else if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) == 21) {
                        p.sendMessage(Lobby.getPrefix() + "§cDu besitzt den Rang bereits!");
                    } else if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) < 21) {
                        p.sendMessage(Lobby.getPrefix() + "§cDu kannst keinen niedriegeren Rang kaufen!");
                    }
                } else {
                    p.sendMessage(Lobby.getPrefix() + "§cDu hast für den Rang nicht genug Geld!");
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dObsidian")) {
                e.setCancelled(true);
                if (MySQL.getcoins(uuid) >= 25000.0D) {
                    if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) < 20) {
                        if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) == 20) {
                            p.sendMessage(Lobby.getPrefix() + "§cDu besitzt den Rang bereits!");
                        }else {
                            p.sendMessage(Lobby.getPrefix() + "§cDu kannst keinen niedriegeren Rang kaufen!");
                        }
                    } else {
                        inv = Bukkit.createInventory(p, 27, "§7Rang §dObsidian §7kaufen?");
                        p.closeInventory();
                        p.openInventory(inv);
                        glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                        glasmeta = glas.getItemMeta();
                        glasmeta.setDisplayName("§e§l");
                        glas.setItemMeta(glasmeta);
                        greenglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                        greenglasmeta = greenglas.getItemMeta();
                        greenglasmeta.setDisplayName("§aJa");
                        greenglasmeta.setLore(Arrays.asList(" ", "§7Kosten: §a25.000$"));
                        greenglas.setItemMeta(greenglasmeta);
                        redglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                        redglasmeta = redglas.getItemMeta();
                        redglasmeta.setDisplayName("§cNein");
                        redglas.setItemMeta(redglasmeta);

                        for(i = 0; i < 27; ++i) {
                            inv.setItem(i, glas);
                        }

                        inv.setItem(11, greenglas);
                        inv.setItem(15, redglas);
                        p.updateInventory();
                    }
                } else {
                    p.sendMessage(Lobby.getPrefix() + "§cDu hast für den Rang nicht genug Geld!");
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§9Titan")) {
                e.setCancelled(true);
                if (MySQL.getcoins(uuid) >=40000.0D) {
                    if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) < 19) {
                        if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) == 19) {
                            p.sendMessage(Lobby.getPrefix() + "§cDu besitzt den Rang bereits!");
                        } else {
                            p.sendMessage(Lobby.getPrefix() + "§cDu kannst dir keinen niedriegeren Rang geben!");
                        }
                    } else {
                        inv = Bukkit.createInventory(p, 27, "§7Rang §9Titan §7kaufen?");
                        p.closeInventory();
                        p.openInventory(inv);
                        glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                        glasmeta = glas.getItemMeta();
                        glasmeta.setDisplayName("§e§l");
                        glas.setItemMeta(glasmeta);
                        greenglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                        greenglasmeta = greenglas.getItemMeta();
                        greenglasmeta.setDisplayName("§aJa");
                        greenglasmeta.setLore(Arrays.asList(" ", "§7Kosten: §a40.000$"));
                        greenglas.setItemMeta(greenglasmeta);
                        redglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                        redglasmeta = redglas.getItemMeta();
                        redglasmeta.setDisplayName("§cNein");
                        redglas.setItemMeta(redglasmeta);

                        for(i = 0; i < 27; ++i) {
                            inv.setItem(i, glas);
                        }

                        inv.setItem(11, greenglas);
                        inv.setItem(15, redglas);
                        p.updateInventory();
                    }
                } else {
                    p.sendMessage(Lobby.getPrefix() + "§cDu hast für den Rang nicht genug Geld!");
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§5Bedrock")) {
                e.setCancelled(true);
                if (MySQL.getcoins(uuid) >= 75000.0D) {
                    if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) < 18) {
                        if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) == 18) {
                            p.sendMessage(Lobby.getPrefix() + "§cDu besitzt den Rang bereits!");
                        } else {
                            p.sendMessage(Lobby.getPrefix() + "§cDu kannst dir keinen niedriegeren Rang geben!");
                        }
                    } else {
                        inv = Bukkit.createInventory(p, 27, "§7Rang §5Bedrock §7kaufen?");
                        p.closeInventory();
                        p.openInventory(inv);
                        glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                        glasmeta = glas.getItemMeta();
                        glasmeta.setDisplayName("§e§l");
                        glas.setItemMeta(glasmeta);
                        greenglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                        greenglasmeta = greenglas.getItemMeta();
                        greenglasmeta.setDisplayName("§aJa");
                        greenglasmeta.setLore(Arrays.asList(" ", "§7Kosten: §75.000$"));
                        greenglas.setItemMeta(greenglasmeta);
                        redglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                        redglasmeta = redglas.getItemMeta();
                        redglasmeta.setDisplayName("§cNein");
                        redglas.setItemMeta(redglasmeta);

                        for(i = 0; i < 27; ++i) {
                            inv.setItem(i, glas);
                        }

                        inv.setItem(11, greenglas);
                        inv.setItem(15, redglas);
                        p.updateInventory();
                    }
                } else {
                    p.sendMessage(Lobby.getPrefix() + "§cDu hast für den Rang nicht genug Geld!");
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lLegende")) {
                e.setCancelled(true);
                if (MySQL.getcoins(uuid) >= 175000.0D) {
                    if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) < 17) {
                        if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) == 17) {
                            p.sendMessage(Lobby.getPrefix() + "§cDu besitzt den Rang bereits!");
                        } else {
                            p.sendMessage(Lobby.getPrefix() + "§cDu kannst dir keinen niedriegeren Rang geben!");
                        }
                    } else {
                        inv = Bukkit.createInventory(p, 27, "§7Rang §c§lLegende §7kaufen?");
                        p.closeInventory();
                        p.openInventory(inv);
                        glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                        glasmeta = glas.getItemMeta();
                        glasmeta.setDisplayName("§e§l");
                        glas.setItemMeta(glasmeta);
                        greenglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                        greenglasmeta = greenglas.getItemMeta();
                        greenglasmeta.setDisplayName("§aJa");
                        greenglasmeta.setLore(Arrays.asList(" ", "§7Kosten: §175.000$"));
                        greenglas.setItemMeta(greenglasmeta);
                        redglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                        redglasmeta = redglas.getItemMeta();
                        redglasmeta.setDisplayName("§cNein");
                        redglas.setItemMeta(redglasmeta);

                        for(i = 0; i < 27; ++i) {
                            inv.setItem(i, glas);
                        }

                        inv.setItem(11, greenglas);
                        inv.setItem(15, redglas);
                        p.updateInventory();
                    }
                } else {
                    p.sendMessage(Lobby.getPrefix() + "§cDu hast für den Rang nicht genug Geld!");
                }
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lPhoenix")) {
                e.setCancelled(true);
                if (MySQL.getcoins(uuid) >= 200000.0D) {
                    if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) < 16) {
                        if (Integer.valueOf(Rang.getTeamNumber(p.getUniqueId().toString())) == 16) {
                            p.sendMessage(Lobby.getPrefix() + "§cDu besitzt den Rang bereits!");
                        } else {
                            p.sendMessage(Lobby.getPrefix() + "§cDu kannst dir keinen niedriegeren Rang geben!");
                        }
                    } else {
                        inv = Bukkit.createInventory(p, 27, "§7Rang §a§lPhoenix §7kaufen?");
                        p.closeInventory();
                        p.openInventory(inv);
                        glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                        glasmeta = glas.getItemMeta();
                        glasmeta.setDisplayName("§e§l");
                        glas.setItemMeta(glasmeta);
                        greenglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                        greenglasmeta = greenglas.getItemMeta();
                        greenglasmeta.setDisplayName("§aJa");
                        greenglasmeta.setLore(Arrays.asList(" ", "§7Kosten: §200.000$"));
                        greenglas.setItemMeta(greenglasmeta);
                        redglas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                        redglasmeta = redglas.getItemMeta();
                        redglasmeta.setDisplayName("§cNein");
                        redglas.setItemMeta(redglasmeta);

                        for(i = 0; i < 27; ++i) {
                            inv.setItem(i, glas);
                        }

                        inv.setItem(11, greenglas);
                        inv.setItem(15, redglas);
                        p.updateInventory();
                    }
                } else {
                    p.sendMessage(Lobby.getPrefix() + "§cDu hast für den Rang nicht genug Geld!");
                }
            }
        } else if (e.getInventory().getTitle().equals("§7Rang §6Premium §7kaufen?")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
                MySQL.setcoins(uuid, MySQL.getcoins(uuid) - 12000.0D);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rang set " + p.getName() + " premium");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        } else if (e.getInventory().getTitle().equals("§7Rang §dObsidian §7kaufen?")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
               MySQL.setcoins(uuid, MySQL.getcoins(uuid) - 25000.0D);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rang set " + p.getName() + " obsidian");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        } else if (e.getInventory().getTitle().equals("§7Rang §9Titan §7kaufen?")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
               MySQL.setcoins(uuid, MySQL.getcoins(uuid) - 40000.0D);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rang set " + p.getName() + " titan");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        } else if (e.getInventory().getTitle().equals("§7Rang §5Bedrock §7kaufen?")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
               MySQL.setcoins(uuid, MySQL.getcoins(uuid) - 75000.0D);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rang set " + p.getName() + " bedrock");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        } else if (e.getInventory().getTitle().equals("§7Rang §c§lLegende §7kaufen?")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
               MySQL.setcoins(uuid, MySQL.getcoins(uuid) - 175000.0D);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rang set " + p.getName() + " legende");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        }else if (e.getInventory().getTitle().equals("§7Rang §a§lPhoenix §7kaufen?")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
                MySQL.setcoins(uuid, MySQL.getcoins(uuid) - 200000.0D);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rang set " + p.getName() + " phoenix");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        }
    }
}
