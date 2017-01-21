package net.kugick.FarmLife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import net.kugick.FarmLife.Config.Config;
import net.kugick.FarmLife.Util.FarmLogger;

public class SQLHandler//TODO change to Singleton and retry db connection if error occurs (maybe?)
{
	private static Connection con = null;
	private static Statement st;
	
	public static boolean addPlayer(UUID uuid, int xp, int level, int money)
	{
		try {st.executeQuery("INSERT INTO " + Config.DB.getPlayertable() + " VALUES (" + uuid.toString() + ", " + xp + ", " + level + ", " + money + ")"); return true;}
		catch (SQLException e) {e.printStackTrace();}
		
		return false;
	}
	public static boolean removePlayer(UUID uuid)
	{
		try {st.executeQuery("DELETE FROM " + Config.DB.getPlayertable() + " WHERE UUID = " + uuid.toString()); return true;}
		catch (SQLException e) {e.printStackTrace();}
		
		return false;
	}
	public static boolean setValue(UUID uuid, String column, String value)
	{
		try {st.executeQuery("UPDATE " + Config.DB.getPlayertable() + " SET " + column + " = " + value + " WHERE UUID = " + uuid.toString()); return true;}
		catch (SQLException exc) {exc.printStackTrace();}
		catch (NullPointerException exc) {FarmLogger.warning2("The connection to database wasn't established. Please reload the plugin.");}
		
		return false;
	}
	public static String getValue(UUID uuid, String column)
	{
		try
		{
			ResultSet rs = st.executeQuery("SELECT " + column + " FROM " + Config.DB.getPlayertable() + " WHERE UUID = " + uuid.toString());
			return rs.next() ? rs.getString(0) : null;
		}
		catch (SQLException e) {e.printStackTrace();}
		catch (NullPointerException exc) {FarmLogger.warning2("The connection to database wasn't established. Please reload the plugin.");}
		
		return null;
	}
	
	public static void init()
	{
		try
		{
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			//con = DriverManager.getConnection("jdbc:mysql://" + Config.DB.getIp() + "?user=" + Utils.wrap(Config.DB.getUser(), "\"") + "&password=" + Utils.wrap(Config.DB.getPassword(), "\""));
			con = DriverManager.getConnection("jdbc:mysql://" + Config.DB.getIp(), Config.DB.getUser(), Config.DB.getPassword());
			con.setAutoCommit(true);
			st = con.createStatement();
			st.executeQuery("USE " + Config.DB.getDatabase()).close();
		}
		catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}
	}
	public static void destructor() throws NullPointerException
	{
		try
		{
			st.close();
			con.close();
		}
		catch (SQLException | NullPointerException exc) {exc.printStackTrace();}
	}
	
	public static void createTables()
	{
		//TODO (maybe?)
	}
}
