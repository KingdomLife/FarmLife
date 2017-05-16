package net.kugick.FarmLife.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.PluginManager;

import net.kugick.FarmLife.FarmLife;

public class Utils {
	public static final String _21 = "§";
	private static PluginManager pluginManager = Bukkit.getServer().getPluginManager();
	
	//////////////////
	// STRING UTILS //
	//////////////////
	public static String coloroze(String s) {
		return s.replaceAll("&", _21);
	}
	public static String getStringFromArray(String[] array, int startIndex, String separator) {
		String opt = array[startIndex++];
		
		while (startIndex<array.length)
			opt += separator + array[startIndex++];
		
		return opt;
	}
	public static List<String> stringDivider(String s, String seperator) {
		return stringDivider(s, seperator, 32);
	}
	public static List<String> stringDivider(String s, String seperator, int linelength) {
		List<String> lines = new ArrayList<String>();
		if (s == null) return lines;
		int i=-1, count=linelength+1;
		
		for (String ss : s.split(seperator)) {
			count+=ss.length()+seperator.length();
			if (count > linelength) {
				lines.add(ss); i++; count=ss.length();
			}
			else {
				lines.set(i, lines.get(i) + seperator + ss);
			}
		}
		
		return lines;
	}
	
	//////////////
	// NEW ITEM //
	//////////////
	/**
	 * 
	 * examples:
	 *   create a new item:
	 *     ItemStack item = Utils.newItem(Material.WOOL, 10, "Cool Wool", "This is a block of wool~for cool people.", (byte)15);
	 *   edit an item:
	 *     item = Utils.newItem(item, null, -1, null, null, (byte)5); //changed wool color to green leaving previous material, amount, name and lore
	 * 
	 * @param material default: null
	 * @param count default: -1
	 * @param name default: null
	 * @param lore default: null
	 * @return A fresh ItemStack.
	 */
	public static ItemStack newItem(Material material, int count, String name, String lore) {
		return newItem(material, count, name, lore, (byte)0);
	}
	public static ItemStack newItem(Material material, int count, String name, String lore, int color) {
		return newItem(material, count, name, lore, color, -1, -1);
	}
	public static ItemStack newItem(Material material, int count, String name, String lore, int red, int green, int blue) {
		return newItem(material, count, name.replaceAll("&", _21), lore=="" ? null : Arrays.asList(lore.replaceAll("&", _21).split("~")), red, green, blue);
	}
	public static ItemStack newItem(Material material, int count, String name, List<String> lore, int red, int green, int blue) {
		ItemStack item;
		if (count < 0) count = 1;
		if (green < 0 && blue < 0) item = new ItemStack(material, count, (byte)red);
		else item = new ItemStack(material, count);
		
		ItemMeta meta = item.getItemMeta();
		if (name != null) meta.setDisplayName(name);
		if (lore != null) meta.setLore(lore);
		if (meta instanceof LeatherArmorMeta && red >= 0 && green >= 0 && blue >= 0) {
			((LeatherArmorMeta)meta).setDisplayName(name);
			((LeatherArmorMeta)meta).setColor(Color.fromRGB(red, green, blue));
		}
		item.setItemMeta(meta);
		
		return item;
	}
	public static ItemStack newItem(ItemStack item, Material material, int count, String name, String lore, short color) {
		return newItem(item, material, count, name==null||name==""?null:name.replaceAll("&", _21), lore=="" ? null : Arrays.asList(lore.replaceAll("&", _21).split("~")), color);
	}
	@SuppressWarnings("deprecation")
	public static ItemStack newItem(ItemStack item, Material material, int count, String name, List<String> lore, short color) {
		ItemStack newItem;
		if (color > 0) newItem = new ItemStack(material==null ? item.getType() : material, count<0 ? item.getAmount() : count, (byte)color);
		else newItem = new ItemStack(material==null ? item.getType() : material, count<0 ? item.getAmount() : count, item.getData().getData());
		ItemMeta meta = item.getItemMeta();
		if (name != null) meta.setDisplayName(name);
		if (lore != null) meta.setLore(lore);
		newItem.setItemMeta(meta);
		
		return newItem;
	}
	
	////////////////////
	// PLUGIN MANAGER //
	////////////////////
	public static PluginManager getPluginManager() {
		return pluginManager;
	}
	public static void registerListener(Listener l) {
		getPluginManager().registerEvents(l, FarmLife.getInstance());
	}
	public static void unregisterListener(Listener l) {
		HandlerList.unregisterAll(l);
	}
	
	//////////////////
	// PACKET UTILS //
	//////////////////
	private static Object respawnPacket = null;
	
	public static void sendRespawnPacket(Player p) throws Exception {
		Object nmsPlayer = Player.class.getMethod("getHandle").invoke(p);
		
		if (respawnPacket == null) {
			respawnPacket = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
			
			Class<?> enumClasses = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
			
			for(Object ob : enumClasses.getEnumConstants()) {
				if(ob.toString().equals("PERFORM_RESPAWN")) {
					respawnPacket = respawnPacket.getClass().getConstructor(enumClasses).newInstance(ob);
				}
			}
		}

		Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
		con.getClass().getMethod("a", respawnPacket.getClass()).invoke(con, respawnPacket);
	}
}
