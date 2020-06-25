package lucien.ConjoinedMinecraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lucien.ConjoinedMinecraft.game.Main;
import lucien.ConjoinedMinecraft.handlers.HealthAndHungerSyncHandlers;

public class ConjoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
	if(arguments.length == 0) {
	    if(Main.movementController != null && Main.interactionController != null) {
		Main.isConjoined = true;
		for(Player player : Bukkit.getOnlinePlayers()) {
		    player.setBedSpawnLocation(player.getLocation(), true);
		    player.setHealth(20.0);
		    player.setFoodLevel(20);
		    player.setCollidable(false);
		}
		Player interactController = Bukkit.getPlayer(Main.interactionController);
		Player movementController = Bukkit.getPlayer(Main.movementController);
		interactController.hidePlayer(Main.plugin, movementController);
		movementController.hidePlayer(Main.plugin, interactController);
		HealthAndHungerSyncHandlers.syncHunger();
	    }
	    else {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cBoth Move and Interact controllers must be assigned before conjoining!"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &f/movement <player_name> &eand &f/interact <player_name> &eto assign controllers."));
	    }
	}
	else
	    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cInvalid arguments!"));
	return false;
    }
}
