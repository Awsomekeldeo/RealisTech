package awsome.techmod.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import awsome.techmod.setup.ModSetup;
import net.minecraft.item.Item;

public class ItemIngot extends Item {
	public static final List<ItemIngot> INGOT_LIST = new ArrayList<>();
	
	public ItemIngot() {
		super(new Item.Properties().maxStackSize(64).group(ModSetup.TECHMOD_MATERIALS));
		INGOT_LIST.add(this);
	}
	
	public static List<ItemIngot> getIngotList() {
		return Collections.unmodifiableList(INGOT_LIST);
	}
	
}
