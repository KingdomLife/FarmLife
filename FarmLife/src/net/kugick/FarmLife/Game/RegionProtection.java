package net.kugick.FarmLife.Game;

import java.util.HashMap;
import java.util.Map;

import net.kugick.FarmLife.Config.Config;

import org.bukkit.Bukkit;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

public class RegionProtection
{
	private static final WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	private static final RegionManager mgr = wg.getRegionManager(Config.farms.getWorld());
	
	public static boolean exists(int chunkX, int chunkZ)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		return mgr.hasRegion(id);
	}
	public static boolean createRegion(int chunkX, int chunkZ)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (mgr.hasRegion(id)) return false;
		
		ProtectedCuboidRegion reg = new ProtectedCuboidRegion(id, new BlockVector(chunkX*16, 255, chunkZ*16), new BlockVector((chunkX+2)*16, 0, (chunkZ+2)*16));
		Map<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();
		flags.put(DefaultFlag.BLOCK_BREAK, StateFlag.State.DENY);
		flags.put(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
		flags.put(DefaultFlag.CHEST_ACCESS, StateFlag.State.DENY);
		flags.put(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, StateFlag.State.DENY);
		flags.put(DefaultFlag.ENTITY_PAINTING_DESTROY, StateFlag.State.DENY);
		flags.put(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
		
		//move to global region:
		flags.put(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.DENY);
		flags.put(DefaultFlag.DAMAGE_ANIMALS, StateFlag.State.DENY);
		flags.put(DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE, StateFlag.State.DENY);
		flags.put(DefaultFlag.ENDER_BUILD, StateFlag.State.DENY);
		flags.put(DefaultFlag.ENDERPEARL, StateFlag.State.DENY);
		flags.put(DefaultFlag.GHAST_FIREBALL, StateFlag.State.DENY);
		flags.put(DefaultFlag.GRASS_SPREAD, StateFlag.State.DENY);
		flags.put(DefaultFlag.ICE_FORM, StateFlag.State.DENY);
		flags.put(DefaultFlag.ICE_MELT, StateFlag.State.DENY);
		flags.put(DefaultFlag.ITEM_DROP, StateFlag.State.DENY);
		flags.put(DefaultFlag.ITEM_PICKUP, StateFlag.State.DENY);
		flags.put(DefaultFlag.LEAF_DECAY, StateFlag.State.DENY);
		flags.put(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		flags.put(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
		flags.put(DefaultFlag.PVP, StateFlag.State.DENY);
		flags.put(DefaultFlag.TNT, StateFlag.State.DENY);
		
		reg.setFlags(flags);
		mgr.addRegion(reg);
		
		return true;
	}
	public static boolean removeRegion(int chunkX, int chunkZ)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.removeRegion(id);
		
		return true;
	}
	
	public static boolean hasOwner(int chunkX, int chunkZ, String nick)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.getRegion(id).getOwners().contains(nick);
		
		return true;
	}
	public static boolean addOwner(int chunkX, int chunkZ, String nick)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.getRegion(id).getOwners().addPlayer(nick);
		
		return true;
	}
	public static boolean removeOwner(int chunkX, int chunkZ, String nick)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.getRegion(id).getOwners().removePlayer(nick);
		
		return true;
	}
	
	public static boolean hasMember(int chunkX, int chunkZ, String nick)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.getRegion(id).getMembers().contains(nick);
		
		return true;
	}
	public static boolean addMember(int chunkX, int chunkZ, String nick)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.getRegion(id).getMembers().addPlayer(nick);
		
		return true;
	}
	public static boolean removeMember(int chunkX, int chunkZ, String nick)
	{
		String id = getRegionID(chunkX, chunkZ);
		
		if (!mgr.hasRegion(id)) return false;
		
		mgr.getRegion(id).getMembers().removePlayer(nick);
		
		return true;
	}
	
	public static String getRegionID(int chunkX, int chunkZ)
		{return "farm" + chunkX + "_" + chunkZ;}
}
