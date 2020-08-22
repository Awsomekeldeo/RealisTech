package awsome.realistech.items;

import awsome.realistech.capability.MoldCapabilityWrapper;
import awsome.realistech.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CeramicMoldItem extends Item {
	
	private int capacity;
	
	public CeramicMoldItem(int capacity) {
		super(new Item.Properties().group(ModSetup.TECHMOD_MISC));
		this.capacity = capacity;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new MoldCapabilityWrapper(capacity, stack);
	}
}
