package net.kugick.FarmLife.temp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener
{
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent e)
		{FLDatabase.createTempFile(e.getPlayer().getUniqueId());}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent e)
		{FLDatabase.deleteTempFile(e.getPlayer().getUniqueId());}
}
