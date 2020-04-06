package com.awsome.techmod;

import org.apache.logging.log4j.Logger;

import com.awsome.techmod.proxy.CommonProxy;
import com.awsome.techmod.setup.BlockSetup;
import com.awsome.techmod.setup.ItemSetup;
import com.awsome.testmod.util.worldgen.OreGen;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = "required:forge@[14.23.5.2768,);", useMetadata = true)
public class Techmod {
	
	@Mod.Instance
	public static Techmod instance;
	
	@SidedProxy(clientSide = "com.awsome.techmod.proxy.ClientProxy", serverSide = "com.awsome.techmod.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		ItemSetup.setup();
		BlockSetup.setup();
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
}
