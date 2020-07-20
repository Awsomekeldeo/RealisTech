package com.awsome.techmod.setup;

import java.util.List;

import com.awsome.techmod.api.ModBlocks;
import com.awsome.techmod.blocks.BlockModOre;
import com.awsome.techmod.blocks.BlockOreSample;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		coalSample = new BlockOreSample("coal_sample", "coal_sample");
		ironSample = new BlockOreSample("iron_sample", "iron_sample");
		diamondSample = new BlockOreSample("diamond_sample", "diamond_sample");
		goldSample = new BlockOreSample("gold_sample", "gold_sample");
		redstoneSample = new BlockOreSample("redstone_sample", "redstone_sample");
		lapisSample = new BlockOreSample("lapis_sample", "lapis_sample");
		emeraldSample = new BlockOreSample("emerald_sample", "emerald_sample");
		modCoalOre = new BlockModOre("mod_coal_ore", "mod_coal_ore", 0);
		modIronOre = new BlockModOre("mod_iron_ore", "mod_iron_ore", 1);
		modGoldOre = new BlockModOre("mod_gold_ore", "mod_gold_ore", 2);
		modLapisOre = new BlockModOre("mod_lapis_ore", "mod_lapis_ore", 1);
		modDiamondOre = new BlockModOre("mod_diamond_ore", "mod_diamond_ore", 2);
		modEmeraldOre = new BlockModOre("mod_emerald_ore", "mod_emerald_ore", 2);
		modRedstoneOre = new BlockModOre("mod_redstone_ore", "mod_redstone_ore", 2);
		loadBlocks();
	}
	
	public static void loadBlocks() {
		
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
