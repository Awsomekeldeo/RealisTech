package com.awsome.techmod.setup;

import java.util.List;

import com.awsome.techmod.api.ModBlocks;
import com.awsome.techmod.blocks.BlockModOre;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSetup extends ModBlocks {
	
	public static List<BlockModOre> ORES = BlockModOre.getOreList();
	
	public static void setup() {
		copperOre = new BlockModOre("copper_ore", "copper_ore");
		tinOre = new BlockModOre("tin_ore", "tin_ore");
		nickelOre = new BlockModOre("nickel_ore", "nickel_ore");
		leadOre = new BlockModOre("lead_ore", "lead_ore");
		silverOre = new BlockModOre("silver_ore", "silver_ore");
		zincOre = new BlockModOre("zinc_ore", "zinc_ore");
		cobaltOre = new BlockModOre("cobalt_ore", "cobalt_ore");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		MinecraftForge.EVENT_BUS.register(BlockSetup.class);
        for (BlockModOre block : ORES) {
            block.registerModels(Item.getItemFromBlock(block));
        }
	}
}
