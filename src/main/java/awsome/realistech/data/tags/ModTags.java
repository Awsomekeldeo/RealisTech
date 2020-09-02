package awsome.realistech.data.tags;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {
	
	public static class Items {
		public static final Tag<Item> COPPER_INGOT = makeForgeWrapperTag("ingots/copper");
		public static final Tag<Item> TIN_INGOT = makeForgeWrapperTag("ingots/tin");
		public static final Tag<Item> NICKEL_INGOT = makeForgeWrapperTag("ingots/nickel");
		public static final Tag<Item> SILVER_INGOT = makeForgeWrapperTag("ingots/silver");
		public static final Tag<Item> LEAD_INGOT = makeForgeWrapperTag("ingots/lead");
		public static final Tag<Item> COBALT_INGOT = makeForgeWrapperTag("ingots/cobalt");
		public static final Tag<Item> ZINC_INGOT = makeForgeWrapperTag("ingots/zinc");
		
		private static Tag<Item> makeForgeWrapperTag(String path) {
			return new ItemTags.Wrapper(new ResourceLocation("forge", path));
		}
	}
}
