package net.kugick.FarmLife.temp;

public class Config
{
	//Temporary config values, move to config file
	//TODO fill in the user and password
	final static String db_user = "test", db_password = "test", db_ip = "127.0.0.1", db_database = "FarmLife", db_table = "playerdata",
						files_player = "[UUID]/game.yml";
	public static String getData(String id)
	{
		switch (id)
		{
			case "db_user":
				return db_user;
			case "db_password":
				return db_password;
			case "db_ip":
				return db_ip;
			case "db_database":
				return db_database;
			case "db_table":
				return db_table;
			case "files_player":
				return files_player;
			default: return null;
		}
	}
	
}
