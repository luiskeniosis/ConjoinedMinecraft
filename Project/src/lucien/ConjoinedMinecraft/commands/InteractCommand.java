package lucien.ConjoinedMinecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lucien.ConjoinedMinecraft.game.Main;

public class InteractCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
	if(arguments.length == 1) {
	    Player player = Bukkit.getPlayer(arguments[0]);
	    if(player != null) {
		Main.interactionController = player.getUniqueId();
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() +
			" &eas the &finteraction &econtroller."));
		if(sender instanceof Player && !((Player)sender).getName().equals(arguments[0]))
		    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &finteraction &econtroller."));
	    }
	    else
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
	}
	else
	    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cInvalid arguments!"));
	return false;
    }
}
