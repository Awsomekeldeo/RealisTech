package com.awsome.techmod.inventory;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTabs extends CreativeTabs {
	
	public static final CreativeTabs MATERIALS = new ModTabs("Tech Mod Materials");
	public static final CreativeTabs ORES = new ModTabs("tmores");
	
	private ItemStack is;

	public ModTabs(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return is;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return is;
	}
	
	public void setTabIconItemStack(ItemStack stack)
	{
		is = stack;
	}
	
	public void setTabIconItem(Item i)
	{
		is = new ItemStack(i);
	}
}
