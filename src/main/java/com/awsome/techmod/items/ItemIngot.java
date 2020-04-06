package com.awsome.techmod.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.awsome.techmod.Reference;
import com.awsome.techmod.Techmod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIngot extends ItemMaterial {
	public static final List<ItemIngot> INGOTS = new ArrayList<>();
	
	public static List<ItemIngot> getIngotList() {
		return Collections.unmodifiableList(INGOTS);
	}
	
	public ItemIngot(String unlocalizedname, String registryname) {
		super();
		this.setUnlocalizedName(unlocalizedname);
		this.setRegistryName(Reference.MODID, registryname);
		Techmod.logger.info("Created Instance of: " + this.getUnlocalizedName());
		INGOTS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, this.getMetadata(new ItemStack(this)), new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":ingots/" + this.getUnlocalizedName().substring(5)), "inventory"));
	}
}
