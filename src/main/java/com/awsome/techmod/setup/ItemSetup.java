package com.awsome.techmod.setup;

import java.util.List;

import com.awsome.techmod.Techmod;
import com.awsome.techmod.api.ModBlocks;
import com.awsome.techmod.api.ModItems;
import com.awsome.techmod.inventory.ModTabs;
import com.awsome.techmod.items.ItemIngot;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSetup extends ModItems {
	
	public static List<ItemIngot> INGOTITEMS = ItemIngot.getIngotList();
	
	public static void setup() {
		copperIngot = new ItemIngot("copper_ingot", "copper_ingot");
		tinIngot = new ItemIngot("tin_ingot", "tin_ingot");
		zincIngot = new ItemIngot("zinc_ingot", "zinc_ingot");
		leadIngot = new ItemIngot("lead_ingot", "lead_ingot");
		cobaltIngot = new ItemIngot("cobalt_ingot", "cobalt_ingot");
		silverIngot = new ItemIngot("silver_ingot", "silver_ingot");
		nickelIngot = new ItemIngot("nickel_ingot", "nickel_ingot");
		
		((ModTabs) ModTabs.MATERIALS).setTabIconItem(ModItems.copperIngot);
		((ModTabs) ModTabs.ORES).setTabIconItem(Item.getItemFromBlock(ModBlocks.cobaltOre));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModels() {
		Techmod.logger.info("Registering Models...");
		MinecraftForge.EVENT_BUS.register(ItemSetup.class);
		for (ItemIngot item : INGOTITEMS) {
			item.registerModels();
		}
	}
}
