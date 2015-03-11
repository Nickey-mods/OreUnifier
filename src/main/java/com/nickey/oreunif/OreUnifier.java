package com.nickey.oreunif;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="OreUnif",name="OreUnifier",version="v1a")
public class OreUnifier {

	public static boolean enable;
	public static String blackList;
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if(enable)
		{
			MinecraftForge.EVENT_BUS.register(new EventSubscriber());
		}
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		File file = event.getSuggestedConfigurationFile();
		Configuration cfg = new Configuration(file);
		cfg.load();
		enable=cfg.getBoolean("Enabled","general",true,"no comment");
		blackList = cfg.getString("BlackList","general","","These items are not unified");
		cfg.save();
	}
}
