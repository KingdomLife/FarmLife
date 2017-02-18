package net.kugick.FarmLife.Config;

public class Config
{
	//Temporary config values, move to config file (maybe?)
	//TODO fill in the user and password
	public static final ConfigDB DB = new ConfigDB("localhost", "SA", "", "FarmLife", "playerdata", "grandmarket");
	public static final ConfigFarms farms = new ConfigFarms("farmWorld");
	
	public static void reload()
	{
		//TODO reload from file (? ^)
		
		//DB = new ConfigSQL(file -> values);
	}
}
