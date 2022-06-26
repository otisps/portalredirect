package me.otisps.portalredirect.wg;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.otisps.portalredirect.PortalRedirect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WorldGuardManager {
    /**
     * Finds a player and teleports it to the region where the player is from
     * If that player doesn't belong to a region, it'll go to a predefined location instead,
     * coming from config.yml
     * @param player Target Player
     */
    public void teleportPlayer(Player player) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
        for (ProtectedRegion region: manager.getRegions().values()) { // IF INSIDE THE LIST OF REGIONS
            if(region.getMembers().contains(player.getUniqueId()) || region.getOwners().contains(player.getUniqueId())){
                String command = "rg tp -w world " + region.getId();
                Boolean wasOp = false;
                if(player.isOp()) {
                    wasOp = true;
                }
                try{
                    player.setOp(true);
                    player.performCommand(command);
                } finally {
                    if(!wasOp) {
                        player.setOp(false);
                    }
                }
                if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                    return;
                }
                }
            }
        // OTHERWISE THEY DONT HAVE A REGION!
        // SO TELEPORT RANDOMLY!
        randomTeleportPlayer(player);
        }

    public void randomTeleportPlayer(Player player){
        final FileConfiguration config = PortalRedirect.getInstance().getConfig();
        int num = 1;
        while(config.contains("random-locations." +num+ "x")){
            num++;
        }
        int random = (int) (Math.random() * num + 1);
        String path = "random-locations." + random;
        double x_value = config.getDouble(path + ".x");
        double y_value = config.getDouble(path + ".y");
        double z_value = config.getDouble(path + ".z");
        player.teleport(new Location(Bukkit.getWorld(config.getString("settings.world-name")), x_value, y_value , z_value));
    }
}
