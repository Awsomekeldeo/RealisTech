package awsome.realistech.items;

import awsome.realistech.setup.ModSetup;
import net.minecraft.item.Item;

public class ToolHeadItem extends Item {
	
	public ToolHeadItem() {
		super(new Item.Properties().maxStackSize(1).group(ModSetup.REALISTECH_TOOLS));
	}
}
