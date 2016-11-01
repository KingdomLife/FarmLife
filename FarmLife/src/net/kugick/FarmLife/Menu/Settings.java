package net.kugick.FarmLife.Menu;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

public class Settings implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args){
		if(args.length == 1){
			Player p = (Player)sender;
			if(args[0].equalsIgnoreCase("settings")){
				openSettings(p);
			}
			else p.sendMessage("Do /myfarm help to print the help.");
		}
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
	private void openSettings(Player p){
		Inventory menu = Bukkit.createInventory(null, 27, "MyFarm Settings");

		//Visibility
		Dye dye = new Dye(Material.INK_SACK);
		dye.setColor(DyeColor.GREEN);
		ItemStack coloredInkSack = dye.toItemStack();
		menu.setItem(10, invItem(new ItemStack(coloredInkSack), ChatColor.GREEN + "Visibility", Arrays.asList("a","b")));

		p.openInventory(menu);
	}
}