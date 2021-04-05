package me.b3n3dkt.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import me.b3n3dkt.Lobby;
import me.b3n3dkt.utils.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MySQL {

    private static ServerConfig scfg;
    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static Connection con;

    public static void connect() {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getMain(), new Runnable()
        {
            public void run()
            {
                if (!MySQL.isConnected()) {
                    try {
                        long start = System.currentTimeMillis();
                        MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.host + ":" + MySQL.port + "/" + MySQL.database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&&autoReconnect=true", MySQL.username, MySQL.password);
                        Update("CREATE TABLE IF NOT EXISTS Playerdata(Spielername VARCHAR(16), UUID  VARCHAR(100), Coins VARCHAR(100), Verwarnungen VARCHAR(100), Rang VARCHAR(100))");
                        Update("CREATE TABLE IF NOT EXISTS TäglicheBelohnungen(Spielername VARCHAR(100), UUID VARCHAR(100), Ende VARCHAR(100))");
                        Update("CREATE TABLE IF NOT EXISTS WöchentlicheBelohnungen(Spielername VARCHAR(100), UUID VARCHAR(100), Ende VARCHAR(100))");
                        Update("CREATE TABLE IF NOT EXISTS MonatlicheBelohnungen(Spielername VARCHAR(100), UUID VARCHAR(100), Ende VARCHAR(100))");
                        long end = System.currentTimeMillis();
                        System.out.println("Connection to MySQL server established! (" + host + ":" + port + ")");
                        System.out.println("Connection took " + ((end - start)) + "ms!");
                    } catch (SQLException e1) {
                        System.out.println("Could not connect to MySQL server! because: " + e1.getMessage());
                    }
                }
            }
        });
    }

    public static void close() {
        if (MySQL.isConnected()) {
            try {
                MySQL.con.close();
                Bukkit.getConsoleSender().sendMessage(Lobby.getPrefix() + "§cMySQL: Connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() { return (con != null); }

    public static void update(final String qry) {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby.getMain(), new Runnable()
        {
            public void run()
            {
                if (MySQL.isConnected()) {
                    try {
                        MySQL.con.createStatement().executeUpdate(qry);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Bukkit.getScheduler().runTaskLater(Lobby.getMain(), new Runnable()
                            {
                                public void run()
                                {
                                    if (!MySQL.isConnected()) {
                                        MySQL.connect();
                                    }
                                }
                            },
                            60L);
                }
            }
        });
    }

    public static ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getScheduler().runTaskLater(Lobby.getMain(), new Runnable()
                    {
                        public void run()
                        {
                            if (!MySQL.isConnected()) {
                                MySQL.connect();
                            }
                        }
                    },
                    60L);
        }
        return null;
    }


    public static Connection getConnection() { return con; }

    public static void Update(String qry) {
        if(MySQL.isConnected()){
            try {
                Statement st = con.createStatement();
                st.executeUpdate(qry);
                st.close();
            } catch (SQLException var3) {
                Bukkit.getConsoleSender().sendMessage("§cMySQL: Error while updating ->" + var3.getMessage());
                var3.printStackTrace();
            }
        } else {
            Bukkit.getScheduler().runTaskLater(Lobby.getMain(), new Runnable()
                    {
                        public void run()
                        {
                            if (!MySQL.isConnected()) {
                                MySQL.connect();
                                Update(qry);
                            }
                        }
                    },
                    60L);
        }

    }

    public static void register(Player p) {
        try {
            PreparedStatement pr = getStatement("INSERT INTO Playerdata (Spielername, UUID, Rang, Coins, Verwarnungen) VALUES (?, ?, ?, ?, ?)");
            pr.setString(1, p.getName());
            pr.setString(2, p.getUniqueId().toString());
            pr.setString(3, "spieler");
            pr.setLong(4, 1000L);
            pr.setInt(5, 0);
            pr.executeUpdate();
            pr.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public static PreparedStatement getStatement(String sql) {
        if (MySQL.isConnected()) {
            try {
                return (PreparedStatement) con.prepareStatement(sql);
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }else{
            Bukkit.getScheduler().runTaskLater(Lobby.getMain(), new Runnable()
                    {
                        public void run()
                        {
                            if (!MySQL.isConnected()) {
                                MySQL.connect();
                                getStatement(sql);
                            }
                        }
                    },
                    60L);
        }

        return null;
    }

    public static boolean isRegistered(String uuid) {
        try (PreparedStatement ps = getStatement("SELECT Rang FROM Playerdata WHERE UUID= ?")) {
            ps.setString(1, uuid.toString());
            if(ps.executeQuery().next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public static long getcoins(String uuid) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Playerdata WHERE UUID= ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long points = rs.getLong("Coins");
            rs.close();
            ps.close();
            return points;
        } catch (Exception var5) {
            var5.printStackTrace();
            return -1L;
        }
    }

    public static void setcoins(String uuid, double coins) {
        try {
            PreparedStatement ps = getStatement("UPDATE Playerdata SET Coins= ? WHERE UUID= ?");
            ps.setDouble(1, coins);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static boolean hasEnoughMoney(String uuid, double amount){
        if(getcoins(uuid) >= amount || getcoins(uuid) == amount){
            return true;
        }else{
            return false;
        }
    }

    public static String getRang(String uuid) {
        try (PreparedStatement ps = getStatement("SELECT Rang FROM Playerdata WHERE UUID= ?")) {
            ps.setString(1, uuid);
            if(ps.executeQuery().next()) {
                return ps.getResultSet().getString("Rang");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "null";
    }


    public static void setRang(String uuid, String Rang) {
        try {
            PreparedStatement ps = getStatement("UPDATE Playerdata SET Rang= ? WHERE UUID= ?");
            ps.setString(1, Rang);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static String getClannameFromUser(UUID user) {
        try (PreparedStatement ps = getStatement("SELECT * FROM clanmembers WHERE UUID= ?")) {
            ps.setString(1, user.toString());
            if(ps.executeQuery().next()) {
                return ps.getResultSet().getString("clanname");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Clanlos";
    }

}
