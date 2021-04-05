package me.b3n3dkt.listener;

import me.b3n3dkt.Lobby;
import me.b3n3dkt.utils.Rang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.util.Vector;

public class LobbyListener implements Listener{

    @EventHandler
    public void onAchieve(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onHandle(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onHandle(WeatherChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onHandle(PlayerCommandPreprocessEvent event){
        if(!event.isCancelled()){
            Player player = event.getPlayer();
            String msg = event.getMessage().split(" ")[0];
            HelpTopic topic = Bukkit.getHelpMap().getHelpTopic(msg);
            if(topic == null){
                player.sendMessage(Lobby.getPrefix() + "§cThe command §8'§e" + msg+ "§8' §cdoes not exist!");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.SURVIVAL && p.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType() != Material.AIR) {
            p.setAllowFlight(true);
            p.setFlying(false);
        }

    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setVelocity(p.getLocation().getDirection().multiply(2).add(new Vector(0.0D, 1.5D, 0.0D)));
        }

    }

    @EventHandler
    public void onAntiPlugins(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().equalsIgnoreCase("/bukkit") || e.getMessage().equalsIgnoreCase("/?") || e.getMessage().equalsIgnoreCase("/help") || e.getMessage().equalsIgnoreCase("/bukkit:help") || e.getMessage().equalsIgnoreCase("/reload") || e.getMessage().equalsIgnoreCase("/rl"))
            if (p.isOp()) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
                p.sendMessage(Lobby.getNoperm());
            }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE)
            return;  e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e instanceof org.bukkit.entity.Player)) {
            return;
        }
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            e.setCancelled(true);
    }

    @EventHandler
    public void onHandle(EntityDamageByEntityEvent e) { e.setCancelled(true); }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER && e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.SURVIVAL)
            return;  e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE)
            return;  e.setCancelled(true);
    }

    @EventHandler
    public void onHandle(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        e.setFormat(Rang.getSuffix(p.getUniqueId().toString()) + " §8× §7" + p.getName() + " §8→ §7" + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
    }

}
