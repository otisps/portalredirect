package me.otisps.portalredirect.db;

import me.otisps.portalredirect.PortalRedirect;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.UUID;

public class Database {

    public Connection  getConnection(Plugin plugin) throws SQLException {
        if (PortalRedirect.getConnection() != null) {
            if (PortalRedirect.getConnection().isValid(1)) {
                return PortalRedirect.getConnection();
            }
        }
        final FileConfiguration config = plugin.getConfig(); // GET CONFIGS HOST
        Bukkit.getScheduler().runTaskAsynchronously(
                PortalRedirect.getInstance(), new Runnable() {
            @Override
            public void run() {
                String url = config.getString("wIslandInfo.database.url");
                String username = config.getString("wIslandInfo.database.username");// & LOGIN
                String password = config.getString("wIslandInfo.database.password");// DETAILS

                Connection newConnection = null;
                try {
                    newConnection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                PortalRedirect.setConnection(newConnection);
            }
        }
        );
        return PortalRedirect.getConnection();
    }

    public double getPoints(final UUID uuid) throws SQLException {
        PreparedStatement statement = getConnection(PortalRedirect.getInstance()).prepareStatement("SELECT score FROM scoreboard WHERE uuid = ?");
        statement.setString(1, uuid.toString()); // SETUP SQL GET QUERY
        ResultSet results =  statement.executeQuery(); // AND EXECUTE
        if(results.next()) {
            double score = results.getDouble("score"); // THEN CHECK SCORE
            return score;
        }
        return 0; // IF NOT FOUND MUST BE 0
    }
}
