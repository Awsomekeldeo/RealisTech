package com.awsome.techmod.setup;

import java.util.ArrayList;
import java.util.List;

import com.awsome.techmod.api.ModBlocks;
import com.awsome.techmod.api.ModItems;
import com.awsome.techmod.blocks.BlockModOre;
import com.awsome.techmod.blocks.BlockOreSample;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

public class BlockSetup extends ModBlocks {
	
	public static List<BlockModOre> ORES = BlockModOre.getOreList();
	public static List<BlockOreSample> SAMPLES = BlockOreSample.getSampleList();
	
	public static void setup() {
		copperOre = new BlockModOre("copper_ore", "copper_ore", 1);
		tinOre = new BlockModOre("tin_ore", "tin_ore", 1);
		nickelOre = new BlockModOre("nickel_ore", "nickel_ore", 2);
		leadOre = new BlockModOre("lead_ore", "lead_ore", 2);
		silverOre = new BlockModOre("silver_ore", "silver_ore", 2);
		zincOre = new BlockModOre("zinc_ore", "zinc_ore", 2);
		cobaltOre = new BlockModOre("cobalt_ore", "cobalt_ore", 1);
		copperSample = new BlockOreSample("copper_sample", "copper_sample");
		tinSample = new BlockOreSample("tin_sample", "tin_sample");
		leadSample = new BlockOreSample("lead_sample", "lead_sample");
		nickelSample = new BlockOreSample("nickel_sample", "nickel_sample");
		silverSample = new BlockOreSample("silver_sample", "silver_sample");
		zincSample = new BlockOreSample("zinc_sample", "zinc_sample");
		cobaltSample = new BlockOreSample("cobalt_sample", "cobalt_sample");
		loadBlocks();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadBlocks() {
		((BlockModOre)copperOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.copperCluster)})));
		((BlockModOre)tinOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.tinCluster)})));
		((BlockModOre)leadOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.leadCluster)})));
		((BlockModOre)nickelOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.nickelCluster)})));
		((BlockModOre)silverOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.silverCluster)})));
		((BlockModOre)cobaltOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.cobaltCluster)})));
		((BlockModOre)zincOre).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.zincCluster)})));
		((BlockOreSample)copperSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.copperCluster)})));
		((BlockOreSample)tinSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.tinCluster)})));
		((BlockOreSample)leadSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.leadCluster)})));
		((BlockOreSample)nickelSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.nickelCluster)})));
		((BlockOreSample)silverSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.silverCluster)})));
		((BlockOreSample)zincSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.zincCluster)})));
		((BlockOreSample)cobaltSample).setDrops(new ArrayList<ItemStack>(Arrays.asList(new ItemStack[] {new ItemStack(ModItems.cobaltCluster)})));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		MinecraftForge.EVENT_BUS.register(BlockSetup.class);
        for (BlockModOre block : ORES) {
            block.registerModels(Item.getItemFromBlock(block));
        }
        for (BlockOreSample block : SAMPLES) {
        	block.registerModels(Item.getItemFromBlock(block));
        }
	}
}
