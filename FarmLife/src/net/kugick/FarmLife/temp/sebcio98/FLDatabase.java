package net.kugick.FarmLife.temp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FLDatabase
{
	public static void createTempFile(UUID uuid)
	{
		File f = Utils.getTempFilePath(uuid).toFile();
		if (f.exists()) f.delete();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PrintWriter pw = null;
		
		try
		{
			f.createNewFile();
			pw = new PrintWriter(f);
			
			con = DriverManager.getConnection("jdbc:mysql://" + Config.getData("db_ip") + "?user=" + Config.getData("db_user") + "&password=" + Config.getData("db_password"));
			st = con.createStatement();
			st.executeQuery("USE " + Config.getData("db_database"));
			rs = st.executeQuery("select level,xp,money from " + Config.getData("db_table") + " where uuid=" + uuid);
			
			rs.next(); pw.println("level: " + rs.getInt(0));
			rs.next(); pw.println("xp: " + rs.getInt(1));
			rs.next(); pw.println("money: " + rs.getInt(2)); 
		}
		catch (IOException | SQLException e) {e.printStackTrace();}
		finally
		{
			try
			{
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
				if (pw != null) pw.close();
			}
			catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	public static boolean deleteTempFile(UUID uuid)
	{
		Path path = Utils.getTempFilePath(uuid);
		if (!path.toFile().exists()) return true;
		Connection con = null;
		Statement st = null;
		
		try
		{
			Map<String, String> values = Utils.inputSplitter(Files.readAllLines(path), ": ");
			
			con = DriverManager.getConnection("jdbc:mysql://" + Config.getData("db_ip") + "?user=" + Config.getData("db_user") + "&password=" + Config.getData("db_password"));
			st = con.createStatement();
			st.executeQuery("USE " + Config.getData("db_database"));
			st.executeQuery("UPDATE " + Config.getData("db_table") + " SET level=" + values.get("level") + ",xp=" + values.get("xp") + ",money=" + values.get("money") + " WHERE UUID=" + uuid);
			
			path.toFile().delete();
			return true;
		}
		catch (SQLException | IOException exc) {System.out.println("Could not connect to MySQL database"); exc.printStackTrace(); return false;}
		finally
		{
			try
			{
				if (st != null) st.close();
				if (con != null) con.close();
			}
			catch (SQLException exc) {exc.printStackTrace();}
		}
	}
	
	/**
	 * 
	 * @param uuid - UUID of a player whom data is to be obtained.
	 * @param values - A Set of value types which are to be read. (HashSet is fine)
	 * @return Map<DB_valType, Integer> - A Map of value types associated with read values.
	 */
	public static Map<DB_valType, Integer> getValues(UUID uuid, Set<DB_valType> values)
	{
		Map<DB_valType, Integer> opt = new HashMap<DB_valType, Integer>();
		
		try
		{
			for (String line : Files.readAllLines(Utils.getTempFilePath(uuid), StandardCharsets.UTF_8))
			{
				DB_valType currType = DB_valType.valueOf(line.split(": ")[0]);
				if (values.contains(currType)) opt.put(currType, Integer.valueOf(line.split(": ")[1]));
			}
		}
		catch (IOException exc) {exc.printStackTrace();}
		
		return opt;
	}
	/**
	 * 
	 * @param uuid - UUID of a player whom data is to be obtained.
	 * @param values - A Map of value types with values which are to be set. (HashMap is fine)
	 * @return boolean - true if action successful
	 */
	public static boolean setValues(UUID uuid, Map<DB_valType, Integer> values)
	{
		try
		{
			List<String> lines = Files.readAllLines(Utils.getTempFilePath(uuid), StandardCharsets.UTF_8);
			ListIterator<String> it = lines.listIterator();
			
			while (it.hasNext())
			{
				DB_valType currType = DB_valType.valueOf(it.next().split(": ")[0]);
				if (values.containsKey(currType)) it.set(currType + ": " + values.get(currType));
			}
			
			Files.write(Utils.getTempFilePath(uuid), lines, StandardCharsets.UTF_8);
			return true;
		}
		catch (IOException e) {e.printStackTrace(); return false;}
	}
	
	public static boolean purgePlayer(UUID uuid)
	{
		
		return false;
	}
}
