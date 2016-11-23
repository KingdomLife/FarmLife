package net.kugick.FarmLife.Generation;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FarmGenerator extends ChunkGenerator
{
	File farmSchematic;
	
	public FarmGenerator(File farmSchematicFile)
		{this.farmSchematic = farmSchematicFile;}
	
	public byte[] generate(World world, Random random, int x, int z)
		{throw new UnsupportedOperationException("FarmGenerator error: generateBlockSections() was not executed");}
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
		{return null;}
	public byte[][] generateBlockSections(World world, Random random, int chunkx, int chunkz, BiomeGrid biomes)
		{return new byte[world.getMaxHeight() / 16][];}
	
	public boolean canSpawn(World world, int x, int z)
		{return true;}
	public List<BlockPopulator> getDefaultPopulators(World world)
		{return Arrays.asList((BlockPopulator)new FarmPopulator(farmSchematic));}
	public Location getFixedSpawnLocation(World world, Random nope)
		{return null;}
}
