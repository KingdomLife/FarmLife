package net.kugick.FarmLife;

import java.io.File;
import java.util.logging.Logger;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.CuboidClipboard;

import net.kugick.FarmLife.Game.MyFarm;
import net.kugick.FarmLife.Generation.CGen;
import net.kugick.FarmLife.Menu.Menu;

public class Main extends JavaPlugin{
	
	Logger log = Bukkit.getLogger();
	
	public void onEnable(){
		log.info("[FarmLife] Plugin enabled.");
	}
	
	public void onDisable(){
		log.info("[FarmLife] Plugin disabled.");
	}
	
	private String path = new File("").getAbsolutePath() + "/plugins/WorldEdit/schematics/";

	  public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
	    File file = new File(this.path + "plot.schematic");
	    CuboidClipboard cc = null;
	    try {
	      cc = CuboidClipboard.loadSchematic(file);
	    } catch (Exception e) {
	      getLogger().info(ExceptionUtils.getStackTrace(e));
	    }

	    return new CGen(cc, this);
	  }
	  
	public static Main getInstance(){
		if ((Bukkit.getServer().getPluginManager().getPlugin("FarmLife") instanceof Main)){
			return (Main)Bukkit.getServer().getPluginManager().getPlugin("FarmLife");
		}
		return null;
	}

}
