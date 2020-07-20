package com.awsome.techmod.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.awsome.techmod.Reference;
import com.awsome.techmod.Techmod;
import com.awsome.techmod.api.ModItems;
import com.awsome.techmod.inventory.ModTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
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
	
	private static final AxisAlignedBB blockBounds = new AxisAlignedBB(4/16d, 0, 5/16d, 12/16d, 2/16d, 13/16d);
	
	
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
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		String blockName = this.getRegistryName().toString();
		Random random = new Random();
		if (blockName.equals("techmod:mod_coal_ore")) {
			drops.add(new ItemStack(Blocks.COAL_ORE.getItemDropped(state, random, fortune), Blocks.COAL_ORE.quantityDroppedWithBonus(fortune, random), Blocks.COAL_ORE.damageDropped(state)));
			Techmod.logger.info(blockName);
		}else if ((blockName.equals("techmod:mod_redstone_ore"))) {
			drops.add(new ItemStack(Blocks.REDSTONE_ORE.getItemDropped(state, random, fortune), Blocks.REDSTONE_ORE.quantityDroppedWithBonus(fortune, random), Blocks.REDSTONE_ORE.damageDropped(state)));
		}else if ((blockName.equals("techmod:mod_diamond_ore"))) {
			drops.add(new ItemStack(Blocks.DIAMOND_ORE.getItemDropped(state, random, fortune), Blocks.DIAMOND_ORE.quantityDroppedWithBonus(fortune, random), Blocks.DIAMOND_ORE.damageDropped(state)));
		}else if ((blockName.equals("techmod:mod_emerald_ore"))) {
			drops.add(new ItemStack(Blocks.EMERALD_ORE.getItemDropped(state, random, fortune), Blocks.EMERALD_ORE.quantityDroppedWithBonus(fortune, random), Blocks.EMERALD_ORE.damageDropped(state)));
		}else if ((blockName.equals("techmod:mod_lapis_ore"))) {
			drops.add(new ItemStack(Blocks.LAPIS_ORE.getItemDropped(state, random, fortune), Blocks.LAPIS_ORE.quantityDroppedWithBonus(fortune, random), Blocks.LAPIS_ORE.damageDropped(state)));
		}else if ((blockName.equals("techmod:mod_iron_ore"))) {
			drops.add(new ItemStack(ModItems.ironCluster, 1, 0));
		}else if ((blockName.equals("techmod:mod_gold_ore"))) {
			drops.add(new ItemStack(ModItems.goldCluster, 1, 0));
		}else if ((blockName.equals("techmod:copper_ore"))) {
			drops.add(new ItemStack(ModItems.copperCluster, 1, 0));
		}else if ((blockName.equals("techmod:tin_ore"))) {
			drops.add(new ItemStack(ModItems.tinCluster, 1, 0));
		}else if ((blockName.equals("techmod:nickel_ore"))) {
			drops.add(new ItemStack(ModItems.nickelCluster, 1, 0));
		}else if ((blockName.equals("techmod:lead_ore"))) {
			drops.add(new ItemStack(ModItems.leadCluster));
		}else if ((blockName.equals("techmod:cobalt_ore"))) {
			drops.add(new ItemStack(ModItems.cobaltCluster));
		}else if ((blockName.equals("techmod:zinc_ore"))) {
			drops.add(new ItemStack(ModItems.zincCluster));
		}else if ((blockName.equals("techmod:silver_ore"))) {
			drops.add(new ItemStack(ModItems.silverCluster));
		}
		for (ItemStack stack : drops) {
            if (stack.getCount() > 1) {
                stack.setCount(1);
            }
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

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	
	
}
