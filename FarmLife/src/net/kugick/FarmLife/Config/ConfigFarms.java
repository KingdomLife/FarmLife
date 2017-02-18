package net.kugick.FarmLife.Config;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class ConfigFarms
{
	World farmWorld;
	
	public ConfigFarms(String farmWorld)
	{
		this.farmWorld = Bukkit.getWorld(farmWorld);
	}
	
	public World getWorld()
		{return farmWorld;}
	
}
