package com.awsome.techmod;

import org.apache.logging.log4j.Logger;

import com.awsome.techmod.api.worldgen.registry.OreDepositRegistration;
import com.awsome.techmod.inventory.TabSetup;
import com.awsome.techmod.proxy.CommonProxy;
import com.awsome.techmod.setup.BlockSetup;
import com.awsome.techmod.setup.ItemSetup;
import com.awsome.techmod.world.ChunkData;
import com.awsome.testmod.util.worldgen.OreGenerator;
import com.awsome.testmod.util.worldgen.VanillaGenOverride;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = "required:forge@[14.23.5.2768,);", useMetadata = true)
public class Techmod {
	
	public ChunkData chunkOreGen;
	
	@Mod.Instance
	public static Techmod instance;
	
	@SidedProxy(clientSide = "com.awsome.techmod.proxy.ClientProxy", serverSide = "com.awsome.techmod.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	public static Techmod getInstance() {
		return instance;
	}
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		chunkOreGen = new ChunkData();
		logger = event.getModLog();
		ItemSetup.setup();
		BlockSetup.setup();
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.ORE_GEN_BUS.register(new VanillaGenOverride());
		OreDepositRegistration.init();
		TabSetup.setup();
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
	}
}
