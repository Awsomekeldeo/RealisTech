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
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModOre extends Block {
	

	public static final List<BlockModOre> ORES = new ArrayList<>();
	public static final PropertyEnum<BlockModOre.EnumStoneType> STONETYPE = PropertyEnum.<BlockModOre.EnumStoneType>create("stonetype", BlockModOre.EnumStoneType.class);
	public List<ItemStack> dropList = new ArrayList<>();
	
	public static List<BlockModOre> getOreList() {
		return Collections.unmodifiableList(ORES);
	}
	
	public BlockModOre(String unlocalizedname, String registryname, int harvestLevel) {
		super(Material.ROCK);
		this.setUnlocalizedName(unlocalizedname);
		this.setHardness(3.0f);
		this.setRegistryName(registryname);
		this.setHarvestLevel("pickaxe", harvestLevel);
		this.setCreativeTab(ModTabs.ORES);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STONETYPE, BlockModOre.EnumStoneType.STONE));
		ORES.add(this);
	}

	public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(STONETYPE, BlockModOre.EnumStoneType.values()[meta]);
    }
	
	public int getMetaFromState(IBlockState state){
        return ((BlockModOre.EnumStoneType)state.getValue(STONETYPE)).getMetadata();
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STONETYPE);
	}
	
	public static enum EnumStoneType implements IStringSerializable {
		STONE(0, "stone");
		
		private final int meta;
		private final String name;
		
		
		private EnumStoneType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		
		public int getMetadata() {
			return this.meta;
		}
		
		public String toString() {
			return this.name;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
	}
	
	public void setDrops(ArrayList<ItemStack> items) {
		dropList = items;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModel(ModelRegistryEvent event){
		registerModels(Item.getItemFromBlock(this));
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, item.getMetadata(new ItemStack(this)), new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ores/" + this.getUnlocalizedName().substring(5)), "inventory"));
		//Techmod.logger.info("Registered models for blocks at" + new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ores/" + this.getUnlocalizedName().substring(5)), "inventory").toString());
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		Random random = new Random();
		String blockName = this.getRegistryName().toString();
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
		
		
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		String blockName = this.getRegistryName().toString();
		if (blockName.equals("techmod:mod_coal_ore")) {
			return Blocks.COAL_ORE.getExpDrop(state, world, pos, fortune);
		}else if ((blockName.equals("techmod:mod_redstone_ore"))) {
			return Blocks.REDSTONE_ORE.getExpDrop(state, world, pos, fortune);
		}else if ((blockName.equals("techmod:mod_diamond_ore"))) {
			return Blocks.DIAMOND_ORE.getExpDrop(state, world, pos, fortune);
		}else if ((blockName.equals("techmod:mod_emerald_ore"))) {
			return Blocks.EMERALD_ORE.getExpDrop(state, world, pos, fortune);
		}else if ((blockName.equals("techmod:mod_lapis_ore"))) {
			return Blocks.LAPIS_ORE.getExpDrop(state, world, pos, fortune);
		}else{
			return 0;
		}
	}
}
