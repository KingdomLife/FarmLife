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

public class Menu implements CommandExecutor{
	
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

	//Main menu #Menu
	public static void openMenu(Player p){
		
		//Name the menu
		Inventory menu = Bukkit.createInventory(null, 45, "Quick Menu");
		
		//Fill the inventory with stained glass panes before adding items. ---> Stupid itemStack stuff to color...
		int slotNumber = 0;
		while(slotNumber != 54){
			ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
			ItemMeta paneMeta = glassPane.getItemMeta();
			paneMeta.setDisplayName("");
			paneMeta.setLore(Arrays.asList(""));
			glassPane.setItemMeta(paneMeta);
			menu.setItem(slotNumber++, glassPane);
		}
		
		//Play now
		menu.setItem(14, invItem(new ItemStack(Material.IRON_HOE), ChatColor.BLUE + "" + ChatColor.BOLD + "Play Now !", Arrays.asList("")));
		
		//Player profile
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(p.getName());
    	skullMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Profile");
    	skullMeta.setLore(Arrays.asList(ChatColor.GRAY + "Change your settings. Coming soon."));
        skull.setItemMeta(skullMeta);
        menu.setItem(30, skull);
		
        //Achievements
		menu.setItem(32, invItem(new ItemStack(Material.DIAMOND_ORE), ChatColor.BLUE + "" + ChatColor.BOLD + "Achievements", Arrays.asList(ChatColor.GRAY + "Track your progress as you unlock", ChatColor.GRAY + "achievements while playing FarmLife!", "", "ACHIEVEMENT_NUMBER", "", ChatColor.YELLOW + "> Click to view progress")));
		
		//Notifications
		menu.setItem(13, invItem(new ItemStack(Material.SLIME_BALL), ChatColor.BLUE + "" + ChatColor.BOLD + "Notifications", Arrays.asList(ChatColor.GRAY + "Keep track of what's going on in", ChatColor.GRAY + "the world of FarmLife, even offline!" + "" + "NOTIFICATION_NUMBER", "", ChatColor.YELLOW + "> Click to view notifications")));

		p.openInventory(menu);
	}
}