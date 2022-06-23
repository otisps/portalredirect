package me.otisps.portalredirect.db;

import me.otisps.portalredirect.PortalRedirect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.UUID;

public class Database {

    // TODO : KEEP CONNECTION OPEN

    public Connection  getConnection(Plugin plugin) throws SQLException {
        FileConfiguration config = plugin.getConfig();
        String url = config.getString("wIslandInfo.database.url");
        String username = config.getString("wIslandInfo.database.username");
        String password = config.getString("wIslandInfo.database.password");

        Connection newConnection = DriverManager.getConnection(url, username, password);
        return newConnection;
    }

    public double getPoints(UUID uuid) throws SQLException {
        PreparedStatement statement = getConnection(PortalRedirect.getInstance()).prepareStatement("SELECT score FROM scoreboard WHERE uuid = ?");
        statement.setString(1, uuid.toString());
        ResultSet results =  statement.executeQuery();
        if(results.next()) {
            double score = results.getDouble("score");
            return score;
        }
        return 0;
    }
}
