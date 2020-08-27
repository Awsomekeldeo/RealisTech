package awsome.realistech.setup;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Ordering;

import awsome.realistech.Reference;
import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.registry.OreDepositRegistration;
import awsome.realistech.registry.Registration;
import awsome.realistech.worldgen.FeatureStripper;
import awsome.realistech.worldgen.api.OreAPI;
import awsome.realistech.worldgen.capability.IWorldgenCapability;
import awsome.realistech.worldgen.capability.WorldgenCapStorage;
import awsome.realistech.worldgen.capability.WorldgenCapability;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@SuppressWarnings("deprecation")
public class ModSetup {
	
	static Comparator<ItemStack> oreSorter;
	static Comparator<ItemStack> materialSorter;
	static Comparator<ItemStack> machineSorter;
	static Comparator<ItemStack> toolSorter;
	static Comparator<ItemStack> miscSorter;
 	
	public static final ItemGroup TECHMOD_MACHINES = new ItemGroup("realistech.machines") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.FIREBOX.get());
		}
		
		@Override
		public void fill(NonNullList<ItemStack> items) {
			super.fill(items);
			items.sort(machineSorter);
		}
		
	};
	
	public static final ItemGroup TECHMOD_ORES =  new ItemGroup("realistech.ores") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.COPPER_ORE.get());
		}
		
		@Override
		public void fill(NonNullList<ItemStack> items) {
			super.fill(items);
			items.sort(oreSorter);
		}
	};
	
	public static final ItemGroup TECHMOD_MATERIALS =  new ItemGroup("realistech.materials") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.COPPER_INGOT.get());
		}
		
		@Override
		public void fill(NonNullList<ItemStack> items) {
			super.fill(items);
			items.sort(materialSorter);
		}
		
	};
	
	public static final ItemGroup TECHMOD_TOOLS = new ItemGroup("realistech.tools") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.AIR);
		}
	};
	
	public static final ItemGroup TECHMOD_MISC = new ItemGroup("realistech.misc") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.FIRED_CERAMIC_INGOT_MOLD.get());
		}
		
		public void fill(net.minecraft.util.NonNullList<ItemStack> items) {
			super.fill(items);
			items.sort(miscSorter);
		}
	};
	
	public static List<Item> getItemList(ItemGroup group) {
		if (group == TECHMOD_MATERIALS) {
			return Arrays.asList(new Item[] {
				Registration.COPPER_INGOT.get(),
				Registration.TIN_INGOT.get(),
				Registration.SILVER_INGOT.get(),
				Registration.NICKEL_INGOT.get(),
				Registration.LEAD_INGOT.get(),
				Registration.COBALT_INGOT.get(),
				Registration.ZINC_INGOT.get(),
				Registration.COPPER_CLUSTER.get(),
				Registration.TIN_CLUSTER.get(),
				Registration.SILVER_CLUSTER.get(),
				Registration.NICKEL_CLUSTER.get(),
				Registration.LEAD_CLUSTER.get(),
				Registration.COBALT_CLUSTER.get(),
				Registration.ZINC_CLUSTER.get(),
				Registration.IRON_CLUSTER.get(),
				Registration.GOLD_CLUSTER.get(),
				Registration.KILN_BRICK.get(),
				Registration.UNFIRED_CLAY_BRICK.get(),
				Registration.UNFIRED_KILN_BRICK.get(),
				Registration.KILN_CLAY_BALL.get()
			}); 
		}
		if (group == TECHMOD_MACHINES) {
			return Arrays.asList(new Item[] {
				Registration.FIREBOX_ITEM.get(),
				Registration.CRUCIBLE_ITEM.get(),
				Registration.KILN_ITEM.get()
			});
		}
		if (group == TECHMOD_ORES) {
			return Arrays.asList(new Item[] {
				Registration.COPPER_ORE_ITEM.get(),
				Registration.TIN_ORE_ITEM.get(),
				Registration.SILVER_ORE_ITEM.get(),
				Registration.NICKEL_ORE_ITEM.get(),
				Registration.LEAD_ORE_ITEM.get(),
				Registration.COBALT_ORE_ITEM.get(),
				Registration.ZINC_ORE_ITEM.get(),
				Registration.MOD_IRON_ORE_ITEM.get(),
				Registration.MOD_COAL_ORE_ITEM.get(),
				Registration.MOD_GOLD_ORE_ITEM.get(),
				Registration.MOD_DIAMOND_ORE_ITEM.get(),
				Registration.MOD_EMERALD_ORE_ITEM.get(),
				Registration.MOD_REDSTONE_ORE_ITEM.get(),
				Registration.MOD_LAPIS_ORE_ITEM.get(),
				Registration.COPPER_SAMPLE_ITEM.get(),
				Registration.TIN_SAMPLE_ITEM.get(),
				Registration.SILVER_SAMPLE_ITEM.get(),
				Registration.NICKEL_SAMPLE_ITEM.get(),
				Registration.LEAD_SAMPLE_ITEM.get(),
				Registration.COBALT_SAMPLE_ITEM.get(),
				Registration.ZINC_SAMPLE_ITEM.get(),
				Registration.IRON_SAMPLE_ITEM.get(),
				Registration.COAL_SAMPLE_ITEM.get(),
				Registration.GOLD_SAMPLE_ITEM.get(),
				Registration.DIAMOND_SAMPLE_ITEM.get(),
				Registration.EMERALD_SAMPLE_ITEM.get(),
				Registration.REDSTONE_SAMPLE_ITEM.get(),
				Registration.LAPIS_SAMPLE_ITEM.get()
			});
		}
		if (group == TECHMOD_TOOLS) {
			return Arrays.asList(new Item[] {
					
			});
		}
		if (group == TECHMOD_MISC) {
			return Arrays.asList(new Item[] {
				Registration.UNFIRED_CERAMIC_INGOT_MOLD.get(),
				Registration.UNFIRED_CERAMIC_SHOVEL_MOLD.get(),
				Registration.UNFIRED_CERAMIC_AXE_MOLD.get(),
				Registration.UNFIRED_CERAMIC_PICKAXE_MOLD.get(),
				Registration.UNFIRED_CERAMIC_PROPICK_MOLD.get(),
				Registration.UNFIRED_CERAMIC_SWORD_MOLD.get(),
				Registration.FIRED_CERAMIC_INGOT_MOLD.get(),
				Registration.FIRED_CERAMIC_SHOVEL_MOLD.get(),
				Registration.FIRED_CERAMIC_AXE_MOLD.get(),
				Registration.FIRED_CERAMIC_PICKAXE_MOLD.get(),
				Registration.FIRED_CERAMIC_PROPICK_MOLD.get(),
				Registration.FIRED_CERAMIC_SWORD_MOLD.get(),
				Registration.PRIMITIVE_BRICK_MOLD.get()
			});
		}
		return null;
	}
	
	public static void init(final FMLCommonSetupEvent event) {
		HeatCapability.init();
		oreSorter = Ordering.explicit(getItemList(TECHMOD_ORES)).onResultOf(ItemStack::getItem);
		machineSorter = Ordering.explicit(getItemList(TECHMOD_MACHINES)).onResultOf(ItemStack::getItem);
		materialSorter = Ordering.explicit(getItemList(TECHMOD_MATERIALS)).onResultOf(ItemStack::getItem);
		toolSorter = Ordering.explicit(getItemList(TECHMOD_TOOLS)).onResultOf(ItemStack::getItem);
		miscSorter = Ordering.explicit(getItemList(TECHMOD_MISC)).onResultOf(ItemStack::getItem);
		CapabilityManager.INSTANCE.register(IWorldgenCapability.class, new WorldgenCapStorage(), WorldgenCapability::new);
		DeferredWorkQueue.runLater(FeatureStripper::strip);
		OreDepositRegistration.getInstance().init();
		OreAPI.plutonRegistry.registerAsOreGenerator();
		
	}
}
