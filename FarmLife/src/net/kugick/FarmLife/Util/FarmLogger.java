package net.kugick.FarmLife.Util;

import java.util.logging.Logger;

import net.kugick.FarmLife.FarmLife;

public class FarmLogger
{
	private static Logger logger = FarmLife.getInstance().getLogger();
	
	public static void info(String msg)
		{logger.info(msg);}
	public static void severe(String msg)
		{logger.severe(msg);}
	public static void warning(String msg)
		{logger.warning(msg);}
	public static void warning2(String msg)
	{
		logger.warning("");
		logger.warning(Utils.wrap(msg, " !!! "));
		logger.warning("");
	}
}
