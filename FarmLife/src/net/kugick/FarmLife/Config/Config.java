package net.kugick.FarmLife.Config;

public class Config
{
	//Temporary config values, move to config file (maybe?)
	//btw, this kind of setup is annoying, needs to be changed
	//TODO fill in the user and password
	public static enum DataType {db_user, db_password, db_ip, db_database, db_table};
	
	private final static String db_user = "test", db_password = "test", db_ip = "127.0.0.1",
						db_table = "playerdata", db_database = "FarmLife";
	public static String getData(DataType type)
	{
		switch (type)
		{
			case db_user:
				return db_user;
			case db_password:
				return db_password;
			case db_ip:
				return db_ip;
			case db_database:
				return db_database;
			case db_table:
				return db_table;
			default: return null;
		}
	}
	
}
