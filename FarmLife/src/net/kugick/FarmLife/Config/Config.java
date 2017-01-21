package net.kugick.FarmLife.Config;

public class Config
{
	//Temporary config values, move to config file (maybe?)
	//TODO fill in the user and password
	public static ConfigDB DB = new ConfigDB("localhost", "SA", "", "FarmLife", "playerdata", "grandmarket");
	
	public static void reload()
	{
		//TODO reload from file (? ^)
		
		//DB = new ConfigSQL(file -> values);
	}
}
