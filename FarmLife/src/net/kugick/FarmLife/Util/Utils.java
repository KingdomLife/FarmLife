package net.kugick.FarmLife.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils
{
	/// #### String ####
	public static String wrap(String inp, String paper)
		{return paper + inp + paper;}
	public static String getStringFromArray(String[] array, int startIndex, String separator)
	{
		String opt = array[startIndex++];
		
		while (startIndex<array.length)
			opt += separator + array[startIndex++];
		
		return opt;
	}
	public static List<String> stringDivider(String s)
	{
		List<String> lines = new ArrayList<String>();
		if (s == null) return lines;
		int i=-1, count=33;
		
		for(String ss : s.split(" "))
		{
			count+=ss.length()+1;
			if (count > 32) {lines.add(ss); i++; count=ss.length();}
			else {lines.set(i, lines.get(i) + " " + ss);}
		}
		
		return lines;
	}
	
	/// #### ItemStack ####
	public static ItemStack newItem(Material material, int count, String name, String lore)
		{return newItem(material, count, name, lore, (byte)0);}
	public static ItemStack newItem(Material material, int count, String name, String lore, byte color)
		{return newItem(material, count, name.replaceAll("&", "§"), lore=="" ? null : Arrays.asList(lore.replaceAll("&", "§").split("~")), color);}
	public static ItemStack newItem(Material material, int count, String name, List<String> lore, byte color)
	{
		ItemStack item;
		if (count < 0) count = 1;
		if (color != 0) item = new ItemStack(material, count, color);
		else item = new ItemStack(material, count);
		
		ItemMeta meta = item.getItemMeta();
		if (name != null) meta.setDisplayName(name);
		if (lore != null) meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	public static ItemStack newItem(ItemStack item, Material material, int count, String name, String lore, short color)
		{return newItem(item, material, count, name==null||name==""?null:name.replaceAll("&", "§"), lore=="" ? null : Arrays.asList(lore.replaceAll("&", "§").split("~")), color);}
	@SuppressWarnings("deprecation")
	public static ItemStack newItem(ItemStack item, Material material, int count, String name, List<String> lore, short color)
	{
		ItemStack newItem;
		if (color > 0) newItem = new ItemStack(material==null ? item.getType() : material, count<0 ? item.getAmount() : count, (byte)color);
		else newItem = new ItemStack(material==null ? item.getType() : material, count<0 ? item.getAmount() : count, item.getData().getData());
		ItemMeta meta = item.getItemMeta();
		if (name != null) meta.setDisplayName(name);
		if (lore != null) meta.setLore(lore);
		newItem.setItemMeta(meta);
		
		return newItem;
	}
}
