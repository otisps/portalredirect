package me.otisps.portalredirect.listeners;

import me.otisps.portalredirect.PortalRedirect;
import me.otisps.portalredirect.db.Database;
import me.otisps.portalredirect.effects.LaunchEffect;
import me.otisps.portalredirect.wg.WorldGuardManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.sql.SQLException;

public class onPortalEnter implements Listener {

    @EventHandler
    public void toEndEvent(PlayerPortalEvent event){
        final Player player = event.getPlayer(); // Player teleporting
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){ // IF END PORTAL
            if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) { // & GOING FROM NORMAL WWORLD
                Database database = new Database(); // THEN OPEN THE DATABASE CONNECTION
                Double points; // AND FIND OUT
                try {
                    points = database.getPoints(player.getUniqueId()); // HOW MANY POINTS
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (points > PortalRedirect.getInstance().getConfig().getDouble("settings.points-end")) {
                    return;
                } // AND CANCEL IF NOT ENOUGH
                event.setCancelled(true);
                if (PortalRedirect.remainingCooldown.containsKey(player.getUniqueId())) {
                    if (System.currentTimeMillis() - PortalRedirect.remainingCooldown.get(player.getUniqueId()) < 2000) {
                        return;
                    } //  LAUNCH THEM BACK & TELL THEM THEY NEED MORE POINTS
                }
                player.sendMessage("You need more points to teleport."); // TODO CONFIG
                LaunchEffect launchEffect = new LaunchEffect();
                launchEffect.launchBackwards(player); // TODO MAKE GOOD
                // WE START A COOLDOWN SO WE DONT HAVE WEIRD DOUBLE LAUNCHES IF ITS LAGGY
                PortalRedirect.remainingCooldown.put(player.getUniqueId(), System.currentTimeMillis());
                return;
            }
        }
    }
    @EventHandler
    public void netherPortalEvent(PlayerPortalEvent event){
        Player player = event.getPlayer();
        World.Environment environment = player.getWorld().getEnvironment();  // FIND OUT
        PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){ // IF NETHER PORTAL
            if(environment.equals(World.Environment.NORMAL)){ // AND IF NORMAL -> NETHER
                return;
            }
            if (environment.equals(World.Environment.NETHER)){ // OR IF NETHER -> NORMAL
                event.setCancelled(true); // IF NETHER -> NORMAL CANCEL
                WorldGuardManager worldGuardManager = new WorldGuardManager();
                worldGuardManager.teleportPlayer(player); // AS WE TELEPORT THEM OURSELVES
                return;
            }
        }
    }


    @EventHandler
    public void exitEndEvent(EntityPortalEnterEvent event){ // IF END -> NORMAL
        if(event.getEntity().getWorld().getEnvironment().equals(World.Environment.THE_END)){
            Player player = Bukkit.getPlayer(event.getEntity().getUniqueId());
            final WorldGuardManager worldGuardManager = new WorldGuardManager();
            worldGuardManager.teleportPlayer(player); // AS WE TELEPORT THEM OURSELVES
            return;
        }
    }
}
