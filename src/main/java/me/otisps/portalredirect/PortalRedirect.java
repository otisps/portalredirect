package me.otisps.portalredirect;

import me.otisps.portalredirect.listeners.onPortalEnter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class PortalRedirect extends JavaPlugin {

    private static PortalRedirect instance;
    // key = player long = epoch time
    public static HashMap<UUID, Long> remainingCooldown  = new HashMap<>();;
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new onPortalEnter(), this);
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {

    }

    public static PortalRedirect getInstance() {
        return instance;
    }
}
