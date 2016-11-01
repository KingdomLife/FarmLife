package net.kugick.FarmLife;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.kugick.FarmLife.Game.MyFarm;
import net.kugick.FarmLife.Menu.Menu;

public class Main extends JavaPlugin {
	
	Logger log = Bukkit.getLogger();
	
	public void onEnable(){
		log.info("[FarmLife] Plugin enabled.");
		//Register Listeners
		
		//Register Commands
		//MyFarm game
		getCommand("myfarm").setExecutor(new MyFarm());
		//Settings menu
		//getCommand("myfarm").setExecutor(new Settings());
		//Menu
		getCommand("menu").setExecutor(new Menu());
		
		//Server chat
		//getCommand("mute").setExecutor(new Mute());
	}
	
	public void onDisable(){
		log.info("[FarmLife] Plugin disabled.");
	}
	
	public static Main getInstance(){
		if ((Bukkit.getServer().getPluginManager().getPlugin("FarmLife") instanceof Main)) {
			return (Main)Bukkit.getServer().getPluginManager().getPlugin("FarmLife");
		}
		return null;
	}

}
