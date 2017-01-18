package net.kugick.FarmLife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import net.kugick.FarmLife.Config.Config;
import net.kugick.FarmLife.Util.Utils;

public class SQLHandler
{
	private static Connection con = null;
	private static PreparedStatement GET, SET;
	
	public static void init()
	{
		try
		{
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:mysql://" + Config.getData(Config.DataType.db_ip) + "?user=" + Utils.wrap(Config.getData(Config.DataType.db_user), "\"") + "&password=" + Utils.wrap(Config.getData(Config.DataType.db_password), "\""));
			con.setAutoCommit(true);
			Statement st = con.createStatement();
			st.executeQuery("USE " + Config.getData(Config.DataType.db_database));
			st.close();
			
			GET = con.prepareStatement("SELECT ? FROM " + Config.getData(Config.DataType.db_table) + " WHERE UUID = ?");
			SET = con.prepareStatement("UPDATE " + Config.getData(Config.DataType.db_table) + " SET ? = ? WHERE UUID = ?");
		}
		catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public static boolean setValue(UUID uuid, String column, String value)
	{
		try
		{
			SET.setString(1, column);
			SET.setString(2, value);
			SET.setString(3, uuid.toString());
			SET.executeQuery();
			return true;
		}
		catch (SQLException e) {e.printStackTrace();}
		
		return false;
	}
	
	public static String getValue(UUID uuid, String column)
	{
		try
		{
			GET.setString(1, column);
			GET.setString(2, uuid.toString());
			ResultSet rs = GET.executeQuery();
			return rs.first() ? rs.getString(0) : null;
		}
		catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static void destructor() throws NullPointerException
	{
		try
		{
			GET.close();
			SET.close();
			con.close();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
}
