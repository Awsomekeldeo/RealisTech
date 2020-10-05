package awsome.realistech.registry;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.HandworkRecipeSerializer;
import awsome.realistech.api.recipe.KilnRecipe;
import awsome.realistech.api.recipe.KilnRecipeSerializer;
import awsome.realistech.api.recipe.KnappingRecipe;
import awsome.realistech.api.recipe.MeltingRecipe;
import awsome.realistech.api.recipe.MeltingRecipeSerializer;
import awsome.realistech.api.recipe.MoldingRecipe;
import awsome.realistech.api.recipe.NonConsumingShapelessRecipe;
import awsome.realistech.api.recipe.NonConsumingShaplessRecipeSerializer;
import awsome.realistech.blocks.ClayGrassBlock;
import awsome.realistech.blocks.CrucibleBlock;
import awsome.realistech.blocks.FireboxBlock;
import awsome.realistech.blocks.KilnBlock;
import awsome.realistech.blocks.ModOreBlock;
import awsome.realistech.blocks.OreSampleBlock;
import awsome.realistech.blocks.OreSampleBlock.SampleShape;
import awsome.realistech.data.loot.GrassDrops.GrassDropSerializer;
import awsome.realistech.inventory.container.CrucibleContainer;
import awsome.realistech.inventory.container.FireboxContainer;
import awsome.realistech.inventory.container.KilnContainer;
import awsome.realistech.inventory.container.KnappingContainer;
import awsome.realistech.inventory.container.MoldingContainer;
import awsome.realistech.items.BrickItem;
import awsome.realistech.items.CeramicMoldItem;
import awsome.realistech.items.ChiselItem;
import awsome.realistech.items.FireclayItem;
import awsome.realistech.items.HammerItem;
import awsome.realistech.items.IngotItem;
import awsome.realistech.items.MortarItem;
import awsome.realistech.items.OreClusterItem;
import awsome.realistech.items.ToolHeadItem;
import awsome.realistech.items.UnfiredCeramicMoldItem;
import awsome.realistech.setup.ModSetup;
import awsome.realistech.tileentity.CrucibleTileEntity;
import awsome.realistech.tileentity.FireboxTileEntity;
import awsome.realistech.tileentity.KilnTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MODID);
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
	private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MODID);
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Reference.MODID);
	
	
	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	//Global Loot Modifiers
		public static final RegistryObject<GrassDropSerializer> GRASS_DROP_OVERRIDE = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("grass_override", GrassDropSerializer::new);
	
	//Recipe Serializers
		public static final RegistryObject<MeltingRecipeSerializer<MeltingRecipe>> MELTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("melting", () -> new MeltingRecipeSerializer<>(MeltingRecipe::new));
		public static final RegistryObject<KilnRecipeSerializer<KilnRecipe>> KILN_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("kiln", () -> new KilnRecipeSerializer<>(KilnRecipe::new));
		public static final RegistryObject<NonConsumingShaplessRecipeSerializer<NonConsumingShapelessRecipe>> NON_CONSUMING_SHAPELESS_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("non_consuming_shapeless", () -> new NonConsumingShaplessRecipeSerializer<>(NonConsumingShapelessRecipe::new));
		public static final RegistryObject<HandworkRecipeSerializer<KnappingRecipe>> KNAPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("knapping", () -> new HandworkRecipeSerializer<>(KnappingRecipe::new));
		public static final RegistryObject<HandworkRecipeSerializer<MoldingRecipe>> MOLDING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("molding", () -> new HandworkRecipeSerializer<>(MoldingRecipe::new));
	
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
		public static final RegistryObject<ModOreBlock> MOD_GOLD_ORE = BLOCKS.register("mod_gold_ore", () -> new ModOreBlock(2));
		public static final RegistryObject<ModOreBlock> MOD_COAL_ORE = BLOCKS.register("mod_coal_ore", () -> new ModOreBlock(0));
		public static final RegistryObject<ModOreBlock> MOD_REDSTONE_ORE = BLOCKS.register("mod_redstone_ore", () -> new ModOreBlock(1));
		public static final RegistryObject<ModOreBlock> MOD_EMERALD_ORE = BLOCKS.register("mod_emerald_ore", () -> new ModOreBlock(2));
		public static final RegistryObject<ModOreBlock> MOD_DIAMOND_ORE = BLOCKS.register("mod_diamond_ore", () -> new ModOreBlock(2));
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
		public static final RegistryObject<Block> ROCK = BLOCKS.register("rock", () -> new OreSampleBlock(-1, SoundType.STONE, SampleShape.ROCKLIKE));
		public static final RegistryObject<Block> STICK = BLOCKS.register("stick", () -> new OreSampleBlock(-1, SoundType.WOOD, SampleShape.STICK));
		
		//Flowers
		public static final RegistryObject<FlowerBlock> GOLDENROD = BLOCKS.register("goldenrod", () -> new FlowerBlock(Effects.GLOWING, 8, Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		public static final RegistryObject<FlowerBlock> KAOLINITE_LILY = BLOCKS.register("kaolinite_lily", () -> new FlowerBlock(Effects.BAD_OMEN, 8, Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		
		//Machines
		public static final RegistryObject<FireboxBlock> FIREBOX = BLOCKS.register("firebox", FireboxBlock::new);
		public static final RegistryObject<CrucibleBlock> CRUCIBLE = BLOCKS.register("crucible", CrucibleBlock::new);
		public static final RegistryObject<KilnBlock> KILN = BLOCKS.register("kiln", KilnBlock::new);
		
		//Clay Grasses
		public static final RegistryObject<ClayGrassBlock> VANILLA_CLAY_GRASS = BLOCKS.register("clay_grass", ClayGrassBlock::new);
		public static final RegistryObject<ClayGrassBlock> KAOLINITE_CLAY_GRASS = BLOCKS.register("kaolinite_clay_grass", ClayGrassBlock::new);
		
		//Clays
		public static final RegistryObject<Block> KAOLINITE_CLAY = BLOCKS.register("kaolinite_clay", () -> new Block(Properties.create(Material.CLAY).hardnessAndResistance(0.6f).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(-1)));
	
	//Tile Entities
		
		//Machines
		public static final RegistryObject<TileEntityType<FireboxTileEntity>> FIREBOX_TILEENTITY = TILE_ENTITIES.register("firebox", () -> TileEntityType.Builder.create(FireboxTileEntity::new, FIREBOX.get()).build(null));
		public static final RegistryObject<TileEntityType<CrucibleTileEntity>> CRUCIBLE_TILEENTITY = TILE_ENTITIES.register("crucible", () -> TileEntityType.Builder.create(CrucibleTileEntity::new, CRUCIBLE.get()).build(null));
		public static final RegistryObject<TileEntityType<KilnTileEntity>> KILN_TILEENTITY = TILE_ENTITIES.register("kiln", () -> TileEntityType.Builder.create(KilnTileEntity::new, KILN.get()).build(null));
	
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
		
		public static final RegistryObject<ContainerType<KilnContainer>> KILN_CONTAINER = CONTAINERS.register("kiln", () -> IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			World world = inv.player.getEntityWorld();
			return new KilnContainer(windowId, world, pos, inv, inv.player);
		}));
		
		
		//Crafting Inventories
		
		public static final RegistryObject<ContainerType<KnappingContainer>> KNAPPING_CRAFTING_CONTAINER = CONTAINERS.register("knapping", () -> IForgeContainerType.create((windowId, inv, data) -> {
			return new KnappingContainer(windowId, inv);
		}));
		
		public static final RegistryObject<ContainerType<MoldingContainer>> MOLDING_CRAFTING_CONTAINER = CONTAINERS.register("molding", () -> IForgeContainerType.create((windowId, inv, data) -> {
			return new MoldingContainer(windowId, inv);
		}));
		
	
	//Item Blocks
		
		//Ores
		public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("ores/copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> TIN_ORE_ITEM = ITEMS.register("ores/tin_ore", () -> new BlockItem(TIN_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> NICKEL_ORE_ITEM = ITEMS.register("ores/nickel_ore", () -> new BlockItem(NICKEL_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("ores/silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("ores/lead_ore", () -> new BlockItem(LEAD_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> ZINC_ORE_ITEM = ITEMS.register("ores/zinc_ore", () -> new BlockItem(ZINC_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> COBALT_ORE_ITEM = ITEMS.register("ores/cobalt_ore", () -> new BlockItem(COBALT_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_IRON_ORE_ITEM = ITEMS.register("ores/mod_iron_ore", () -> new BlockItem(MOD_IRON_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_GOLD_ORE_ITEM = ITEMS.register("ores/mod_gold_ore", () -> new BlockItem(MOD_GOLD_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_COAL_ORE_ITEM = ITEMS.register("ores/mod_coal_ore", () -> new BlockItem(MOD_COAL_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_DIAMOND_ORE_ITEM = ITEMS.register("ores/mod_diamond_ore", () -> new BlockItem(MOD_DIAMOND_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_EMERALD_ORE_ITEM = ITEMS.register("ores/mod_emerald_ore", () -> new BlockItem(MOD_EMERALD_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_LAPIS_ORE_ITEM = ITEMS.register("ores/mod_lapis_ore", () -> new BlockItem(MOD_LAPIS_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> MOD_REDSTONE_ORE_ITEM = ITEMS.register("ores/mod_redstone_ore", () -> new BlockItem(MOD_REDSTONE_ORE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		
		//Samples
		public static final RegistryObject<Item> COPPER_SAMPLE_ITEM = ITEMS.register("ores/samples/copper_sample", () -> new BlockItem(COPPER_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> TIN_SAMPLE_ITEM = ITEMS.register("ores/samples/tin_sample", () -> new BlockItem(TIN_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> NICKEL_SAMPLE_ITEM = ITEMS.register("ores/samples/nickel_sample", () -> new BlockItem(NICKEL_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> SILVER_SAMPLE_ITEM = ITEMS.register("ores/samples/silver_sample", () -> new BlockItem(SILVER_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> LEAD_SAMPLE_ITEM = ITEMS.register("ores/samples/lead_sample", () -> new BlockItem(LEAD_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> COBALT_SAMPLE_ITEM = ITEMS.register("ores/samples/cobalt_sample", () -> new BlockItem(COBALT_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> ZINC_SAMPLE_ITEM = ITEMS.register("ores/samples/zinc_sample", () -> new BlockItem(ZINC_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> COAL_SAMPLE_ITEM = ITEMS.register("ores/samples/coal_sample", () -> new BlockItem(COAL_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> IRON_SAMPLE_ITEM = ITEMS.register("ores/samples/iron_sample", () -> new BlockItem(IRON_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> GOLD_SAMPLE_ITEM = ITEMS.register("ores/samples/gold_sample", () -> new BlockItem(GOLD_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> DIAMOND_SAMPLE_ITEM = ITEMS.register("ores/samples/diamond_sample", () -> new BlockItem(DIAMOND_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> EMERALD_SAMPLE_ITEM = ITEMS.register("ores/samples/emerald_sample", () -> new BlockItem(EMERALD_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> REDSTONE_SAMPLE_ITEM = ITEMS.register("ores/samples/redstone_sample", () -> new BlockItem(REDSTONE_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> LAPIS_SAMPLE_ITEM = ITEMS.register("ores/samples/lapis_sample", () -> new BlockItem(LAPIS_SAMPLE.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> ROCK_ITEMBLOCK = ITEMS.register("rock_blockitem", () -> new BlockItem(ROCK.get(), new Item.Properties().group(ModSetup.REALISTECH_ORES)));
		public static final RegistryObject<Item> STICK_ITEMBLOCK = ITEMS.register("inworld_stick", () -> new BlockItem(STICK.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
		//Machines
		public static final RegistryObject<Item> FIREBOX_ITEM = ITEMS.register("machines/firebox", () -> new BlockItem(FIREBOX.get(), new Item.Properties().group(ModSetup.REALISTECH_MACHINES)));
		public static final RegistryObject<Item> CRUCIBLE_ITEM = ITEMS.register("machines/crucible", () -> new BlockItem(CRUCIBLE.get(), new Item.Properties().group(ModSetup.REALISTECH_MACHINES)));
		public static final RegistryObject<Item> KILN_ITEM = ITEMS.register("machines/kiln", () -> new BlockItem(KILN.get(), new Item.Properties().group(ModSetup.REALISTECH_MACHINES)));
		
		//Flowers
		public static final RegistryObject<Item> GOLDENROD_ITEM = ITEMS.register("goldenrod", () -> new BlockItem(GOLDENROD.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		public static final RegistryObject<Item> KAOLINITE_LILY_ITEM = ITEMS.register("kaolinite_lily", () -> new BlockItem(KAOLINITE_LILY.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
		//Clay Grasses
		public static final RegistryObject<Item> VANILLA_CLAY_GRASS_ITEM = ITEMS.register("clay_grass", () -> new BlockItem(VANILLA_CLAY_GRASS.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		public static final RegistryObject<Item> KAOLINITE_CLAY_GRASS_ITEM = ITEMS.register("kaolinite_clay_grass", () -> new BlockItem(KAOLINITE_CLAY_GRASS.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
		//Clays
		public static final RegistryObject<Item> KAOLINITE_CLAY_ITEM = ITEMS.register("kaolinite_clay", () -> new BlockItem(KAOLINITE_CLAY.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
	//Items
		
		//Ingots
		public static final RegistryObject<IngotItem> COPPER_INGOT = ITEMS.register("ingots/copper_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> TIN_INGOT = ITEMS.register("ingots/tin_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> NICKEL_INGOT = ITEMS.register("ingots/nickel_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> LEAD_INGOT = ITEMS.register("ingots/lead_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> SILVER_INGOT = ITEMS.register("ingots/silver_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> ZINC_INGOT = ITEMS.register("ingots/zinc_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> COBALT_INGOT = ITEMS.register("ingots/cobalt_ingot", IngotItem::new);
		public static final RegistryObject<IngotItem> CRUDE_COPPER_INGOT = ITEMS.register("ingots/crude_copper_ingot", IngotItem::new);
		
		//Plates
		public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("plates/copper_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> TIN_PLATE = ITEMS.register("plates/tin_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> NICKEL_PLATE = ITEMS.register("plates/nickel_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> LEAD_PLATE = ITEMS.register("plates/lead_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> SILVER_PLATE = ITEMS.register("plates/silver_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> ZINC_PLATE = ITEMS.register("plates/zinc_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> COBALT_PLATE = ITEMS.register("plates/cobalt_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		
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
		
		//Chunks
		public static final RegistryObject<Item> COPPER_CHUNK = ITEMS.register("chunks/copper_chunk", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		
		//Ceramic Molds
		
			//Fired
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_INGOT_MOLD = ITEMS.register("ceramic_molds/fired/ingot", () -> new CeramicMoldItem(144));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_PICKAXE_MOLD = ITEMS.register("ceramic_molds/fired/pickaxe", () -> new CeramicMoldItem(432));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_AXE_MOLD = ITEMS.register("ceramic_molds/fired/axe", () -> new CeramicMoldItem(144));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_SHOVEL_MOLD = ITEMS.register("ceramic_molds/fired/shovel", () -> new CeramicMoldItem(144));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_SWORD_MOLD = ITEMS.register("ceramic_molds/fired/sword", () -> new CeramicMoldItem(144));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_PROPICK_MOLD = ITEMS.register("ceramic_molds/fired/prospectors_pickaxe", () -> new CeramicMoldItem(144));
			
			//Unfired
			public static final RegistryObject<UnfiredCeramicMoldItem> UNFIRED_CERAMIC_INGOT_MOLD = ITEMS.register("ceramic_molds/unfired/ingot", UnfiredCeramicMoldItem::new);
			public static final RegistryObject<UnfiredCeramicMoldItem> UNFIRED_CERAMIC_PICKAXE_MOLD = ITEMS.register("ceramic_molds/unfired/pickaxe", UnfiredCeramicMoldItem::new);
			public static final RegistryObject<UnfiredCeramicMoldItem> UNFIRED_CERAMIC_AXE_MOLD = ITEMS.register("ceramic_molds/unfired/axe", UnfiredCeramicMoldItem::new);
			public static final RegistryObject<UnfiredCeramicMoldItem> UNFIRED_CERAMIC_SHOVEL_MOLD = ITEMS.register("ceramic_molds/unfired/shovel", UnfiredCeramicMoldItem::new);
			public static final RegistryObject<UnfiredCeramicMoldItem> UNFIRED_CERAMIC_SWORD_MOLD = ITEMS.register("ceramic_molds/unfired/sword", UnfiredCeramicMoldItem::new);
			public static final RegistryObject<UnfiredCeramicMoldItem> UNFIRED_CERAMIC_PROPICK_MOLD = ITEMS.register("ceramic_molds/unfired/prospectors_pickaxe", UnfiredCeramicMoldItem::new);
			
		//Ceramics
		public static final RegistryObject<Item> UNFIRED_CLAY_BRICK = ITEMS.register("unfired_clay_brick", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> PRIMITIVE_BRICK_MOLD = ITEMS.register("primitive_brick_mold", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MISC).maxStackSize(1)));
		public static final RegistryObject<Item> UNFIRED_KILN_BRICK = ITEMS.register("unfired_kiln_brick", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<BrickItem> KILN_BRICK = ITEMS.register("kiln_brick", BrickItem::new);
		public static final RegistryObject<Item> KILN_CLAY_BALL = ITEMS.register("kiln_clay_ball", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<FireclayItem> KAOLINITE_CLAY_BALL = ITEMS.register("kaolinite_clay_ball", FireclayItem::new);
		public static final RegistryObject<FireclayItem> UNFIRED_KAOLINITE_BRICK = ITEMS.register("unfired_kaolinite_brick", FireclayItem::new);
		public static final RegistryObject<BrickItem> FIREBRICK = ITEMS.register("firebrick", BrickItem::new);
		
		//Tool Heads
		
			//Stone
			public static final RegistryObject<ToolHeadItem> STONE_AXE_HEAD = ITEMS.register("tool_heads/stone_axe", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> STONE_SHOVEL_HEAD = ITEMS.register("tool_heads/stone_shovel", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> STONE_CHISEL_HEAD = ITEMS.register("tool_heads/stone_chisel", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> STONE_PICKAXE_HEAD = ITEMS.register("tool_heads/stone_pickaxe", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> STONE_HAMMER_HEAD = ITEMS.register("tool_heads/stone_hammer", ToolHeadItem::new);
		
		//Tools
			
			//Stone
			public static final RegistryObject<AxeItem> STONE_AXE = ITEMS.register("tools/stone_axe", () -> new AxeItem(ItemTier.STONE, 7.0f, -3.2f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ShovelItem> STONE_SHOVEL = ITEMS.register("tools/stone_shovel", () -> new ShovelItem(ItemTier.STONE, 1.5f, -3.0f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<PickaxeItem> STONE_PICKAXE = ITEMS.register("tools/stone_pickaxe", () -> new PickaxeItem(ItemTier.STONE, 1, -2.8f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ChiselItem> STONE_CHISEL = ITEMS.register("tools/stone_chisel", () -> new ChiselItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<HammerItem> STONE_HAMMER = ITEMS.register("tools/stone_hammer", () -> new HammerItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<MortarItem> STONE_MORTAR_AND_PESTLE = ITEMS.register("tools/stone_mortar", () -> new MortarItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			
		//Misc Items
		public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register("rock", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> PLANT_FIBER_CORDAGE = ITEMS.register("plant_fiber_cordage", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> KILN_CLAY_MIXTURE = ITEMS.register("kiln_clay_mixture", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
}
