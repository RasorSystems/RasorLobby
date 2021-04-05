package me.b3n3dkt.commands;

import me.b3n3dkt.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Gamemode implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String l, String[] args) {
        if (s instanceof Player) {
            Player p = (Player)s;
            if (p.hasPermission("jailedmc.command.gamemode")) {
                if (args.length == 0) {
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cNutze /gamemode <0|1|2|3> <Spieler>!");
                } else if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("0")) {
                        p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §b0");
                        p.setGameMode(GameMode.SURVIVAL);
                    } else if (args[0].equalsIgnoreCase("1")) {
                        p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §b1");
                        p.setGameMode(GameMode.CREATIVE);
                    } else if (args[0].equalsIgnoreCase("2")) {
                        p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §b2");
                        p.setGameMode(GameMode.ADVENTURE);
                    } else if (args[0].equalsIgnoreCase("3")) {
                        p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §b3");
                        p.setGameMode(GameMode.SPECTATOR);
                    } else {
                        p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cNutze /gamemode <0|1|2|3> <Spieler>!");
                    }

                } else if (args.length == 2) {
                    if (!p.hasPermission("jailedrp.command.gamemode.other")) {
                        p.sendMessage(Lobby.getNoperm());
                        return true;
                    }
                    Player t = Bukkit.getPlayer(args[1]);
                    if (t != null) {
                        if (args[0].equalsIgnoreCase("0")) {
                            p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du hast §9" + args[1] + " §7Gamemode: §b0 §7gegeben!");
                            t.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §e0");
                            t.setGameMode(GameMode.SURVIVAL);
                        } else if (args[0].equalsIgnoreCase("1")) {
                            p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du hast §9" + args[1] + " §7Gamemode: §b1 §7gegeben!");
                            t.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §e1");
                            t.setGameMode(GameMode.CREATIVE);
                        } else if (args[0].equalsIgnoreCase("2")) {
                            p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du hast §9" + args[1] + " §7Gamemode: §b2 §7gegeben!");
                            t.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §e2");
                            t.setGameMode(GameMode.ADVENTURE);
                        } else if (args[0].equalsIgnoreCase("3")) {
                            p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du hast §9" + args[1] + " §7Gamemode: §b3 §7gegeben!");
                            t.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Du bist nun im Gamemode §b3");
                            t.setGameMode(GameMode.SPECTATOR);
                        } else {
                            p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cNutze /gamemode <0|1|2|3> <Spieler>!");
                        }
                    } else {
                        p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§7Der Spieler ist nicht Online!");
                    }
                } else {
                    p.sendMessage(String.valueOf(Lobby.getPrefix()) + "§cNutze /gamemode <0|1|2|3> <Spieler>!");
                }
            } else {
                p.sendMessage(Lobby.getNoperm());
            }
        }
        return false;
    }
}
