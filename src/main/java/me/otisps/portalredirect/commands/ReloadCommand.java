package me.otisps.portalredirect.commands;

import me.otisps.portalredirect.PortalRedirect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(!sender.isOp()){
                return true;
            }
        }
        PortalRedirect.getInstance().reloadConfig();
        return true;
    }
}
