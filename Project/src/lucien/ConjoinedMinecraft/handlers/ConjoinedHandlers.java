package lucien.ConjoinedMinecraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import lucien.ConjoinedMinecraft.game.Main;

public class ConjoinedHandlers implements Listener {
    @EventHandler
    public void handle(PlayerMoveEvent event) {
	if(Main.isConjoined == true) {
	    Player playerMoving = event.getPlayer();
	    if(playerMoving.getUniqueId().equals(Main.movementController)) {
		Player interactController = Bukkit.getPlayer(Main.interactionController);
		if(!interactController.isDead())
		    interactController.teleport(playerMoving.getLocation());
	    }
	    else
		event.setCancelled(true);
	}
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
	if(Main.isConjoined == true && event.getPlayer().getUniqueId().equals(Main.movementController))
	    event.setCancelled(true);
    }

    /*@EventHandler
    public void handle(EntityTargetLivingEntityEvent event) {
	if(Main.isConjoined == true && event.getTarget().getUniqueId().equals(Main.interactionController))
	    event.setTarget(Bukkit.getPlayer(Main.movementController));
    }*/

    @EventHandler
    public void handle(EntityToggleSwimEvent event) {
	if(Main.isConjoined == true && event.getEntityType() == EntityType.PLAYER && event.getEntity().getUniqueId().equals(Main.movementController)) {
	    Bukkit.getPlayer(Main.interactionController).setSwimming(true);
	}
    }
    
    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
	if(Main.isConjoined == true && event.getDamager().getType() == EntityType.PLAYER && event.getDamager().getUniqueId().equals(Main.movementController)) {
	    event.setCancelled(true);
	}
    }
}