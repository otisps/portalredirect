package me.otisps.portalredirect.wg;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldGuardManager {
    /**
     * Finds a player and teleports it to the region where the player is from
     * If that player doesn't belong to a region, it'll go to a predefined location instead,
     * coming from config.yml
     * @param player Target Player
     */
    public void teleportPlayer(Player player) {
        if(hasWorldGuardRegion(player)){
            // findWorldGuardRegion(Player);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager manager = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
            for (ProtectedRegion region:
            manager.getRegions().values()) {
                if(region.getMembers().contains(player.getUniqueId()) || region.getOwners().contains(player.getUniqueId())){
                    // TO DO tp command
                }
            }
        } else {
            // TODO : get config location.
        }
    }

    // TODO : Get players' region

    public boolean hasWorldGuardRegion(Player player){
        // TODO: Does player belong to region
        return true;
    }
}
