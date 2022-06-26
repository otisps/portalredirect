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
        Player player = event.getPlayer();
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){ // IF END PORTAL
            if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) { // & GOING FROM NORMAL WORLD
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
                    if (System.currentTimeMillis() - PortalRedirect.remainingCooldown.get(player.getUniqueId()) < PortalRedirect.getInstance().getConfig().getLong("settings.rebound-cooldown")) {
                        return;
                    } //  LAUNCH THEM BACK & TELL THEM THEY NEED MORE POINTS
                }
                player.sendMessage(PortalRedirect.getInstance().getConfig().getString("messages.missing-points"));

                LaunchEffect launchEffect = new LaunchEffect();
                launchEffect.launchBackwards(player);

                // THEN START A COOLDOWN SO WE DON'T HAVE WEIRD DOUBLE LAUNCHES IF ITS LAGGY
                PortalRedirect.remainingCooldown.put(player.getUniqueId(), System.currentTimeMillis());
                return;
            }
        }
    }
    @EventHandler
    public void netherPortalEvent(PlayerPortalEvent event){
        Player player = event.getPlayer();
        World.Environment environment = player.getWorld().getEnvironment();  // FIND OUT
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){ // IF NETHER PORTAL
            if(environment.equals(World.Environment.NORMAL)){ // AND IF NORMAL -> NETHER
                return;
            }
            if (environment.equals(World.Environment.NETHER)){ // OR IF NETHER -> NORMAL
                event.setCancelled(true); // IF NETHER -> NORMAL CANCEL
                WorldGuardManager worldGuardManager = new WorldGuardManager();
                worldGuardManager.teleportPlayer(player); // TELEPORT THEM OURSELVES
                return;
            }
        }
    }


    @EventHandler
    public void exitEndEvent(EntityPortalEnterEvent event){ // IF END -> NORMAL
        if(event.getEntity().getWorld().getEnvironment().equals(World.Environment.THE_END)){
            Player player = Bukkit.getPlayer(event.getEntity().getUniqueId());
            WorldGuardManager worldGuardManager = new WorldGuardManager();
            worldGuardManager.teleportPlayer(player); // TELEPORT THEM OURSELVES
            return;
        }
    }

}
