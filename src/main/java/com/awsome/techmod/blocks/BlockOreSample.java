package com.awsome.techmod.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.awsome.techmod.Reference;
import com.awsome.techmod.inventory.ModTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOreSample extends Block {
	public static final List<BlockOreSample> SAMPLES = new ArrayList<>();
	public List<ItemStack> dropList = new ArrayList<>();
	
	private static final AxisAlignedBB blockBounds = new AxisAlignedBB(4/16d, 0, 05/16d, 11/16d, 02/16d, 13/16d);
	
	
	public BlockOreSample(String unlocalizedname, String registryname) {
		super(Material.GROUND);
		this.setUnlocalizedName(unlocalizedname);
		this.setRegistryName(registryname);
		this.setCreativeTab(ModTabs.ORES);
		this.setSoundType(SoundType.GROUND);
		SAMPLES.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, item.getMetadata(new ItemStack(this)), new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ores/samples/" + this.getUnlocalizedName().substring(5)), "inventory"));		//Techmod.logger.info("Registered models for blocks at" + new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ores/" + this.getUnlocalizedName().substring(5)), "inventory").toString());
	}
	
	public void setDrops(ArrayList<ItemStack> items) {
		dropList = items;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		
		for (int i = 0; i < dropList.size(); i++) {
			ItemStack j = dropList.get(i);
			drops.add(j);
		}
	}

	public static List<BlockOreSample> getSampleList() {
		return Collections.unmodifiableList(SAMPLES);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BlockOreSample.blockBounds;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return BlockOreSample.blockBounds;
	}

	
	
	
}
