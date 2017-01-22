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

public class Achievements implements CommandExecutor{
	
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

	//Main menu #Achievements
	public static void openMenu(Player p){
		
		//Name the menu
		Inventory menu = Bukkit.createInventory(null, 54, "Achievements");

		//Fill the inventory with stained glass panes before adding items.
		int index = 0;
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);/// Seb - declaring a variable once and using it instead of creating new ones every iteration
		ItemMeta paneMeta = item.getItemMeta();
		paneMeta.setDisplayName("");
		paneMeta.setLore(Arrays.asList(""));
		item.setItemMeta(paneMeta);
		while(index != 27)
			menu.setItem(index++, glassPane);

		//Make a blank space.
		index = 10;
		item = new ItemStack(Material.AIR);/// Seb - reusing already declared variables, no need for new ones
		/**ItemMeta airMeta = air.getItemMeta();
		airMeta.setDisplayName("");
		airMeta.setLore(Arrays.asList(""));
		air.setItemMeta(airMeta);*/
		while(index != 35)
			menu.setItem(index++, item);

		//Informations
		menu.setItem(5, invItem(new ItemStack(Material.DIAMOND_ORE), ChatColor.BLUE + "Achievements", Arrays.asList(ChatColor.GRAY + "Hover the icons to view")));
		
		//Return to menu
		menu.setItem(45, invItem(new ItemStack(Material.COMPASS), ChatColor.RED + "Exit to Menu", null));

		//TODO DISPLAY ACHIEVEMENTS

		p.openInventory(menu);

	}
}
