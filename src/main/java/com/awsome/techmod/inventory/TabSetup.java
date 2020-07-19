package com.awsome.techmod.inventory;

import com.awsome.techmod.Techmod;
import com.awsome.techmod.api.ModBlocks;
import com.awsome.techmod.api.ModItems;

import net.minecraft.item.Item;

public class TabSetup {
	public static void setup() {
		Techmod.logger.info("Setting up creative tabs...");
		((ModTabs) ModTabs.MATERIALS).setTabIconItem(ModItems.copperIngot);
		((ModTabs) ModTabs.ORES).setTabIconItem(Item.getItemFromBlock(ModBlocks.cobaltOre));
	}
}
