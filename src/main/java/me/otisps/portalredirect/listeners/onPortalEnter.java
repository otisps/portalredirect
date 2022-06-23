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
    public void onPortal(PlayerPortalEvent event){
        Player player = event.getPlayer();
        World.Environment environment = player.getWorld().getEnvironment();
        PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();
        switch(teleportCause){
            case END_GATEWAY:
                player.sendMessage("cool");
                break;
            case END_PORTAL:
                switch(environment){
                    case NORMAL:
                        Database database = new Database();
                        Double points;
                        try {
                            points = database.getPoints(player.getUniqueId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        if (points > PortalRedirect.getInstance().getConfig().getDouble("wIslandInfo.settings.points-end")){
                            return;
                        }
                        event.setCancelled(true);
                        if(PortalRedirect.remainingCooldown.containsKey(player.getUniqueId())){
                            if(System.currentTimeMillis() -  PortalRedirect.remainingCooldown.get(player.getUniqueId()) < 20000 ){
                                return;
                            }
                        }
                        player.sendMessage("You need more points.");
                        LaunchEffect launchEffect = new LaunchEffect();
                        launchEffect.launchBackwards(player);
                        PortalRedirect.remainingCooldown.put(player.getUniqueId(), System.currentTimeMillis());
                        return;
                }
                break;
            case NETHER_PORTAL:
                switch(environment){
                    case NETHER:
                        // TODO : a check is made to see if they are a WG region owner or member ETC
                        player.sendMessage("nether to normal");
                        break;
                    case NORMAL:
                        player.sendMessage("normal to nether");
                        break;
                }
                break;
        }
    }

    @EventHandler
    public void endToOverworld(EntityPortalEnterEvent event){
        if(event.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end")){
            Player player = Bukkit.getPlayer(event.getEntity().getUniqueId());
            player.sendMessage("omg");
            WorldGuardManager wgManager = new WorldGuardManager();
            if(wgManager.hasWorldGuardRegion(player)){
                // todo tp to region
                return;
            }
            // todo get place
            // todo tp to that place
        }
    }
}
