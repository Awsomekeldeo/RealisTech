package com.awsome.techmod.setup;

import java.util.List;

import com.awsome.techmod.Techmod;
import com.awsome.techmod.api.ModItems;
import com.awsome.techmod.items.ItemIngot;
import com.awsome.techmod.items.ItemOreCluster;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSetup extends ModItems {
	
	public static List<ItemIngot> INGOTITEMS = ItemIngot.getIngotList();
	public static List<ItemOreCluster> ORECLUSTERS = ItemOreCluster.getClusterList();
	
	public static void setup() {
		copperIngot = new ItemIngot("copper_ingot", "copper_ingot");
		tinIngot = new ItemIngot("tin_ingot", "tin_ingot");
		zincIngot = new ItemIngot("zinc_ingot", "zinc_ingot");
		leadIngot = new ItemIngot("lead_ingot", "lead_ingot");
		cobaltIngot = new ItemIngot("cobalt_ingot", "cobalt_ingot");
		silverIngot = new ItemIngot("silver_ingot", "silver_ingot");
		nickelIngot = new ItemIngot("nickel_ingot", "nickel_ingot");
		copperCluster = new ItemOreCluster("copper_cluster", "copper_cluster");
		tinCluster = new ItemOreCluster("tin_cluster", "tin_cluster");
		zincCluster = new ItemOreCluster("zinc_cluster", "zinc_cluster");
		leadCluster = new ItemOreCluster("lead_cluster", "lead_cluster");
		cobaltCluster = new ItemOreCluster("cobalt_cluster", "cobalt_cluster");
		silverCluster = new ItemOreCluster("silver_cluster", "silver_cluster");
		nickelCluster = new ItemOreCluster("nickel_cluster", "nickel_cluster");
		ironCluster = new ItemOreCluster("iron_cluster", "iron_cluster");
		goldCluster = new ItemOreCluster("gold_cluster", "gold_cluster");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModels() {
		Techmod.logger.info("Registering Item Models...");
		MinecraftForge.EVENT_BUS.register(ItemSetup.class);
		for (ItemIngot item : INGOTITEMS) {
			item.registerModels();
		}
		for (ItemOreCluster item : ORECLUSTERS) {
			item.registerModels();
		}
	}
}
