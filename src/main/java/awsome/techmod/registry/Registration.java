package awsome.techmod.registry;

import awsome.techmod.Reference;
import awsome.techmod.blocks.BlockCrucible;
import awsome.techmod.blocks.BlockFirebox;
import awsome.techmod.blocks.BlockModOre;
import awsome.techmod.blocks.BlockOreSample;
import awsome.techmod.inventory.container.ContainerCrucible;
import awsome.techmod.inventory.container.ContainerFirebox;
import awsome.techmod.items.ItemIngot;
import awsome.techmod.items.ItemOreCluster;
import awsome.techmod.setup.ModSetup;
import awsome.techmod.tileentity.TECrucible;
import awsome.techmod.tileentity.TEFirebox;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MODID);
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
	
	
	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	//Blocks
		
		//Ores
		public static final RegistryObject<BlockModOre> COPPER_ORE = BLOCKS.register("copper_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> TIN_ORE = BLOCKS.register("tin_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> LEAD_ORE = BLOCKS.register("lead_ore", () -> new BlockModOre(2));
		public static final RegistryObject<BlockModOre> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new BlockModOre(2));
		public static final RegistryObject<BlockModOre> SILVER_ORE = BLOCKS.register("silver_ore", () -> new BlockModOre(2));
		public static final RegistryObject<BlockModOre> COBALT_ORE = BLOCKS.register("cobalt_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> ZINC_ORE = BLOCKS.register("zinc_ore", () -> new BlockModOre(2));
		public static final RegistryObject<BlockModOre> MOD_IRON_ORE = BLOCKS.register("mod_iron_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> MOD_GOLD_ORE = BLOCKS.register("mod_gold_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> MOD_COAL_ORE = BLOCKS.register("mod_coal_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> MOD_REDSTONE_ORE = BLOCKS.register("mod_redstone_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> MOD_EMERALD_ORE = BLOCKS.register("mod_emerald_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> MOD_DIAMOND_ORE = BLOCKS.register("mod_diamond_ore", () -> new BlockModOre(1));
		public static final RegistryObject<BlockModOre> MOD_LAPIS_ORE = BLOCKS.register("mod_lapis_ore", () -> new BlockModOre(1));
		
		//Ore Samples
		public static final RegistryObject<BlockOreSample> COPPER_SAMPLE = BLOCKS.register("copper_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> TIN_SAMPLE = BLOCKS.register("tin_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> NICKEL_SAMPLE = BLOCKS.register("nickel_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> LEAD_SAMPLE = BLOCKS.register("lead_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> SILVER_SAMPLE = BLOCKS.register("silver_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> COBALT_SAMPLE = BLOCKS.register("cobalt_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> ZINC_SAMPLE = BLOCKS.register("zinc_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> COAL_SAMPLE = BLOCKS.register("coal_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> IRON_SAMPLE = BLOCKS.register("iron_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> GOLD_SAMPLE = BLOCKS.register("gold_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> DIAMOND_SAMPLE = BLOCKS.register("diamond_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> EMERALD_SAMPLE = BLOCKS.register("emerald_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> LAPIS_SAMPLE = BLOCKS.register("lapis_sample", () -> new BlockOreSample(1));
		public static final RegistryObject<BlockOreSample> REDSTONE_SAMPLE = BLOCKS.register("redstone_sample", () -> new BlockOreSample(1));
		
		
		//Machines
		public static final RegistryObject<BlockFirebox> FIREBOX = BLOCKS.register("firebox", BlockFirebox::new);
		public static final RegistryObject<BlockCrucible> CRUCIBLE = BLOCKS.register("crucible", BlockCrucible::new);
	
	//Tile Entities
		
		//Machines
		public static final RegistryObject<TileEntityType<TEFirebox>> FIREBOX_TILEENTITY = TILE_ENTITIES.register("firebox", () -> TileEntityType.Builder.create(TEFirebox::new, FIREBOX.get()).build(null));
		public static final RegistryObject<TileEntityType<TECrucible>> CRUCIBLE_TILEENTITY = TILE_ENTITIES.register("crucible", () -> TileEntityType.Builder.create(TECrucible::new, CRUCIBLE.get()).build(null));
	
	//Containers
		
		//Machines
		public static final RegistryObject<ContainerType<ContainerFirebox>> FIREBOX_CONTAINER = CONTAINERS.register("firebox", () -> IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			World world = inv.player.getEntityWorld();
			return new ContainerFirebox(windowId, world, pos, inv, inv.player);
		}));
		
		public static final RegistryObject<ContainerType<ContainerCrucible>> CRUCIBLE_CONTAINER = CONTAINERS.register("crucible", () -> IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			World world = inv.player.getEntityWorld();
			return new ContainerCrucible(windowId, world, pos, inv, inv.player);
		}));
		
	
	//Item Blocks
		
		//Ores
		public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("ores/copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> TIN_ORE_ITEM = ITEMS.register("ores/tin_ore", () -> new BlockItem(TIN_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> NICKEL_ORE_ITEM = ITEMS.register("ores/nickel_ore", () -> new BlockItem(NICKEL_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("ores/silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("ores/lead_ore", () -> new BlockItem(LEAD_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> ZINC_ORE_ITEM = ITEMS.register("ores/zinc_ore", () -> new BlockItem(ZINC_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> COBALT_ORE_ITEM = ITEMS.register("ores/cobalt_ore", () -> new BlockItem(COBALT_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_IRON_ORE_ITEM = ITEMS.register("ores/mod_iron_ore", () -> new BlockItem(MOD_IRON_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_GOLD_ORE_ITEM = ITEMS.register("ores/mod_gold_ore", () -> new BlockItem(MOD_GOLD_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_COAL_ORE_ITEM = ITEMS.register("ores/mod_coal_ore", () -> new BlockItem(MOD_COAL_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_DIAMOND_ORE_ITEM = ITEMS.register("ores/mod_diamond_ore", () -> new BlockItem(MOD_DIAMOND_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_EMERALD_ORE_ITEM = ITEMS.register("ores/mod_emerald_ore", () -> new BlockItem(MOD_EMERALD_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_LAPIS_ORE_ITEM = ITEMS.register("ores/mod_lapis_ore", () -> new BlockItem(MOD_LAPIS_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> MOD_REDSTONE_ORE_ITEM = ITEMS.register("ores/mod_redstone_ore", () -> new BlockItem(MOD_REDSTONE_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		
		//Samples
		public static final RegistryObject<Item> COPPER_SAMPLE_ITEM = ITEMS.register("ores/samples/copper_sample", () -> new BlockItem(COPPER_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> TIN_SAMPLE_ITEM = ITEMS.register("ores/samples/tin_sample", () -> new BlockItem(TIN_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> NICKEL_SAMPLE_ITEM = ITEMS.register("ores/samples/nickel_sample", () -> new BlockItem(NICKEL_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> SILVER_SAMPLE_ITEM = ITEMS.register("ores/samples/silver_sample", () -> new BlockItem(SILVER_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> LEAD_SAMPLE_ITEM = ITEMS.register("ores/samples/lead_sample", () -> new BlockItem(LEAD_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> COBALT_SAMPLE_ITEM = ITEMS.register("ores/samples/cobalt_sample", () -> new BlockItem(COBALT_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> ZINC_SAMPLE_ITEM = ITEMS.register("ores/samples/zinc_sample", () -> new BlockItem(ZINC_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> COAL_SAMPLE_ITEM = ITEMS.register("ores/samples/coal_sample", () -> new BlockItem(COAL_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> IRON_SAMPLE_ITEM = ITEMS.register("ores/samples/iron_sample", () -> new BlockItem(IRON_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> GOLD_SAMPLE_ITEM = ITEMS.register("ores/samples/gold_sample", () -> new BlockItem(GOLD_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> DIAMOND_SAMPLE_ITEM = ITEMS.register("ores/samples/diamond_sample", () -> new BlockItem(DIAMOND_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> EMERALD_SAMPLE_ITEM = ITEMS.register("ores/samples/emerald_sample", () -> new BlockItem(EMERALD_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> REDSTONE_SAMPLE_ITEM = ITEMS.register("ores/samples/redstone_sample", () -> new BlockItem(REDSTONE_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		public static final RegistryObject<Item> LAPIS_SAMPLE_ITEM = ITEMS.register("ores/samples/lapis_sample", () -> new BlockItem(LAPIS_SAMPLE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
		
		//Machines
		public static final RegistryObject<Item> FIREBOX_ITEM = ITEMS.register("machines/firebox", () -> new BlockItem(FIREBOX.get(), new Item.Properties().group(ModSetup.TECHMOD_MACHINES)));
		public static final RegistryObject<Item> CRUCIBLE_ITEM = ITEMS.register("machines/crucible", () -> new BlockItem(CRUCIBLE.get(), new Item.Properties().group(ModSetup.TECHMOD_MACHINES)));
		
	//Items
		
		//Ingots
		public static final RegistryObject<ItemIngot> COPPER_INGOT = ITEMS.register("ingots/copper_ingot", ItemIngot::new);
		public static final RegistryObject<ItemIngot> TIN_INGOT = ITEMS.register("ingots/tin_ingot", ItemIngot::new);
		public static final RegistryObject<ItemIngot> NICKEL_INGOT = ITEMS.register("ingots/nickel_ingot", ItemIngot::new);
		public static final RegistryObject<ItemIngot> LEAD_INGOT = ITEMS.register("ingots/lead_ingot", ItemIngot::new);
		public static final RegistryObject<ItemIngot> SILVER_INGOT = ITEMS.register("ingots/silver_ingot", ItemIngot::new);
		public static final RegistryObject<ItemIngot> ZINC_INGOT = ITEMS.register("ingots/zinc_ingot", ItemIngot::new);
		public static final RegistryObject<ItemIngot> COBALT_INGOT = ITEMS.register("ingots/cobalt_ingot", ItemIngot::new);
		
		//Clusters
		public static final RegistryObject<ItemOreCluster> COPPER_CLUSTER = ITEMS.register("ore_clusters/copper_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> TIN_CLUSTER = ITEMS.register("ore_clusters/tin_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> NICKEL_CLUSTER = ITEMS.register("ore_clusters/nickel_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> SILVER_CLUSTER = ITEMS.register("ore_clusters/silver_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> LEAD_CLUSTER = ITEMS.register("ore_clusters/lead_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> ZINC_CLUSTER = ITEMS.register("ore_clusters/zinc_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> COBALT_CLUSTER = ITEMS.register("ore_clusters/cobalt_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> IRON_CLUSTER = ITEMS.register("ore_clusters/iron_cluster", ItemOreCluster::new);
		public static final RegistryObject<ItemOreCluster> GOLD_CLUSTER = ITEMS.register("ore_clusters/gold_cluster", ItemOreCluster::new);
}
