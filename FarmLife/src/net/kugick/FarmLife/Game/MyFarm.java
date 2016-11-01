package net.kugick.FarmLife.Game;

import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.util.DomainInputResolver;
import com.sk89q.worldguard.protection.util.DomainInputResolver.UserLocatorPolicy;
import com.sk89q.worldguard.util.profile.resolver.ProfileService;

public class MyFarm implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {


		//if(args.length == 0){
			
			//TODO CHECK IF THE PLAYER ALREADY HAVE A MYFARM
			
			
			//Get the sender
			Player p = (Player)sender;

			//TODO KNOW AND GET COORDINATES FOR REGION CREATION
			int regionSize = 20;
			BlockVector min = new BlockVector(p.getLocation().getX() - regionSize, 0.0D, p.getLocation().getZ() - regionSize);
			BlockVector max = new BlockVector(p.getLocation().getX() + regionSize, 255.0D, p.getLocation().getZ() + regionSize);


			//Region informations
			//final ProtectedRegion region = new ProtectedCuboidRegion("myfarm-" + p.getUniqueId(), min, max);
			RegionContainer container = getWorldGuard().getRegionContainer();
			RegionManager regions = container.get(p.getWorld());
			ProtectedRegion myfarm = new ProtectedCuboidRegion("myfarm-" + p.getUniqueId(), min, max);
			//ProtectedRegion border = new ProtectedCuboidRegion("myfarm-path-" + p.getUniqueId(), min, max);
			//ProtectedRegion house = new ProtectedCuboidRegion("myfarm-house-" + p.getUniqueId(), min, max);
			ApplicableRegionSet regionSet = regions.getApplicableRegions(myfarm);

			if (regionSet.size() == 0){

				//Google's Guava library
				ListeningExecutorService executor =
						MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

				//Get the player UUID from Mojang
				String[] input = new String[] { p.getName() };
				ProfileService profiles = getWorldGuard().getProfileService();
				DomainInputResolver resolver = new DomainInputResolver(profiles, input);
				//UUID_AND_NAME
				resolver.setLocatorPolicy(UserLocatorPolicy.UUID_ONLY);
				ListenableFuture<DefaultDomain> future = executor.submit(resolver);

				//Add a callback using Guava
				Futures.addCallback(future, new FutureCallback<DefaultDomain>() {
					@Override
					public void onSuccess(DefaultDomain result) {
						myfarm.getOwners().addAll(result);
					}

					@Override
					public void onFailure(Throwable throwable) {
						p.sendMessage(ChatColor.RED + "An error occured please contact a staff member.");
						Bukkit.getLogger().severe("[MyFarm] An error occured while adding a owner to a worldguard (myfarm) region. There is maybe an action required.");
					}

				});

				//Add the regions
				regions.addRegion(myfarm);
				//regions.addRegion(border);
				//regions.addRegion(house);
			}
			return false;
		}
		/*else if(args.length == 1){
			try{
				//TODO
				
				//Check if the myfarm is set to private
				
				//Teleport the player to the myfarm
			}
			catch(NullPointerException ex){
				ex.printStackTrace();
			}
		}
		else sender.sendMessage("/myfarm <player>");
		return false;
	}*/



	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		return (WorldGuardPlugin)plugin;
	}


	protected static String checkRegionId(String id, boolean allowGlobal) throws CommandException {
		if (!ProtectedRegion.isValidId(id)) {
			throw new CommandException(
					"The region name of '" + id + "' contains characters that are not allowed.");
		}

		if (!allowGlobal && id.equalsIgnoreCase("__global__")) { // Sorry, no global
			throw new CommandException(
					"Sorry, you can't use __global__ here.");
		}

		return id;
	}

	protected static RegionManager checkRegionManager(WorldGuardPlugin plugin, World world) throws CommandException {
		if (!plugin.getGlobalStateManager().get(world).useRegions) {
			throw new CommandException("Region support is disabled in the target world. " +
					"It can be enabled per-world in WorldGuard's configuration files. " +
					"However, you may need to restart your server afterwards.");
		}

		RegionManager manager = plugin.getRegionContainer().get(world);
		if (manager == null) {
			throw new CommandException("Region data failed to load for this world. " +
					"Please ask a server administrator to read the logs to identify the reason.");
		}
		return manager;
	}


	protected static ProtectedRegion checkExistingRegion(RegionManager regionManager, String id, boolean allowGlobal) throws CommandException {
		// Validate the id
		checkRegionId(id, allowGlobal);

		ProtectedRegion region = regionManager.getRegion(id);

		// No region found!
		if (region == null) {
			// But we want a __global__, so let's create one
			if (id.equalsIgnoreCase("__global__")) {
				region = new GlobalProtectedRegion(id);
				regionManager.addRegion(region);
				return region;
			}

			throw new CommandException(
					"No region could be found with the name of '" + id + "'.");
		}

		return region;
	}

}