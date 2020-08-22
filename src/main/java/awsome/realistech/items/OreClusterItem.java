package awsome.realistech.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import awsome.realistech.setup.ModSetup;
import net.minecraft.item.Item;

public class OreClusterItem extends Item {
	
	public static final List<OreClusterItem> CLUSTER_LIST = new ArrayList<>();
	
	public OreClusterItem() {
		super(new Item.Properties().maxStackSize(32).group(ModSetup.TECHMOD_MATERIALS));
		CLUSTER_LIST.add(this);
	}
	
	public static List<OreClusterItem> getClusterList() {
		return Collections.unmodifiableList(CLUSTER_LIST);
	}
}
