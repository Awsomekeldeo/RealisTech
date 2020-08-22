package awsome.realistech.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import awsome.realistech.setup.ModSetup;
import net.minecraft.item.Item;

public class IngotItem extends Item {
	public static final List<IngotItem> INGOT_LIST = new ArrayList<>();
	
	public IngotItem() {
		super(new Item.Properties().maxStackSize(64).group(ModSetup.TECHMOD_MATERIALS));
		INGOT_LIST.add(this);
	}
	
	public static List<IngotItem> getIngotList() {
		return Collections.unmodifiableList(INGOT_LIST);
	}
	
}
