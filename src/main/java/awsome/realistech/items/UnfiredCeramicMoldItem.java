package awsome.realistech.items;

import awsome.realistech.setup.ModSetup;
import net.minecraft.item.Item;

public class UnfiredCeramicMoldItem extends Item {
	public UnfiredCeramicMoldItem() {
		super(new Item.Properties().group(ModSetup.REALISTECH_MISC).maxStackSize(1));
	}
}
