package awsome.realistech.items;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import awsome.realistech.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

public class ProspectorsPickaxeItem extends Item {
	
	public static final ToolType PROSPECTORS_PICKAXE = ToolType.get(Reference.MODID + "propick");
	
	public ProspectorsPickaxeItem(Properties properties) {
		super(properties.defaultMaxDamage(300));
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public Set<ToolType> getToolTypes(ItemStack stack) {
		return ImmutableSet.of(PROSPECTORS_PICKAXE);
	}
}
