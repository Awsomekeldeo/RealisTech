package com.awsome.techmod.proxy;

import com.awsome.techmod.Reference;
import com.awsome.techmod.Techmod;
import com.awsome.techmod.blocks.BlockModOre;
import com.awsome.techmod.blocks.BlockOreSample;
import com.awsome.techmod.items.ItemIngot;
import com.awsome.techmod.items.ItemOreCluster;
import com.awsome.techmod.setup.BlockSetup;
import com.awsome.techmod.setup.ItemSetup;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class CommonProxy {
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		Techmod.logger.info("Registering Blocks...");
		for (BlockModOre block : BlockSetup.ORES) {
			registry.register(block);
			Techmod.logger.info("Registered " + block.getRegistryName());
		}
		for (BlockOreSample block : BlockSetup.SAMPLES) {
			registry.register(block);
		}
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		Techmod.logger.info("[Tech Mod] Registering Items...");
		for (ItemIngot item : ItemSetup.INGOTITEMS) {
			registry.register(item);
			Techmod.logger.info("Registered " + item.getRegistryName());
		}
		for (BlockModOre itemblock : BlockSetup.ORES) {
			registry.register(new ItemBlock(itemblock).setRegistryName(itemblock.getRegistryName()));
			Techmod.logger.info("Registered " + itemblock.getRegistryName());
		}
		for (ItemOreCluster item : ItemSetup.ORECLUSTERS) {
			registry.register(item);
		}
		for (BlockOreSample itemblock : BlockSetup.SAMPLES) {
			registry.register(new ItemBlock(itemblock).setRegistryName(itemblock.getRegistryName()));
		}
	}
	
	public void preInit(FMLPreInitializationEvent e) {
		Techmod.logger.info("Undergoing Pre-Initialization...");
	}
	
	public void init(FMLInitializationEvent e) {
		
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
