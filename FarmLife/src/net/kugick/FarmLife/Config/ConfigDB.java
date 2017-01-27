package net.kugick.FarmLife.Config;

public class ConfigDB
{
	String ip, user, password, database, playertable, grandmarkettable;
	
	public ConfigDB(String ip, String user, String password, String database, String playertable, String grandmarkettable)
	{
		this.ip = ip;
		this.user = user;
		this.password = password;
		this.database = database;
		this.playertable = playertable;
		this.grandmarkettable = grandmarkettable;
	}
	
	public String getIp()
		{return ip;}
	public String getUser()
		{return user;}
	public String getPassword()
		{return password;}
	public String getDatabase()
		{return database;}
	public String getPlayertable()
		{return playertable;}
	public String getGrandmarkettable()
		{return grandmarkettable;}
}
