package me.b3n3dkt;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.gson.JsonObject;
import eu.skyrasor.entity.NpcEntity;
import me.b3n3dkt.commands.*;
import me.b3n3dkt.listener.InventoryClick;
import me.b3n3dkt.listener.Join_Quit;
import me.b3n3dkt.listener.LobbyListener;
import me.b3n3dkt.listener.NPCInteract;
import me.b3n3dkt.mysql.MySQL;
import me.b3n3dkt.navigator.Compass;
import me.b3n3dkt.navigator.Locations;
import me.b3n3dkt.settings.Settings;
import me.b3n3dkt.utils.LabyModProtocol;
import me.b3n3dkt.utils.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Lobby extends JavaPlugin{

    private ServerConfig scfg = new ServerConfig();
    private static Lobby main;
    private static String prefix;
    private ProtocolManager protocolmanager;
    private static String noperm;

    @Override
    public void onEnable(){
        if(scfg.exist() == false){
            scfg.newConfig();
        }
        try {
            loadUtils();
        } catch (IOException e) {}
        MySQL.connect();
        register(); this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getConsoleSender().sendMessage("§aLobby System started!");

        (new NpcEntity(new Location(Bukkit.getWorld("world"), -628.5D, 20.0D, 692.5D), "eyJ0aW1lc3RhbXAiOjE1ODY3MTU2MDIwMjMsInByb2ZpbGVJZCI6IjczZDRlMDY4M2E2ZDRjOGI4Zjg1MzMyMzU0Njk1NWM0IiwicHJvZmlsZU5hbWUiOiJNSEZfQ2hlc3QiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc1ODMwNWU1MjdmY2YwODUyNWZkZmYwN2U0Y2Q2ZTc5Nzc0YWNkZDQyNmNjMjczMjdkNDVkZjhhNWZmODY0MTkifX19", "U1VjGwv8dwjqvcWoQNVJE8pqxIJT2BvmK1X4nrJG2esX1+yL6M7GgCLUAVkkCla50k5IDlrulRQzODpKfWteORbH1ap3JRJUh3Cja3nFJIjNYcd+QsXwSXjkzYoqQ/yfpHiLJZqAmEVyNV5D4NgYjoqioGw7/D3O4pHCKBHeuIgH2q9u7eW11C9Yv24svlKL0OoB4R+WqLl6qEXx6Thq4MS04cwFpvkXKODxAZGPhw0ok8Pf0TioVNtC5qJTC7czggZZ3ivQXRfaKrIH/BY/RqW9BBQ2CcBxMLIyJejEPo1w9gcWHr1HlRwUpBx2wOr3FYqjSJLHP+tZofS8W2oYR+pS4jP3MMe+XcpHwf6gcadsfbOLb9IQ3oHmso+A5xHp9/mfT5EcRLhAHn2FehBzCyG1PkwiA4KBe8OIPCI/UK/bUqt67sHj3mJ6QerY8KqtiRlgKLWH/epvlH/0JmpuIVunZ+4pVCibA4N27JZropQclm3n9IWHJFdkEwrCKPJ4I7kDpl+iA0p8BUrMoyZFWpqsrqCuwEHhAdniFFEMWKEMGVmCxGRj/r8VYsBKWRLr0gf6Irqt3qIQyigNNW4FRBGxBPMsFU5RqrULILNe/pxhy4/udhnggydQUelBU3NXYmwrYRtBVL9ZiiWwHyfH7XGheNKmh63tqh2RSr0/DFw=")).init("§6§lBelohnungen");
        (new NpcEntity(new Location(Bukkit.getWorld("world"), -620.5D, 20.0D, 700.5D), "eyJ0aW1lc3RhbXAiOjE1NTIzMjcxNTM2MTMsInByb2ZpbGVJZCI6Ijg1MmI4ZGZkYjZiMzQ4MWNiYmYwMTM3YjM2YmFlN2JmIiwicHJvZmlsZU5hbWUiOiJaMkk3TzJLOExHNjciLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWEzNjEyZDY5OWMxYzg2YmExMjAzY2MxM2UzY2I2MjI5OWVmMWI4ZTYzYzZjZDA1ZTk0OGU4M2Y3YzNjZGM1NyJ9fX0=", "e7eIA9Msnhwuh3p9PigJg2FKA4n6o9kDd/9pWNqCntm+8lEGFWWBmvaHhURuRs1vZvsxWFIMdTwdwKeGJ7XW4exhiPDffblx9odt3JyyGdAZG7sc8Owv2yG9q1ZB1UUyMICd7z9WWhoFsntYnHdrw4XGncjfEaR/AYaE8pMS5fVDzggyf3w4ffYOqmeKJEzt7XBcBovJKM1ZDRnyWO6O/LM6i/YJnKbuyGQHAAhP7Qtx1OD7sAPXyd7POMhfqK/EVnLOCFsTc6NP/wXNmUFjmAY96/eD5nl9ckuCUYl7kWClUb9oRJSC52SyI5P+YyK5KbWPVFRqK4uqNqOG4+5KTSWb0av3MzFxVOJhuOEtDzCmJhsSKhVXMUD7jPN+nQ5f0QJJm+xoqJZa4iPLots3L6/qOwcW5FHL5xlsObVor7C4vMuaDNb1Ii7dyoUknjdShuC/iiwMohob5NzYK1Wd+TCSki91juzNVPjGR9/kORXE9aMZtbbGWRrrzAbaauFDHdgdgFrLofqywKJer68xkQisfnalqYvKrOFKSYXYGYtMqsDwvtu+VY1upBqZjQsL30Fz7JHp9iyf+m3b4qExrwlB0vBbZ3uZIHb4tclivqfKxym7ESNJOSVlgFn/5J+8IRkk16vYwj+rkFXMFZhMRMvXl6dL3mXj4HCcsptXoXU=")).init("§6§lRang-Shop");

    }

    @Override
    public void onDisable(){
        MySQL.close();
        Bukkit.getConsoleSender().sendMessage("§aLobby System stopped!");
    }

    public void register(){
        getCommand("fly").setExecutor(new Fly());
        getCommand("gamemode").setExecutor(new Gamemode());
        getCommand("money").setExecutor(new Money());
        getCommand("eco").setExecutor(new Eco());
        getCommand("rang").setExecutor(new Rang());
        getCommand("pay").setExecutor(new Pay());

        Bukkit.getPluginManager().registerEvents(new Join_Quit(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new Compass(), this);
        Bukkit.getPluginManager().registerEvents(new Locations(), this);
        Bukkit.getPluginManager().registerEvents(new Settings(), this);
        Bukkit.getPluginManager().registerEvents(new NPCInteract(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
    }

    public void loadUtils() throws IOException{
        prefix = scfg.getPrefix();
        noperm = scfg.getNoPerms();
        main = this;
        MySQL.username = scfg.getUsername();
        MySQL.password = scfg.getPassword();
        MySQL.database = scfg.getDatabase();
        MySQL.host = scfg.getHost();
        MySQL.port = scfg.getPort();

    }

    private void loadProtocolLib() {
        this.protocolmanager = ProtocolLibrary.getProtocolManager();
        this.protocolmanager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, new PacketType[]{Server.TAB_COMPLETE}) {
            public void onPacketSending(PacketEvent e) {
                if (e.getPacketType() == Server.TAB_COMPLETE && !e.getPlayer().isOp()) {
                    e.setCancelled(true);
                }

            }
        });
    }

    public void sendCurrentPlayingGamemode(Player receiver, boolean visible, String gamemodeName) {
        JsonObject object = new JsonObject();
        object.addProperty("show_gamemode", visible);
        object.addProperty("gamemode_name", gamemodeName);
        LabyModProtocol.sendLMCMessage(receiver, "server_gamemode", object);
    }

    public static String getPrefix(){ return prefix; }

    public static String getNoperm(){ return noperm; }

    public static Lobby getMain(){ return main; }

}
