package lucien.ConjoinedMinecraft.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import lucien.ConjoinedMinecraft.commands.ConjoinCommand;
import lucien.ConjoinedMinecraft.commands.InteractCommand;
import lucien.ConjoinedMinecraft.commands.MovementCommand;
import lucien.ConjoinedMinecraft.handlers.ConjoinedHandlers;
import lucien.ConjoinedMinecraft.handlers.HealthAndHungerSyncHandlers;
import lucien.ConjoinedMinecraft.handlers.InventorySyncHandlers;

public class Main extends JavaPlugin {
    public static Main plugin;
    public static boolean isConjoined = false;
    public static UUID movementController;
    public static UUID interactionController;
    
    @Override
    public void onEnable() {
	//Stores "this" for use in other classes
	plugin = this;

	//Sets up plugin functions
	registerCommands();
	registerHandlers();
    }
    
    private void registerCommands() {
	getCommand("movement").setExecutor(new MovementCommand());
	getCommand("interact").setExecutor(new InteractCommand());
	getCommand("conjoin").setExecutor(new ConjoinCommand());
    }
    
    private void registerHandlers() {
	PluginManager pluginManager = Bukkit.getPluginManager();
	pluginManager.registerEvents(new ConjoinedHandlers(), this);
	pluginManager.registerEvents(new InventorySyncHandlers(), this);
	pluginManager.registerEvents(new HealthAndHungerSyncHandlers(), this);
    }
}
