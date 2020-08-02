package awsome.techmod.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import awsome.techmod.setup.ModSetup;
import net.minecraft.item.Item;

public class ItemOreCluster extends Item {
	
	public static final List<ItemOreCluster> CLUSTER_LIST = new ArrayList<>();
	
	public ItemOreCluster() {
		super(new Item.Properties().maxStackSize(32).group(ModSetup.TECHMOD_MATERIALS));
		CLUSTER_LIST.add(this);
	}
	
	public static List<ItemOreCluster> getClusterList() {
		return Collections.unmodifiableList(CLUSTER_LIST);
	}
}
