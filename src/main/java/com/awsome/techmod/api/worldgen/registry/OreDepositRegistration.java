package com.awsome.techmod.api.worldgen.registry;

import java.util.ArrayList;

import com.awsome.techmod.api.ModBlocks;
import com.awsome.techmod.api.worldgen.OreAPI;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import scala.actors.threadpool.Arrays;

public class OreDepositRegistration {
	@SuppressWarnings("unchecked")
	public static void init() {
		OreAPI.registerMineralDeposit(ModBlocks.copperOre.getDefaultState(), ModBlocks.copperSample, 25, 52, 50, 36, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "malachite");
		OreAPI.registerMineralDeposit(ModBlocks.tinOre.getDefaultState(), ModBlocks.tinSample, 44, 68, 45, 37, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "cassiterite");
		OreAPI.registerMineralDeposit(ModBlocks.nickelOre.getDefaultState(), ModBlocks.nickelSample, 6, 40, 30, 17, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "garnierite");
		OreAPI.registerMineralDeposit(ModBlocks.leadOre.getDefaultState(), ModBlocks.leadSample, 16, 50, 40, 19, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "galena");
		OreAPI.registerMineralDeposit(ModBlocks.silverOre.getDefaultState(), ModBlocks.silverSample, 16, 50, 30, 19, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "native_silver");
		OreAPI.registerMineralDeposit(ModBlocks.zincOre.getDefaultState(), ModBlocks.zincSample, 35, 55, 20, 19, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "sphalerite");
		OreAPI.registerMineralDeposit(ModBlocks.cobaltOre.getDefaultState(), ModBlocks.cobaltSample, 16, 32, 50, 15, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "cobaltite");
		OreAPI.registerMineralDeposit(ModBlocks.modCoalOre.getDefaultState(), ModBlocks.coalSample, 28, 78, 40, 23, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "bituminous_coal");
		OreAPI.registerMineralDeposit(ModBlocks.modIronOre.getDefaultState(), ModBlocks.ironSample, 32, 60, 50, 22, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "hematite");
		OreAPI.registerMineralDeposit(ModBlocks.modGoldOre.getDefaultState(), ModBlocks.goldSample, 5, 40, 35, 19, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "native_gold");
		OreAPI.registerMineralDeposit(ModBlocks.modDiamondOre.getDefaultState(), ModBlocks.diamondSample, 2, 20, 20, 15, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "kimberlite");
		OreAPI.registerMineralDeposit(ModBlocks.modRedstoneOre.getDefaultState(), ModBlocks.redstoneSample, 5, 12, 35, 17, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "cinnabar");
		OreAPI.registerMineralDeposit(ModBlocks.modEmeraldOre.getDefaultState(), ModBlocks.emeraldSample, 4, 32, 20, 15, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "beryl");
		OreAPI.registerMineralDeposit(ModBlocks.modLapisOre.getDefaultState(), ModBlocks.lapisSample, 10, 38, 35, 31, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "lazurite");
	}
}
