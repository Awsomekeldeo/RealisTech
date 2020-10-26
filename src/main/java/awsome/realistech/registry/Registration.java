package awsome.realistech.registry;

import awsome.realistech.Reference;
import awsome.realistech.api.recipe.AlloyRecipe;
import awsome.realistech.api.recipe.AlloyRecipeSerializer;
import awsome.realistech.api.recipe.AnvilRecipe;
import awsome.realistech.api.recipe.AnvilRecipeSerializer;
import awsome.realistech.api.recipe.HandworkRecipeSerializer;
import awsome.realistech.api.recipe.KilnRecipe;
import awsome.realistech.api.recipe.KilnRecipeSerializer;
import awsome.realistech.api.recipe.KnappingRecipe;
import awsome.realistech.api.recipe.MeltingRecipe;
import awsome.realistech.api.recipe.MeltingRecipeSerializer;
import awsome.realistech.api.recipe.MoldingRecipe;
import awsome.realistech.api.recipe.NonConsumingShapelessRecipe;
import awsome.realistech.api.recipe.NonConsumingShaplessRecipeSerializer;
import awsome.realistech.api.recipe.SolidificationRecipe;
import awsome.realistech.api.recipe.SolidificationRecipeSerializer;
import awsome.realistech.api.recipe.WeakSmeltingRecipe;
import awsome.realistech.api.recipe.WeakSmeltingRecipeSerializer;
import awsome.realistech.blocks.AnvilBlock;
import awsome.realistech.blocks.ClayGrassBlock;
import awsome.realistech.blocks.CrucibleBlock;
import awsome.realistech.blocks.FireboxBlock;
import awsome.realistech.blocks.KilnBlock;
import awsome.realistech.blocks.MediumHeatFurnaceBlock;
import awsome.realistech.blocks.ModOreBlock;
import awsome.realistech.blocks.OreSampleBlock;
import awsome.realistech.blocks.OreSampleBlock.SampleShape;
import awsome.realistech.data.loot.GrassDrops.GrassDropSerializer;
import awsome.realistech.inventory.container.CrucibleContainer;
import awsome.realistech.inventory.container.FireboxContainer;
import awsome.realistech.inventory.container.KilnContainer;
import awsome.realistech.inventory.container.KnappingContainer;
import awsome.realistech.inventory.container.MediumHeatFurnaceContainer;
import awsome.realistech.inventory.container.MoldingContainer;
import awsome.realistech.items.BrickItem;
import awsome.realistech.items.CeramicMoldItem;
import awsome.realistech.items.ChiselItem;
import awsome.realistech.items.FireclayItem;
import awsome.realistech.items.HammerItem;
import awsome.realistech.items.IngotItem;
import awsome.realistech.items.MortarItem;
import awsome.realistech.items.OreClusterItem;
import awsome.realistech.items.ProspectorsPickaxeItem;
import awsome.realistech.items.SolidCeramicMoldItem;
import awsome.realistech.items.ToolHeadItem;
import awsome.realistech.items.UnfiredCeramicMoldItem;
import awsome.realistech.setup.ModSetup;
import awsome.realistech.tileentity.AnvilTileEntity;
import awsome.realistech.tileentity.CrucibleTileEntity;
import awsome.realistech.tileentity.FireboxTileEntity;
import awsome.realistech.tileentity.KilnTileEntity;
import awsome.realistech.tileentity.MediumHeatFurnaceTileEntity;
import awsome.realistech.util.ModItemTier;
import awsome.realistech.util.MoldType;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MODID);
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
	private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MODID);
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Reference.MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Reference.MODID);
	private static final FluidRegisterer FLUID_REGISTERER = new FluidRegisterer();
	
	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	private static FluidAttributes.Builder moltenBuilder() {
		return FluidAttributes.builder(MOLTEN_STILL_TEXTURE, MOLTEN_FLOWING_TEXTURE).density(2000).viscosity(10000).temperature(1000).sound(SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
	}
	
	public static final ResourceLocation MOLTEN_STILL_TEXTURE = new ResourceLocation(Reference.MODID, "blocks/fluids/molten_metal_still");
	public static final ResourceLocation MOLTEN_FLOWING_TEXTURE = new ResourceLocation(Reference.MODID, "blocks/fluids/molten_metal");
	
	//Fluids
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_COPPER = FLUID_REGISTERER.register("molten_copper", moltenBuilder().color(0xffc67b5b).temperature(1085), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_TIN = FLUID_REGISTERER.register("molten_tin", moltenBuilder().color(0xffacacac).temperature(231), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_NICKEL = FLUID_REGISTERER.register("molten_nickel", moltenBuilder().color(0xffb3b3a0).temperature(1455), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_SILVER = FLUID_REGISTERER.register("molten_silver", moltenBuilder().color(0xffb1b9b9).temperature(961), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_LEAD = FLUID_REGISTERER.register("molten_lead", moltenBuilder().color(0xff54545a).temperature(621), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_COBALT = FLUID_REGISTERER.register("molten_cobalt", moltenBuilder().color(0xffa0a09a).temperature(1495), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_ZINC = FLUID_REGISTERER.register("molten_zinc", moltenBuilder().color(0xffe0e0e0).temperature(419), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_IRON = FLUID_REGISTERER.register("molten_iron", moltenBuilder().color(0xffc15b5b).temperature(1538), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_GOLD = FLUID_REGISTERER.register("molten_gold", moltenBuilder().color(0xffbcc048).temperature(1064), Material.LAVA, 9);
		public static final FluidObject<ForgeFlowingFluid> MOLTEN_BRONZE = FLUID_REGISTERER.register("molten_bronze", moltenBuilder().color(0xffc69d5b).temperature(950), Material.LAVA, 9);
	
	//Global Loot Modifiers
		public static final RegistryObject<GrassDropSerializer> GRASS_DROP_OVERRIDE = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("grass_override", GrassDropSerializer::new);
	
	//Recipe Serializers
		public static final RegistryObject<MeltingRecipeSerializer<MeltingRecipe>> MELTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("melting", () -> new MeltingRecipeSerializer<>(MeltingRecipe::new));
		public static final RegistryObject<KilnRecipeSerializer<KilnRecipe>> KILN_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("kiln", () -> new KilnRecipeSerializer<>(KilnRecipe::new));
		public static final RegistryObject<NonConsumingShaplessRecipeSerializer<NonConsumingShapelessRecipe>> NON_CONSUMING_SHAPELESS_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("non_consuming_shapeless", () -> new NonConsumingShaplessRecipeSerializer<>(NonConsumingShapelessRecipe::new));
		public static final RegistryObject<HandworkRecipeSerializer<KnappingRecipe>> KNAPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("knapping", () -> new HandworkRecipeSerializer<>(KnappingRecipe::new));
		public static final RegistryObject<HandworkRecipeSerializer<MoldingRecipe>> MOLDING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("molding", () -> new HandworkRecipeSerializer<>(MoldingRecipe::new));
		public static final RegistryObject<WeakSmeltingRecipeSerializer<WeakSmeltingRecipe>> WEAK_SMELTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("weak_smelting", () -> new WeakSmeltingRecipeSerializer<>(WeakSmeltingRecipe::new));
		public static final RegistryObject<AnvilRecipeSerializer<AnvilRecipe>> ANVIL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("anvil_hammering", () -> new AnvilRecipeSerializer<>(AnvilRecipe::new));
		public static final RegistryObject<AlloyRecipeSerializer<AlloyRecipe>> ALLOY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloying", () -> new AlloyRecipeSerializer<>(AlloyRecipe::new));
		public static final RegistryObject<SolidificationRecipeSerializer<SolidificationRecipe>> SOLIDIFICATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("solidification", () -> new SolidificationRecipeSerializer<>(SolidificationRecipe::new));
	
	//Blocks
		
		//Ores
		public static final RegistryObject<ModOreBlock> COPPER_ORE = BLOCKS.register("copper_ore", () -> new ModOreBlock(0));
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
		public static final RegistryObject<OreSampleBlock> COPPER_SAMPLE = BLOCKS.register("copper_sample", () -> new OreSampleBlock(0));
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
		public static final RegistryObject<MediumHeatFurnaceBlock> WEAK_FURNACE = BLOCKS.register("weak_furnace", MediumHeatFurnaceBlock::new);
		
		//Anvils
		public static final RegistryObject<AnvilBlock> STONE_ANVIL = BLOCKS.register("stone_anvil", () -> new AnvilBlock(0, 1.5f, 6.0f));
		
		//Clay Grasses
		public static final RegistryObject<ClayGrassBlock> VANILLA_CLAY_GRASS = BLOCKS.register("clay_grass", ClayGrassBlock::new);
		public static final RegistryObject<ClayGrassBlock> KAOLINITE_CLAY_GRASS = BLOCKS.register("kaolinite_clay_grass", ClayGrassBlock::new);
		
		//Clays
		public static final RegistryObject<Block> KAOLINITE_CLAY = BLOCKS.register("kaolinite_clay", () -> new Block(Properties.create(Material.CLAY).hardnessAndResistance(0.6f).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(-1)));
		
		//Misc Blocks
		public static final RegistryObject<Block> FIREBRICKS = BLOCKS.register("firebricks", () -> new Block(Properties.create(Material.ROCK, MaterialColor.ADOBE).hardnessAndResistance(1.25f, 7.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
	
	//Tile Entities
		
		//Machines
		public static final RegistryObject<TileEntityType<FireboxTileEntity>> FIREBOX_TILEENTITY = TILE_ENTITIES.register("firebox", () -> TileEntityType.Builder.create(FireboxTileEntity::new, FIREBOX.get()).build(null));
		public static final RegistryObject<TileEntityType<CrucibleTileEntity>> CRUCIBLE_TILEENTITY = TILE_ENTITIES.register("crucible", () -> TileEntityType.Builder.create(CrucibleTileEntity::new, CRUCIBLE.get()).build(null));
		public static final RegistryObject<TileEntityType<KilnTileEntity>> KILN_TILEENTITY = TILE_ENTITIES.register("kiln", () -> TileEntityType.Builder.create(KilnTileEntity::new, KILN.get()).build(null));
		public static final RegistryObject<TileEntityType<MediumHeatFurnaceTileEntity>> WEAK_FURNACE_TILEENTITY = TILE_ENTITIES.register("weak_furnace", () -> TileEntityType.Builder.create(MediumHeatFurnaceTileEntity::new, WEAK_FURNACE.get()).build(null));
		public static final RegistryObject<TileEntityType<AnvilTileEntity>> ANVIL_TILEENTITY = TILE_ENTITIES.register("anvil", () -> TileEntityType.Builder.create(AnvilTileEntity::new, STONE_ANVIL.get()).build(null));
	
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
		
		public static final RegistryObject<ContainerType<MediumHeatFurnaceContainer>> WEAK_FURNACE_CONTAINER = CONTAINERS.register("weak_furnace", () -> IForgeContainerType.create((windowId, inv, data) -> {
			BlockPos pos = data.readBlockPos();
			World world = inv.player.getEntityWorld();
			return new MediumHeatFurnaceContainer(windowId, world, pos, inv, inv.player);
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
		public static final RegistryObject<Item> WEAK_FURNACE_ITEM = ITEMS.register("machines/weak_furnace", () -> new BlockItem(WEAK_FURNACE.get(), new Item.Properties().group(ModSetup.REALISTECH_MACHINES)));
		
		//Anvils
		public static final RegistryObject<Item> STONE_ANVIL_ITEM = ITEMS.register("anvils/stone", () -> new BlockItem(STONE_ANVIL.get(), new Item.Properties().group(ModSetup.REALISTECH_MACHINES)));
		
		//Flowers
		public static final RegistryObject<Item> GOLDENROD_ITEM = ITEMS.register("goldenrod", () -> new BlockItem(GOLDENROD.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		public static final RegistryObject<Item> KAOLINITE_LILY_ITEM = ITEMS.register("kaolinite_lily", () -> new BlockItem(KAOLINITE_LILY.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
		//Clay Grasses
		public static final RegistryObject<Item> VANILLA_CLAY_GRASS_ITEM = ITEMS.register("clay_grass", () -> new BlockItem(VANILLA_CLAY_GRASS.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		public static final RegistryObject<Item> KAOLINITE_CLAY_GRASS_ITEM = ITEMS.register("kaolinite_clay_grass", () -> new BlockItem(KAOLINITE_CLAY_GRASS.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
		//Clays
		public static final RegistryObject<Item> KAOLINITE_CLAY_ITEM = ITEMS.register("kaolinite_clay", () -> new BlockItem(KAOLINITE_CLAY.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
		//Misc Blocks
		public static final RegistryObject<Item> FIREBRICKS_ITEM = ITEMS.register("firebricks", () -> new BlockItem(FIREBRICKS.get(), new Item.Properties().group(ModSetup.REALISTECH_MISC)));
		
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
		public static final RegistryObject<IngotItem> BRONZE_INGOT = ITEMS.register("ingots/bronze_ingot", IngotItem::new);
		
		//Plates
		public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("plates/copper_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> TIN_PLATE = ITEMS.register("plates/tin_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> NICKEL_PLATE = ITEMS.register("plates/nickel_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> LEAD_PLATE = ITEMS.register("plates/lead_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> SILVER_PLATE = ITEMS.register("plates/silver_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> ZINC_PLATE = ITEMS.register("plates/zinc_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> COBALT_PLATE = ITEMS.register("plates/cobalt_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> BRONZE_PLATE = ITEMS.register("plates/bronze_plate", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		
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
		
			//Fired, Solidified
			public static final RegistryObject<SolidCeramicMoldItem> FIRED_FILLED_CERAMIC_INGOT_MOLD = ITEMS.register("ceramic_molds/fired/ingot_solidified", () -> new SolidCeramicMoldItem(MoldType.INGOT));
			public static final RegistryObject<SolidCeramicMoldItem> FIRED_FILLED_CERAMIC_PICKAXE_MOLD = ITEMS.register("ceramic_molds/fired/pickaxe_solidified", () -> new SolidCeramicMoldItem(MoldType.PICKAXE));
			public static final RegistryObject<SolidCeramicMoldItem> FIRED_FILLED_CERAMIC_AXE_MOLD = ITEMS.register("ceramic_molds/fired/axe_solidified", () -> new SolidCeramicMoldItem(MoldType.AXE));
			public static final RegistryObject<SolidCeramicMoldItem> FIRED_FILLED_CERAMIC_SHOVEL_MOLD = ITEMS.register("ceramic_molds/fired/shovel_solidified", () -> new SolidCeramicMoldItem(MoldType.SHOVEL));
			public static final RegistryObject<SolidCeramicMoldItem> FIRED_FILLED_CERAMIC_SWORD_MOLD = ITEMS.register("ceramic_molds/fired/sword_solidified", () -> new SolidCeramicMoldItem(MoldType.SWORD));
			public static final RegistryObject<SolidCeramicMoldItem> FIRED_FILLED_CERAMIC_PROPICK_MOLD = ITEMS.register("ceramic_molds/fired/prospectors_pickaxe_solidified", () -> new SolidCeramicMoldItem(MoldType.PROPICK));
		
			//Fired
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_INGOT_MOLD = ITEMS.register("ceramic_molds/fired/ingot", () -> new CeramicMoldItem(144));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_PICKAXE_MOLD = ITEMS.register("ceramic_molds/fired/pickaxe", () -> new CeramicMoldItem(432));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_AXE_MOLD = ITEMS.register("ceramic_molds/fired/axe", () -> new CeramicMoldItem(432));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_SHOVEL_MOLD = ITEMS.register("ceramic_molds/fired/shovel", () -> new CeramicMoldItem(144));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_SWORD_MOLD = ITEMS.register("ceramic_molds/fired/sword", () -> new CeramicMoldItem(288));
			public static final RegistryObject<CeramicMoldItem> FIRED_CERAMIC_PROPICK_MOLD = ITEMS.register("ceramic_molds/fired/prospectors_pickaxe", () -> new CeramicMoldItem(288));
			
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
			
			//Copper
			public static final RegistryObject<ToolHeadItem> COPPER_AXE_HEAD = ITEMS.register("tool_heads/copper_axe", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> COPPER_SHOVEL_HEAD = ITEMS.register("tool_heads/copper_shovel", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> COPPER_PROPICK_HEAD = ITEMS.register("tool_heads/copper_chisel", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> COPPER_PICKAXE_HEAD = ITEMS.register("tool_heads/copper_pickaxe", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> COPPER_SWORD_HEAD = ITEMS.register("tool_heads/copper_hammer", ToolHeadItem::new);
			
			//Bronze
			public static final RegistryObject<ToolHeadItem> BRONZE_AXE_HEAD = ITEMS.register("tool_heads/bronze_axe", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> BRONZE_SHOVEL_HEAD = ITEMS.register("tool_heads/bronze_shovel", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> BRONZE_PROPICK_HEAD = ITEMS.register("tool_heads/bronze_chisel", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> BRONZE_PICKAXE_HEAD = ITEMS.register("tool_heads/bronze_pickaxe", ToolHeadItem::new);
			public static final RegistryObject<ToolHeadItem> BRONZE_SWORD_HEAD = ITEMS.register("tool_heads/bronze_hammer", ToolHeadItem::new);
			
		//Tools
			
			//Stone
			public static final RegistryObject<AxeItem> STONE_AXE = ITEMS.register("tools/stone_axe", () -> new AxeItem(ModItemTier.STONE, 7.0f, -3.2f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ShovelItem> STONE_SHOVEL = ITEMS.register("tools/stone_shovel", () -> new ShovelItem(ModItemTier.STONE, 1.5f, -3.0f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<PickaxeItem> STONE_PICKAXE = ITEMS.register("tools/stone_pickaxe", () -> new PickaxeItem(ModItemTier.STONE, 1, -2.8f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ChiselItem> STONE_CHISEL = ITEMS.register("tools/stone_chisel", () -> new ChiselItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<HammerItem> STONE_HAMMER = ITEMS.register("tools/stone_hammer", () -> new HammerItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<MortarItem> STONE_MORTAR_AND_PESTLE = ITEMS.register("tools/stone_mortar", () -> new MortarItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			
			//Copper
			public static final RegistryObject<AxeItem> COPPER_AXE = ITEMS.register("tools/copper_axe", () -> new AxeItem(ModItemTier.COPPER, 7.0f, -3.2f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ShovelItem> COPPER_SHOVEL = ITEMS.register("tools/copper_shovel", () -> new ShovelItem(ModItemTier.COPPER, 1.5f, -3.0f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<PickaxeItem> COPPER_PICKAXE = ITEMS.register("tools/copper_pickaxe", () -> new PickaxeItem(ModItemTier.COPPER, 1, -2.8f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ProspectorsPickaxeItem> COPPER_PROPICK = ITEMS.register("tools/copper_propick", () -> new ProspectorsPickaxeItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<SwordItem> COPPER_SWORD = ITEMS.register("tools/copper_sword", () -> new SwordItem(ModItemTier.COPPER, 3, -2.4f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			
			//Bronze
			public static final RegistryObject<AxeItem> BRONZE_AXE = ITEMS.register("tools/bronze_axe", () -> new AxeItem(ModItemTier.BRONZE, 7.0f, -3.2f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ShovelItem> BRONZE_SHOVEL = ITEMS.register("tools/bronze_shovel", () -> new ShovelItem(ModItemTier.BRONZE, 1.5f, -3.0f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<PickaxeItem> BRONZE_PICKAXE = ITEMS.register("tools/bronze_pickaxe", () -> new PickaxeItem(ModItemTier.BRONZE, 1, -2.8f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<ProspectorsPickaxeItem> BRONZE_PROPICK = ITEMS.register("tools/bronze_propick", () -> new ProspectorsPickaxeItem(new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			public static final RegistryObject<SwordItem> BRONZE_SWORD = ITEMS.register("tools/bronze_sword", () -> new SwordItem(ModItemTier.BRONZE, 3, -2.4f, new Item.Properties().group(ModSetup.REALISTECH_TOOLS)));
			
		//Misc Items
		public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register("rock", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> PLANT_FIBER_CORDAGE = ITEMS.register("plant_fiber_cordage", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
		public static final RegistryObject<Item> KILN_CLAY_MIXTURE = ITEMS.register("kiln_clay_mixture", () -> new Item(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS)));
}
