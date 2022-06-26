package me.otisps.portalredirect.effects;

import me.otisps.portalredirect.PortalRedirect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import javax.sound.sampled.Port;

public class LaunchEffect {
    /**
     * Throws a player up & back in the opposite direction to which they're looking
     * @param player desired target player
     */
    public void launchBackwards(Player player){
        
        player.setVelocity(new Vector(0, 1, 0));
        Vector eyeDirection = player.getLocation().getDirection();
        eyeDirection.setY(0);
        BukkitScheduler scheduler = PortalRedirect.getInstance().getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(PortalRedirect.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.setVelocity(eyeDirection.multiply(-1));
            }
        }, 5L);
    }

}
