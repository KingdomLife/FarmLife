package net.kugick.FarmLife.Menu;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Menu implements CommandExecutor {
	//Command
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player p = (Player)sender;
		openMenu(p);
		return false;
	}
	//Create the item & meta
	public ItemStack invItem(ItemStack stack, String name, List<String> lore){
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	//Menu
	private void openMenu(Player p){
		//We create the menu
		Inventory menu = Bukkit.createInventory(null, 36, "FarmLife Menu");

		//Player info's
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(p.getName());
    	skullMeta.setDisplayName(ChatColor.GREEN + "My progress - Level Null");
    	skullMeta.setLore(Arrays.asList(ChatColor.GRAY + "Selling goods in the market and", ChatColor.GRAY + "achievements are ways to earn", ChatColor.GRAY + "experience wich is required to", ChatColor.GRAY + "level up & unlock new cool", ChatColor.GRAY + "features!", "", ChatColor.DARK_GRAY + "[" + ChatColor.RESET + "llllllllllllllllllllllllllllllllllllllll" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " 0%", "", ChatColor.GREEN + "» " + ChatColor.GRAY + "Experience Needed: 0", "", ChatColor.YELLOW + "Click to view multipliers!"));
        skull.setItemMeta(skullMeta);
        menu.setItem(9, skull);
		//Market place
		menu.setItem(11, invItem(new ItemStack(Material.GOLD_NUGGET), ChatColor.GREEN + "Marketplace", Arrays.asList(ChatColor.GRAY + "Buy and sell crops, animals and", ChatColor.GRAY + "trees to return a profit!", "", ChatColor.YELLOW + "Click to open the shop!")));
		//Business
		menu.setItem(13, invItem(new ItemStack(Material.EMERALD), ChatColor.GREEN + "Business & Co.", Arrays.asList("a","b")));
		//Achievements
		menu.setItem(15, invItem(new ItemStack(Material.DIAMOND), ChatColor.GREEN + "Leaderboards", Arrays.asList("a","b")));
		//MyFarm
		menu.setItem(18, invItem(new ItemStack(Material.BED), ChatColor.GREEN + "MyFarm", Arrays.asList("a","b")));
		//Leaderboards
		menu.setItem(21, invItem(new ItemStack(Material.PAPER), ChatColor.GREEN + "Leaderboards", Arrays.asList("a","b")));
		//Post
		menu.setItem(23, invItem(new ItemStack(Material.BOOK), ChatColor.GREEN + "Post Office", Arrays.asList("a","b")));
		//Boosters
		menu.setItem(25, invItem(new ItemStack(Material.POTION), ChatColor.GREEN + "My Boosters", Arrays.asList("a","b")));

		p.openInventory(menu);
	}
}