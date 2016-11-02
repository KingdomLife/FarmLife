package net.kugick.FarmLife.Generation;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Block;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.world.chunk.Chunk;
import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;

public class CPop {
	  private CuboidClipboard schem;
	  private Schema plugin;

	  public CPop(CuboidClipboard schem, Schema plugin)
	  {
	    this.schem = schem;
	    this.plugin = plugin;
	  }

	  public void populate(World world, Random random, Chunk chunk)
	  {
	    int chunkX = ((Block) chunk).getX();
	    int chunkZ = ((Block) chunk).getZ();

	    for (int x = 0; x < 16; x++)
	      for (int y = 0; y < this.schem.getHeight(); y++)
	        for (int z = 0; z < 16; z++) {
	          int blockX = chunkX * 16 + x;
	          int blockZ = chunkZ * 16 + z;

	          int rx = blockX % this.schem.getWidth();
	          if (rx < 0)
	            rx += this.schem.getWidth();
	          int rz = blockZ % this.schem.getLength();
	          if (rz < 0) {
	            rz += this.schem.getLength();
	          }
	          Block block = world.getBlockAt(blockX, y, blockZ);
	          BaseBlock bb = this.schem.getBlock(new Vector(rx, y, rz));
	          block.setTypeId(bb.getType());
	          block.setData((byte)bb.getData());
	        }
	  }
}
