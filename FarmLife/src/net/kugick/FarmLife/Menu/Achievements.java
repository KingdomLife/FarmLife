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
		int glassNumber = 0;
		while(glassNumber != 27){
			ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
			ItemMeta paneMeta = glassPane.getItemMeta();
			paneMeta.setDisplayName("");
			paneMeta.setLore(Arrays.asList(""));
			glassPane.setItemMeta(paneMeta);
			menu.setItem(glassNumber++, glassPane);
		}

		//Make a blank space.
		int airNumber = 10;
		while(airNumber != 35){
			ItemStack air = new ItemStack(Material.AIR);
			/**ItemMeta airMeta = air.getItemMeta();
			airMeta.setDisplayName("");
			airMeta.setLore(Arrays.asList(""));
			air.setItemMeta(airMeta);*/
			menu.setItem(airNumber++, air);
		}

		//Informations
		menu.setItem(5, invItem(new ItemStack(Material.DIAMOND_ORE), ChatColor.BLUE + "Achievements", Arrays.asList(ChatColor.GRAY + "Hover the icons to view")));
		
		//Return to menu
		menu.setItem(45, invItem(new ItemStack(Material.COMPASS), ChatColor.RED + "Exit to Menu", null));

		//TODO DISPLAY ACHIEVEMENTS

		p.openInventory(menu);

	}
}
