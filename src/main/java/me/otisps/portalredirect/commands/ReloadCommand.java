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
            return true;
        }
        PortalRedirect.getInstance().reloadConfig();
        sender.sendMessage("You just reloaded config!");
        return true;
    }
}
