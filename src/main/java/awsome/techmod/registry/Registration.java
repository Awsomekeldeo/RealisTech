package awsome.techmod.registry;

import awsome.techmod.Reference;
import awsome.techmod.blocks.CrucibleBlock;
import awsome.techmod.blocks.FireboxBlock;
import awsome.techmod.blocks.ModOreBlock;
import awsome.techmod.blocks.OreSampleBlock;
import awsome.techmod.inventory.container.CrucibleContainer;
import awsome.techmod.inventory.container.FireboxContainer;
import awsome.techmod.items.IngotItem;
import awsome.techmod.items.OreClusterItem;
import awsome.techmod.setup.ModSetup;
import awsome.techmod.tileentity.CrucibleTileEntity;
import awsome.techmod.tileentity.FireboxTileEntity;
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
		public static final RegistryObject<ModOreBlock> COPPER_ORE = BLOCKS.register("copper_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> TIN_ORE = BLOCKS.register("tin_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> LEAD_ORE = BLOCKS.register("lead_ore", () -> new ModOreBlock(2));
		public static final RegistryObject<ModOreBlock> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new ModOreBlock(2));
		public static final RegistryObject<ModOreBlock> SILVER_ORE = BLOCKS.register("silver_ore", () -> new ModOreBlock(2));
		public static final RegistryObject<ModOreBlock> COBALT_ORE = BLOCKS.register("cobalt_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> ZINC_ORE = BLOCKS.register("zinc_ore", () -> new ModOreBlock(2));
		public static final RegistryObject<ModOreBlock> MOD_IRON_ORE = BLOCKS.register("mod_iron_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_GOLD_ORE = BLOCKS.register("mod_gold_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_COAL_ORE = BLOCKS.register("mod_coal_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_REDSTONE_ORE = BLOCKS.register("mod_redstone_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_EMERALD_ORE = BLOCKS.register("mod_emerald_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_DIAMOND_ORE = BLOCKS.register("mod_diamond_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_LAPIS_ORE = BLOCKS.register("mod_lapis_ore", () -> new ModOreBlock(1));
		
		//Ore Samples
		public static final RegistryObject<OreSampleBlock> COPPER_SAMPLE = BLOCKS.register("copper_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> TIN_SAMPLE = BLOCKS.register("tin_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> NICKEL_SAMPLE = BLOCKS.register("nickel_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> LEAD_SAMPLE = BLOCKS.register("lead_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> SILVER_SAMPLE = BLOCKS.register("silver_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> COBALT_SAMPLE = BLOCKS.register("cobalt_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> ZINC_SAMPLE = BLOCKS.register("zinc_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> COAL_SAMPLE = BLOCKS.register("coal_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> IRON_SAMPLE = BLOCKS.register("iron_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> GOLD_SAMPLE = BLOCKS.register("gold_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> DIAMOND_SAMPLE = BLOCKS.register("diamond_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> EMERALD_SAMPLE = BLOCKS.register("emerald_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> LAPIS_SAMPLE = BLOCKS.register("lapis_sample", () -> new OreSampleBlock(1));
		public static final RegistryObject<OreSampleBlock> REDSTONE_SAMPLE = BLOCKS.register("redstone_sample", () -> new OreSampleBlock(1));
		
		
		//Machines
		public static final RegistryObject<FireboxBlock> FIREBOX = BLOCKS.register("firebox", FireboxBlock::new);
		public static final RegistryObject<CrucibleBlock> CRUCIBLE = BLOCKS.register("crucible", CrucibleBlock::new);
	
	//Tile Entities
		
		//Machines
		public static final RegistryObject<TileEntityType<FireboxTileEntity>> FIREBOX_TILEENTITY = TILE_ENTITIES.register("firebox", () -> TileEntityType.Builder.create(FireboxTileEntity::new, FIREBOX.get()).build(null));
		public static final RegistryObject<TileEntityType<CrucibleTileEntity>> CRUCIBLE_TILEENTITY = TILE_ENTITIES.register("crucible", () -> TileEntityType.Builder.create(CrucibleTileEntity::new, CRUCIBLE.get()).build(null));
	
	//Containers
		
		//Machines
		public static final RegistryObject<ContainerType<FireboxContainer>> FIREBOX_CONTAINER = CONTAINERS.register("firebox", () -> IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			World world = inv.player.getEntityWorld();
			return new FireboxContainer(windowId, world, pos, inv, inv.player);
		}));
		
		public static final RegistryObject<ContainerType<CrucibleContainer>> CRUCIBLE_CONTAINER = CONTAINERS.register("crucible", () -> IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			World world = inv.player.getEntityWorld();
			return new CrucibleContainer(windowId, world, pos, inv, inv.player);
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
		public static final RegistryObject<IngotItem> COPPER_INGOT = ITEMS.register("ingots/copper_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> TIN_INGOT = ITEMS.register("ingots/tin_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> NICKEL_INGOT = ITEMS.register("ingots/nickel_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> LEAD_INGOT = ITEMS.register("ingots/lead_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> SILVER_INGOT = ITEMS.register("ingots/silver_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> ZINC_INGOT = ITEMS.register("ingots/zinc_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> COBALT_INGOT = ITEMS.register("ingots/cobalt_ingot", IngotItem::new);
		
		//Clusters
		public static final RegistryObject<OreClusterItem> COPPER_CLUSTER = ITEMS.register("ore_clusters/copper_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> TIN_CLUSTER = ITEMS.register("ore_clusters/tin_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> NICKEL_CLUSTER = ITEMS.register("ore_clusters/nickel_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> SILVER_CLUSTER = ITEMS.register("ore_clusters/silver_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> LEAD_CLUSTER = ITEMS.register("ore_clusters/lead_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> ZINC_CLUSTER = ITEMS.register("ore_clusters/zinc_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> COBALT_CLUSTER = ITEMS.register("ore_clusters/cobalt_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> IRON_CLUSTER = ITEMS.register("ore_clusters/iron_cluster", OreClusterItem::new);
		public static final RegistryObject<OreClusterItem> GOLD_CLUSTER = ITEMS.register("ore_clusters/gold_cluster", OreClusterItem::new);
}
