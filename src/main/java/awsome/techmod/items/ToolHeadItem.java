package awsome.techmod.items;

import awsome.techmod.setup.ModSetup;
import net.minecraft.item.Item;

public class ToolHeadItem extends Item {
	
	public ToolHeadItem() {
		super(new Item.Properties().maxStackSize(1).group(ModSetup.TECHMOD_TOOLS));
	}
}
