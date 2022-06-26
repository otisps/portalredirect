package me.otisps.portalredirect.commands;

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
        // TODO: Reload config
        // TODO: Message confirming ...
        return true;
    }
}
