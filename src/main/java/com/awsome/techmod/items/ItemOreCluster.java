package com.awsome.techmod.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.awsome.techmod.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOreCluster extends ItemMaterial {
	public static final List<ItemOreCluster> CLUSTERS = new ArrayList<>();
	
	public static List<ItemOreCluster> getClusterList() {
		return Collections.unmodifiableList(CLUSTERS);
	}
	
	public ItemOreCluster(String unlocalizedName, String registryName) {
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(Reference.MODID, registryName);
		CLUSTERS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, this.getMetadata(new ItemStack(this)), new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ore_clusters/" + this.getUnlocalizedName().substring(5)), "inventory"));
	}
}
	