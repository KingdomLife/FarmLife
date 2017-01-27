package net.kugick.FarmLife.Event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener{
	//Game chat, display staff/public chat, player level...

	//Game chat

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		org.bukkit.entity.Player p = e.getPlayer();

		e.setCancelled(true);

		String prefix = null;
		String level = null;

		for(org.bukkit.entity.Player online : Bukkit.getOnlinePlayers()){
			//is he in staff
			if(p.hasPermission("FarmLife.staff")){
				e.setMessage("<"+prefix+"> " + p.getName() + ": " + e.getMessage());
			}
			//normal player
			else{
				//Just default preset of the chat, still need to do colors for ranks
				e.setMessage("["+level+"] " + p.getName() + ": " + e.getMessage());
			}
		}
	}
}

