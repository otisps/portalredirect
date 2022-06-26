package me.otisps.portalredirect.db;

import me.otisps.portalredirect.PortalRedirect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class Database {

    public Connection  getConnection(Plugin plugin) throws SQLException {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin("wIslandInfo").getDataFolder(), "scoreboard.db");
        Connection newConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            newConnection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return newConnection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPoints(final UUID uuid) throws SQLException {
        PreparedStatement statement = getConnection(PortalRedirect.getInstance()).prepareStatement("SELECT score FROM 'scoreboard' WHERE uuid = ?");
        statement.setString(1, uuid.toString()); // SETUP SQL GET QUERY
        ResultSet results =  statement.executeQuery(); // AND EXECUTE
        if(results.next()) {
            double score = results.getDouble("score"); // THEN CHECK SCORE
            statement.close();
            return score;
        }
        statement.close();
        return 0; // IF NOT FOUND MUST BE 0
    }
}
