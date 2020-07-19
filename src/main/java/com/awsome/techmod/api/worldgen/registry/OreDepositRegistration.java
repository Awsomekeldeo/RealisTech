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
		OreAPI.registerMineralDeposit(ModBlocks.copperOre.getDefaultState(), ModBlocks.copperSample, 32, 112, 50, 5, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "malachite");
		OreAPI.registerMineralDeposit(ModBlocks.tinOre.getDefaultState(), ModBlocks.tinSample, 32, 112, 45, 5, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "cassiterite");
		OreAPI.registerMineralDeposit(ModBlocks.nickelOre.getDefaultState(), ModBlocks.nickelSample, 16, 48, 30, 4, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "garnierite");
		OreAPI.registerMineralDeposit(ModBlocks.leadOre.getDefaultState(), ModBlocks.leadSample, 16, 32, 40, 5, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "galena");
		OreAPI.registerMineralDeposit(ModBlocks.silverOre.getDefaultState(), ModBlocks.silverSample, 16, 32, 30, 4, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "native_silver");
		OreAPI.registerMineralDeposit(ModBlocks.zincOre.getDefaultState(), ModBlocks.zincSample, 24, 56, 20, 3, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "sphalerite");
		OreAPI.registerMineralDeposit(ModBlocks.cobaltOre.getDefaultState(), ModBlocks.cobaltSample, 16, 32, 50, 2, new int[] { 1 }, new ArrayList<IBlockState>(Arrays.asList(new IBlockState[] {Blocks.STONE.getDefaultState()})), 0.5f, "cobaltite");
	}
}
