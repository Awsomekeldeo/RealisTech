package awsome.realistech.items;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import awsome.realistech.Reference;
import awsome.realistech.util.GeneralUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

public class HammerItem extends Item {

	public static final ToolType HAMMER = ToolType.get(Reference.MODID + "hammer");
	
	public HammerItem(Properties properties) {
		super(properties.defaultMaxDamage(128));
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack containerItem = itemStack.copy();
		if (containerItem.attemptDamageItem(1, GeneralUtils.RAND, null)) {
			return ItemStack.EMPTY;
		}else{
			return containerItem;
		}
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
		return ImmutableSet.of(HAMMER);
	}
}
