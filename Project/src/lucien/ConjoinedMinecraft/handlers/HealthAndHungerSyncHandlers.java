package lucien.ConjoinedMinecraft.handlers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import lucien.ConjoinedMinecraft.game.Main;

public class HealthAndHungerSyncHandlers implements Listener {
    @EventHandler
    public void handle(EntityDamageEvent event) {
	if(Main.isConjoined == true) {
	    if(event.getEntityType() == EntityType.PLAYER) {
		UUID damagedPlayer = event.getEntity().getUniqueId();
		Player interactController = Bukkit.getPlayer(Main.interactionController);
		Player movementController = Bukkit.getPlayer(Main.movementController);
		if(damagedPlayer.equals(Main.movementController)) {
		    if(movementController.getHealth() - event.getDamage() > 0)
			interactController.setHealth(movementController.getHealth() - event.getDamage());
		    else
			interactController.setHealth(0);
		}
		else if(damagedPlayer.equals(Main.interactionController)) {
		    event.setCancelled(true);
		}
	    }
	}
    }

    @EventHandler
    public void handle(EntityRegainHealthEvent event) {
	if(Main.isConjoined == true) {
	    if(event.getEntityType() == EntityType.PLAYER) {
		UUID damagedPlayer = event.getEntity().getUniqueId();
		Player interactController = Bukkit.getPlayer(Main.interactionController);
		Player movementController = Bukkit.getPlayer(Main.movementController);
		if(damagedPlayer.equals(Main.interactionController))
		    if(interactController.getHealth() + event.getAmount() < 20)
			movementController.setHealth(interactController.getHealth() + event.getAmount());
		    else
			movementController.setHealth(20);
		else if(damagedPlayer.equals(Main.movementController))
		    if(movementController.getHealth() + event.getAmount() < 20)
			interactController.setHealth(movementController.getHealth() + event.getAmount());
		    else
			interactController.setHealth(20);
	    }
	}
    }

    @EventHandler
    public void handle(FoodLevelChangeEvent event) {
	if(Main.isConjoined == true) {
	    if(event.getEntityType() == EntityType.PLAYER) {
		UUID playerWhoAte = event.getEntity().getUniqueId();
		if(playerWhoAte.equals(Main.interactionController) && event.getFoodLevel() > ((Player)event.getEntity()).getFoodLevel()) {
		    Player interactController = Bukkit.getPlayer(Main.interactionController);
		    Player movementController = Bukkit.getPlayer(Main.movementController);
		    movementController.setFoodLevel(event.getFoodLevel());
		    movementController.setSaturation(interactController.getSaturation());
		    movementController.setExhaustion(interactController.getExhaustion());
		}
	    }
	}
    }

    public static void syncHunger() {
	new BukkitRunnable() {
	    @Override
	    public void run() {
		Player interactController = Bukkit.getPlayer(Main.interactionController);
		Player movementController = Bukkit.getPlayer(Main.movementController);
		interactController.setFoodLevel(movementController.getFoodLevel());
		interactController.setSaturation(movementController.getSaturation());
		interactController.setExhaustion(movementController.getExhaustion());
	    }
	}.runTaskTimer(Main.plugin, 900l, 300l);
    }
}
