package me.b3n3dkt.listener;

import eu.skyrasor.Core;
import eu.skyrasor.entity.Animation;
import eu.skyrasor.events.PlayerInteractNPCEvent;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import java.util.Arrays;

public class NPCInteract implements Listener{

    @EventHandler
    public void onHandle(PlayerInteractNPCEvent event){
        Player player = event.getPlayer();
        Core.getExecutorService().execute(() -> {
            if(event.getClickType() == ClickType.RIGHT){
                if(event.getNpcEntity().getEntityPlayer().getName().equalsIgnoreCase("§6§lBelohnungen")){
                    Inventory inv = Bukkit.createInventory((InventoryHolder) null, 27, "§6§lBelohnungen");
                    ItemStack glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                    ItemMeta glasmeta = glas.getItemMeta();
                    glasmeta.setDisplayName("§e§l");
                    glas.setItemMeta(glasmeta);
                    ItemStack tskull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta tmeta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
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
                    player.openInventory(inv);

                    for(int ix = 0; ix < 27; ++ix) {
                        inv.setItem(ix, glas);
                    }

                    inv.setItem(11, tskull);
                    inv.setItem(13, wskull);
                    inv.setItem(15, mskull);
                    player.updateInventory();
                }else if (event.getNpcEntity().getEntityPlayer().getName().equalsIgnoreCase("§6§lRang-Shop")) {
                    Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "§6§lRang-Shop");
                    ItemStack glas = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
                    ItemMeta glasmeta = glas.getItemMeta();
                    glasmeta.setDisplayName("§e§l");
                    glas.setItemMeta(glasmeta);
                    DyeColor yellow = DyeColor.YELLOW;
                    byte yellowdata = (byte)(15 - yellow.getData());
                    ItemStack phoenix = new ItemStack(Material.INK_SACK, 1, yellowdata);
                    ItemMeta phoenixmeta = phoenix.getItemMeta();
                    phoenixmeta.setDisplayName("§e§lPhoenix");
                    phoenixmeta.setLore(Arrays.asList(" ", "§7Kosten: §a200.000$"));
                    phoenix.setItemMeta(phoenixmeta);
                    DyeColor red = DyeColor.RED;
                    byte reddata = (byte)(15 - red.getData());
                    ItemStack legende = new ItemStack(Material.INK_SACK, 1, reddata);
                    ItemMeta legendemeta = legende.getItemMeta();
                    legendemeta.setDisplayName("§c§lLegende");
                    legendemeta.setLore(Arrays.asList(" ", "§7Kosten: §a175.000$"));
                    legende.setItemMeta(legendemeta);
                    DyeColor purple = DyeColor.PURPLE;
                    byte purpledata = (byte)(15 - purple.getData());
                    ItemStack bedrock = new ItemStack(Material.INK_SACK, 1, purpledata);
                    ItemMeta bedrockmeta = bedrock.getItemMeta();
                    bedrockmeta.setDisplayName("§5Bedrock");
                    bedrockmeta.setLore(Arrays.asList(" ", "§7Kosten: §a75.000$"));
                    bedrock.setItemMeta(bedrockmeta);
                    DyeColor blue = DyeColor.BLUE;
                    byte bluedata = (byte)(15 - blue.getData());
                    ItemStack titan = new ItemStack(Material.INK_SACK, 1, bluedata);
                    ItemMeta titanmeta = titan.getItemMeta();
                    titanmeta.setDisplayName("§9Titan");
                    titanmeta.setLore(Arrays.asList(" ", "§7Kosten: §a40.000$"));
                    titan.setItemMeta(titanmeta);
                    DyeColor pink = DyeColor.PINK;
                    byte pinkdata = (byte)(15 - pink.getData());
                    ItemStack obsidian = new ItemStack(Material.INK_SACK, 1, pinkdata);
                    ItemMeta obsidianmeta = obsidian.getItemMeta();
                    obsidianmeta.setDisplayName("§dObsidian");
                    obsidianmeta.setLore(Arrays.asList(" ", "§7Kosten: §a25.000$"));
                    obsidian.setItemMeta(obsidianmeta);
                    DyeColor orange = DyeColor.ORANGE;
                    byte orangedata = (byte)(15 - orange.getData());
                    ItemStack premium = new ItemStack(Material.INK_SACK, 1, orangedata);
                    ItemMeta premiummeta = premium.getItemMeta();
                    premiummeta.setDisplayName("§6Premium");
                    premiummeta.setLore(Arrays.asList(" ", "§7Kosten: §a12.000$"));
                    premium.setItemMeta(premiummeta);
                    player.openInventory(inv);

                    for(int i = 0; i < 54; ++i) {
                        inv.setItem(i, glas);
                    }

                    inv.setItem(10, premium);
                    inv.setItem(13, obsidian);
                    inv.setItem(16, titan);
                    inv.setItem(29, bedrock);
                    inv.setItem(33, legende);
                    inv.setItem(40, phoenix);
                    player.updateInventory();
                }
            }else if (event.getClickType() == ClickType.LEFT) {
                event.getNpcEntity().setAnimation(player, Animation.HIT.HIT);
            }


            });
        }
    }
