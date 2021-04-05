package me.b3n3dkt.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StartInventory
{
    public static void setStartInv(Player p) {
        p.getInventory().clear();
        ItemStack Navigator = new ItemStack(Material.COMPASS);
        ItemMeta NaviMeta = Navigator.getItemMeta();
        NaviMeta.setDisplayName("§6Navigator");
        Navigator.setItemMeta(NaviMeta);

        ItemStack Hide = new ItemStack(Material.BLAZE_ROD);
        ItemMeta HideMeta = Hide.getItemMeta();
        HideMeta.setDisplayName("§6Spieler Verstecken");
        Hide.setItemMeta(HideMeta);

        ItemStack Settings = new ItemStack(Material.APPLE);
        ItemMeta SetMeta = Settings.getItemMeta();
        SetMeta.setDisplayName("§6Einstellungen");
        Settings.setItemMeta(SetMeta);

        p.getInventory().setItem(1, Navigator);

        p.getInventory().setItem(4, Settings);

        p.getInventory().setItem(7, Hide);

        p.updateInventory();
    }
}
