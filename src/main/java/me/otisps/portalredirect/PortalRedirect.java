package me.otisps.portalredirect;

import me.otisps.portalredirect.commands.ReloadCommand;
import me.otisps.portalredirect.listeners.onPortalEnter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

public final class PortalRedirect extends JavaPlugin {

    private static PortalRedirect instance;
    private static Connection connection;

    // key = player long = epoch time
    public static HashMap<UUID, Long> remainingCooldown  = new HashMap<>();;
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new onPortalEnter(), this);
        saveDefaultConfig();
        getCommand("portalredirectreload").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {

    }

    public static PortalRedirect getInstance() {
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        PortalRedirect.connection = connection;
    }
}
