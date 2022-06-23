package me.otisps.portalredirect.effects;

import org.bukkit.entity.Player;

public class LaunchEffect {
    public void launchBackwards(Player player){
        player.setVelocity(player.getVelocity().multiply(-2).setY(2));
    }

}
