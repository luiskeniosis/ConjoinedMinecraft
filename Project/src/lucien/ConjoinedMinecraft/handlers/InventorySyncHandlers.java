package lucien.ConjoinedMinecraft.handlers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import lucien.ConjoinedMinecraft.game.Main;

public class InventorySyncHandlers implements Listener {
    @EventHandler
    public void handle(InventoryClickEvent event) {
	if(Main.isConjoined == true) {
	    HumanEntity entity = event.getWhoClicked();
	    if(entity != null && entity instanceof Player) {
		UUID playerWhoClicked = entity.getUniqueId();
		if(event.getInventory().getType() == InventoryType.CRAFTING) {
		    if(playerWhoClicked.equals(Main.interactionController))
			interactControllerInventoryChange();
		    else if(playerWhoClicked.equals(Main.movementController))
			movementControllerInventoryChange();
		}
		else {
		    if(playerWhoClicked.equals(Main.movementController))
			event.setCancelled(true);
		}
	    }
	}
    }

    @EventHandler
    public void handle(PlayerSwapHandItemsEvent event){
	if(Main.isConjoined == true) {
	    UUID player = event.getPlayer().getUniqueId();
	    if(player.equals(Main.interactionController))
		interactControllerInventoryChange();
	    else if(player.equals(Main.movementController))
		movementControllerInventoryChange();
	}
    }

    @EventHandler
    public void handle(InventoryDragEvent event) {
	if(Main.isConjoined == true) {
	    HumanEntity entity = event.getWhoClicked();
	    if(entity != null && entity instanceof Player) {
		UUID playerWhoClicked = entity.getUniqueId();
		if(event.getInventory().getType() == InventoryType.CRAFTING) {
		    if(playerWhoClicked.equals(Main.interactionController))
			interactControllerInventoryChange();
		    else if(playerWhoClicked.equals(Main.movementController))
			movementControllerInventoryChange();
		}
		else {
		    if(playerWhoClicked.equals(Main.movementController))
			event.setCancelled(true);
		}
	    }
	}
    }

    @EventHandler
    public void handle(EntityPickupItemEvent event) {
	if(Main.isConjoined == true) {
	    if(event.getEntity() != null && event.getEntityType() == EntityType.PLAYER) {
		UUID playerWhoClicked = event.getEntity().getUniqueId();
		if(playerWhoClicked.equals(Main.interactionController))
		    interactControllerInventoryChange();
		else if(playerWhoClicked.equals(Main.movementController))
		    movementControllerInventoryChange();
	    }
	}
    }

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
	if(Main.isConjoined == true) {
	    UUID player = event.getPlayer().getUniqueId();
	    if(player.equals(Main.interactionController))
		interactControllerInventoryChange();
	    else if(player.equals(Main.movementController))
		movementControllerInventoryChange();
	}
    }

    @EventHandler
    public void handle(PlayerItemConsumeEvent event) {
	if(Main.isConjoined == true) {
	    UUID player = event.getPlayer().getUniqueId();
	    if(player.equals(Main.interactionController))
		interactControllerInventoryChange();
	    else if(player.equals(Main.movementController))
		movementControllerInventoryChange();
	}
    }

    /*@EventHandler
    public void handle(InventoryOpenEvent event) {
	if(Main.isConjoined == true) {
	    Player movementController = Bukkit.getPlayer(Main.movementController);
	    if(!event.getViewers().contains(movementController) && event.getPlayer().getUniqueId().equals(Main.interactionController)) {
		new BukkitRunnable() {
		    @Override
		    public void run() {
			event.getViewers().add(movementController);
			movementController.openInventory(event.getView());
		    }
		}.runTaskLater(Main.plugin, 5);
	    }
	}
    }

    @EventHandler
    public void handle(InventoryCloseEvent event) {
	Bukkit.broadcastMessage(event.getPlayer().getName());
	HumanEntity player = event.getPlayer();
	if(player.getUniqueId().equals(Main.interactionController)) {
	    new BukkitRunnable() {
		@Override
		public void run() {
		    try {
			for(HumanEntity viewer : event.getViewers()) {
			    if(viewer.getOpenInventory().equals(event.getView()))
				viewer.closeInventory();
			}
		    }
		    catch (ConcurrentModificationException exception){
		    }
		}
	    }.runTaskLater(Main.plugin, 5);
	}
    }
    */

    private void movementControllerInventoryChange() {
	Player movementController = Bukkit.getPlayer(Main.movementController);
	Player interactController = Bukkit.getPlayer(Main.interactionController);
	new BukkitRunnable() {
	    int tick = 10;
	    @Override
	    public void run() {
		if(tick == 5) {
		    movementController.updateInventory();
		    tick--;
		}
		else if(tick == 0) {
		    interactController.getInventory().setContents(movementController.getInventory().getContents());
		    tick--;
		}
		else if(tick == -1) {
		    interactController.updateInventory();
		    this.cancel();
		}
		else
		    tick--;

	    }
	}.runTaskTimer(Main.plugin, 0l, 1);
    }

    private void interactControllerInventoryChange() {
	Player movementController = Bukkit.getPlayer(Main.movementController);
	Player interactController = Bukkit.getPlayer(Main.interactionController);
	new BukkitRunnable() {
	    int tick = 10;
	    @Override
	    public void run() {
		if(tick == 5) {
		    interactController.updateInventory();
		    tick--;
		}
		else if(tick == 0) {
		    movementController.getInventory().setContents(interactController.getInventory().getContents());
		    tick--;
		}
		else if(tick == -1) {
		    movementController.updateInventory();
		    this.cancel();
		}
		else
		    tick--;

	    }
	}.runTaskTimer(Main.plugin, 0l, 1);
    }
}
