package net.kugick.FarmLife.Menu;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Play implements CommandExecutor{
	
	//Command
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player p = (Player)sender;
		openMenu(p);
		return false;
	}
	
	//Create the item & meta
	private static ItemStack invItem(ItemStack stack, String name, List<String> lore){
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	//Main menu #Play
	public static void openMenu(Player p){
		
		//Name the menu
		Inventory menu = Bukkit.createInventory(null, 27, "Start Playing");

		//Fill the inventory with stained glass panes before adding items. ---> Stupid itemStack stuff to color...
		int slotNumber = 0;
		while(slotNumber != 27){
			ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
			ItemMeta paneMeta = glassPane.getItemMeta();
			paneMeta.setDisplayName("");
			paneMeta.setLore(Arrays.asList(""));
			glassPane.setItemMeta(paneMeta);
			menu.setItem(slotNumber++, glassPane);
		}

		//Create Farm
		menu.setItem(32, invItem(new ItemStack(Material.WORKBENCH), ChatColor.BLUE + "" + ChatColor.BOLD + "Create Farm", Arrays.asList(ChatColor.GRAY + "Start your own farm!", "", ChatColor.YELLOW + "> Click to start!")));

		//Join Farm
		menu.setItem(13, invItem(new ItemStack(Material.SKULL_ITEM), ChatColor.BLUE + "" + ChatColor.BOLD + "Join Farm", Arrays.asList(ChatColor.GRAY + "Join an existing farm", "", ChatColor.YELLOW + "> Click to browse!")));

		p.openInventory(menu);
	}
}