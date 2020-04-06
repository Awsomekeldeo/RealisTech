package com.awsome.techmod.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.awsome.techmod.Reference;
import com.awsome.techmod.inventory.ModTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModOre extends Block {
	

	public static final List<BlockModOre> ORES = new ArrayList<>();
	public static final PropertyEnum<BlockModOre.EnumStoneType> STONETYPE = PropertyEnum.<BlockModOre.EnumStoneType>create("stonetype", BlockModOre.EnumStoneType.class);
	
	public static List<BlockModOre> getOreList() {
		return Collections.unmodifiableList(ORES);
	}
	
	public BlockModOre(String unlocalizedname, String registryname) {
		super(Material.ROCK);
		this.setUnlocalizedName(unlocalizedname);
		this.setRegistryName(registryname);
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
	
	@SideOnly(Side.CLIENT)
	public void registerModel(ModelRegistryEvent event){
		registerModels(Item.getItemFromBlock(this));
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, item.getMetadata(new ItemStack(this)), new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ores/" + this.getUnlocalizedName().substring(5)), "inventory"));
		//Techmod.logger.info("Registered models for blocks at" + new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ores/" + this.getUnlocalizedName().substring(5)), "inventory").toString());
	}
	
	
}
