package me.otisps.portalredirect.effects;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LaunchEffect {
    /**
     * Throws a player up & back in the opposite direction to which they're looking
     * @param player desired target player
     */
    public void launchBackwards(Player player){
        player.setVelocity(player.getVelocity().subtract(new Vector(player.getVelocity().getX() * 2, player.getVelocity().getY() - 1.5, player.getVelocity().getZ() * 2).multiply(1.1)));





    }

}
