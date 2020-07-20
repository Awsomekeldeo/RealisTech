package com.awsome.techmod.recipes;

import com.awsome.techmod.api.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void init() {
		GameRegistry.addSmelting(ModItems.copperCluster, new ItemStack(ModItems.copperIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.tinCluster, new ItemStack(ModItems.tinIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.nickelCluster, new ItemStack(ModItems.nickelIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.leadCluster, new ItemStack(ModItems.leadIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.silverCluster, new ItemStack(ModItems.silverIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.zincCluster, new ItemStack(ModItems.zincIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.cobaltCluster, new ItemStack(ModItems.cobaltIngot), 0.7f);
		GameRegistry.addSmelting(ModItems.ironCluster, new ItemStack(Items.IRON_INGOT), 0.7f);
		GameRegistry.addSmelting(ModItems.goldCluster, new ItemStack(Items.GOLD_INGOT), 0.7f);
	}
}
