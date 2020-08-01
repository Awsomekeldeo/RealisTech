package awsome.techmod.registry;

import awsome.techmod.Reference;
import awsome.techmod.blocks.BlockFirebox;
import awsome.techmod.blocks.BlockModOre;
import awsome.techmod.inventory.container.ContainerFirebox;
import awsome.techmod.items.ItemIngot;
import awsome.techmod.items.ItemOreCluster;
import awsome.techmod.setup.ModSetup;
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
	public static final RegistryObject<BlockFirebox> FIREBOX = BLOCKS.register("firebox", BlockFirebox::new);
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
	
	//Tile Entities
	public static final RegistryObject<TileEntityType<TEFirebox>> FIREBOX_TILEENTITY = TILE_ENTITIES.register("firebox", () -> TileEntityType.Builder.create(TEFirebox::new, FIREBOX.get()).build(null));
	
	//Containers
	public static final RegistryObject<ContainerType<ContainerFirebox>> FIREBOX_CONTAINER = CONTAINERS.register("firebox", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		World world = inv.player.getEntityWorld();
		return new ContainerFirebox(windowId, world, pos, inv, inv.player);
	}));
	
	//Item Blocks
	public static final RegistryObject<Item> FIREBOX_ITEM = ITEMS.register("firebox", () -> new BlockItem(FIREBOX.get(), new Item.Properties().group(ModSetup.TECHMOD_MACHINES)));
	public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> TIN_ORE_ITEM = ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> NICKEL_ORE_ITEM = ITEMS.register("nickel_ore", () -> new BlockItem(NICKEL_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItem(LEAD_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> ZINC_ORE_ITEM = ITEMS.register("zinc_ore", () -> new BlockItem(ZINC_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> COBALT_ORE_ITEM = ITEMS.register("cobalt_ore", () -> new BlockItem(COBALT_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_IRON_ORE_ITEM = ITEMS.register("mod_iron_ore", () -> new BlockItem(MOD_IRON_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_GOLD_ORE_ITEM = ITEMS.register("mod_gold_ore", () -> new BlockItem(MOD_GOLD_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_COAL_ORE_ITEM = ITEMS.register("mod_coal_ore", () -> new BlockItem(MOD_COAL_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_DIAMOND_ORE_ITEM = ITEMS.register("mod_diamond_ore", () -> new BlockItem(MOD_DIAMOND_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_EMERALD_ORE_ITEM = ITEMS.register("mod_emerald_ore", () -> new BlockItem(MOD_EMERALD_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_LAPIS_ORE_ITEM = ITEMS.register("mod_lapis_ore", () -> new BlockItem(MOD_LAPIS_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	public static final RegistryObject<Item> MOD_REDSTONE_ORE_ITEM = ITEMS.register("mod_redstone_ore", () -> new BlockItem(MOD_REDSTONE_ORE.get(), new Item.Properties().group(ModSetup.TECHMOD_ORES)));
	
	//Items
	public static final RegistryObject<ItemIngot> COPPER_INGOT = ITEMS.register("copper_ingot", ItemIngot::new);
	public static final RegistryObject<ItemIngot> TIN_INGOT = ITEMS.register("tin_ingot", ItemIngot::new);
	public static final RegistryObject<ItemIngot> NICKEL_INGOT = ITEMS.register("nickel_ingot", ItemIngot::new);
	public static final RegistryObject<ItemIngot> LEAD_INGOT = ITEMS.register("lead_ingot", ItemIngot::new);
	public static final RegistryObject<ItemIngot> SILVER_INGOT = ITEMS.register("silver_ingot", ItemIngot::new);
	public static final RegistryObject<ItemIngot> ZINC_INGOT = ITEMS.register("zinc_ingot", ItemIngot::new);
	public static final RegistryObject<ItemIngot> COBALT_INGOT = ITEMS.register("cobalt_ingot", ItemIngot::new);
	public static final RegistryObject<ItemOreCluster> COPPER_CLUSTER = ITEMS.register("copper_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> TIN_CLUSTER = ITEMS.register("tin_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> NICKEL_CLUSTER = ITEMS.register("nickel_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> SILVER_CLUSTER = ITEMS.register("silver_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> LEAD_CLUSTER = ITEMS.register("lead_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> ZINC_CLUSTER = ITEMS.register("zinc_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> COBALT_CLUSTER = ITEMS.register("cobalt_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> IRON_CLUSTER = ITEMS.register("iron_cluster", ItemOreCluster::new);
	public static final RegistryObject<ItemOreCluster> GOLD_CLUSTER = ITEMS.register("gold_cluster", ItemOreCluster::new);
}
