package net.kugick.FarmLife.Generation;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class FarmPopulator extends BlockPopulator
{
	short width, height, length, chwidth, chlength;
	int[][][] chunkedBlocks;
	byte[][][] chunkedData;
	
	public FarmPopulator(File farmSchematic)
	{
		byte[] blocks, data;
		
		try
		{
			FileInputStream fis = new FileInputStream(farmSchematic);
			NBTTagCompound nbt = NBTCompressedStreamTools.a(fis);
			
			width = nbt.getShort("Width");
			height = nbt.getShort("Height");
			length = nbt.getShort("Length");
			
			blocks = nbt.getByteArray("Blocks");
			data = nbt.getByteArray("Data");
			
			fis.close();
			
			chwidth = (short)(width/16 +1);
			chlength = (short)(length/16 +1);
			chunkedBlocks = new int[chwidth][chlength][height*256];
			chunkedData = new byte[chwidth][chlength][height*256];
			
			for(int x=0; x<width; ++x)
				for(int y=0; y<height; ++y)
					for(int z=0; z<length; ++z)
					{
						int index = y*width*length+z*width+x, index2 = 256*y+16*(z%16)+x%16;
						
						chunkedBlocks[x/16][z/16][index2] = blocks[index] & 0xFF;
						chunkedData[x/16][z/16][index2] = data[index];
					}
		}
		catch(Exception e) {System.out.println("Could not load farm schematic file!"); e.printStackTrace();}
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World world, Random hahahahaNO, Chunk chunk)
	{
		int X = Math.abs(chunk.getX())%12, Z = Math.abs(chunk.getZ())%12;
		
		if (X < chwidth && Z < chlength)
		{
			for(byte x=0; x<16; ++x)
				for (short y=0; y<height; ++y)
					for (byte z=0; z<16; ++z)
					{
						chunk.getBlock(x, y+64, z).setTypeId(chunkedBlocks[X][Z][256*y+16*(z%16)+x%16]);
						chunk.getBlock(x, y+64, z).setData(chunkedData[X][Z][256*y+16*(z%16)+x%16]);
					}
		}
	}
}
