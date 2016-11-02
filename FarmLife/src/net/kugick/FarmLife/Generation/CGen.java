package net.kugick.FarmLife.Generation;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class CGen {
	  private CuboidClipboard schem;
	  private Schema plugin;
	  private int c = 0;

	  public CGen(CuboidClipboard schem, Schema plugin)
	  {
	    this.schem = schem;
	    this.plugin = plugin;
	  }

	  public List getDefaultPopulators(World world)
	  {
	    List populators = (List) new ArrayList();
	    ((ArrayList) populators).add(new CPop(this.schem, this.plugin));
	    return populators;
	  }

	  public byte[][] generateBlockSections(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
	    byte[][] chunk = new byte[world.getMaxY() / 16][];

	    return chunk;
	  }

	  private void set(int x, int y, int z, Material mat, byte[] schunk) {
	    schunk[(256 * y + 16 * z + x)] = ((byte)mat.getId());
	  }

	  private void set(int x, int y, int z, int mat, byte[] schunk) {
	    schunk[(256 * y + 16 * z + x)] = ((byte)mat);
	  }
}
