package me.b3n3dkt.listener;

import me.b3n3dkt.Lobby;
import me.b3n3dkt.mechanics.StartInventory;
import me.b3n3dkt.mysql.MySQL;
import me.b3n3dkt.utils.Rang;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import me.b3n3dkt.utils.Score;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.lang.reflect.Field;

public class Join_Quit implements Listener {

    @EventHandler
    public void onHandle(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Score sb = new Score(player);
        event.setJoinMessage("§8[§a+§8] §7" + player.getName());
        if (MySQL.isRegistered(player.getUniqueId().toString()) != true) {
            MySQL.register(player);
        }

        for(int i = 0; i < 200; ++i) {
            if (!player.hasPermission("jailedmc.command.clearchat.bypass")) {
                player.sendMessage(" ");
            }
        }

        Lobby.getMain().sendCurrentPlayingGamemode(event.getPlayer(), true, "§b§lJailedMC.net");

        player.sendMessage(" ");
        player.sendMessage(" §7Wilkommen auf §bJailedmc.net");
        player.sendMessage(" §7Wir sind gerade in der Beta Testphase und manche Dinge funktunieren noch nicht so wie sie sollen " +
                "oder sind noch nicht ganz fertig.");
        player.sendMessage(" §7Es gibt auch Spielfehler die uns nicht auffalen, Melde diese Bugs bitte auf unserem Discord oder Teamspeak");
        player.sendMessage(" §7Viel Spaß auf §bJailedmc.net§7!");
        player.sendMessage(" ");

        player.setDisplayName(String.valueOf(Rang.getPrefix(player.getUniqueId().toString() + "§8×§7" + player.getName())));

        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setHealthScale(2.0D);
        player.setGameMode(GameMode.SURVIVAL);
        StartInventory.setStartInv(player);
        sb.setscoreboard();

        File file = new File("plugins//Lobby//spawns.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String w = cfg.getString("Spawn.World");
        double x = cfg.getDouble("Spawn.X");
        double y = cfg.getDouble("Spawn.Y");
        double z = cfg.getDouble("Spawn.Z");
        double yaw = cfg.getDouble("Spawn.Yaw");
        double pitch = cfg.getDouble("Spawn.Pitch");
        Location loc = new Location(Bukkit.getWorld(w), x, y, z);
        loc.setYaw((float) yaw);
        loc.setPitch((float) pitch);
        player.teleport(loc);

        Tab(player, "\n§8» §bJailedmc.net §8«\n\n §fBETA Release\n", "\n     §7TeamSpeak | §bjailedmc.net     \n§7Server | §b§oLobby\n");
    }

    @EventHandler
    public void onHandle(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Score sb = new Score(player);
        event.setQuitMessage("§8[§c-§8] §7" + event.getPlayer().getName());
        try {
            sb.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void Tab(Player p, String header, String footer) {
        if (header == null) header = "";
        if (footer == null) footer = "";

        PlayerConnection connection = (((CraftPlayer)p).getHandle()).playerConnection;

        IChatBaseComponent Title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent Foot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(Title);

        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, Foot);
        }
        catch (Exception e) {

            e.printStackTrace();
        } finally {

            connection.sendPacket(headerPacket);
        }
    }

}
