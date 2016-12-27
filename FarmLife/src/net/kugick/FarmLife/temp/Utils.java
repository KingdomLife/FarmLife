package net.kugick.FarmLife.temp;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.kugick.FarmLife.FarmLife;

public class Utils
{
	//////////////////
	// String utils //
	//////////////////
	public static Map<String, String> inputSplitter(List<String> input, String splitter)
	{
		Map<String, String> opt = new HashMap<String, String>();
		
		for (String line : input)
		{
			String[] sline = line.split(splitter);
			opt.put(sline[0].toLowerCase(), sline[1]);
		}
		
		return opt;
	}
	
	////////////////
	// File utils //
	////////////////
	public static Path getTempFilePath(UUID uuid)
		{return Paths.get(FarmLife.getInstance().getDataFolder().toPath().toString(), Config.getData("files_player").replace("[UUID]", uuid.toString()));}
}
