package net.kugick.FarmLife;

import java.io.File;
import java.nio.file.Paths;

import net.kugick.FarmLife.WorldGenerator.FarmGenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmLife extends JavaPlugin
{
	private static FarmLife instance;
	
	public static FarmLife getInstance()
		{return instance;}
	public void onEnable()
	{
		instance = this;
		SQLHandler.init();
	}
	
	public void onDisable()
	{
		SQLHandler.destructor();
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		File farmSchematicFile = Paths.get(getDataFolder().toPath().toString(), "Farm.schematic").toFile();
		return farmSchematicFile == null ? null : new FarmGenerator(farmSchematicFile);
	}
}
