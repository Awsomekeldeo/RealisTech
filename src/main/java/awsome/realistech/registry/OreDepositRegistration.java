package awsome.realistech.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import awsome.realistech.worldgen.api.PlutonType;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class OreDepositRegistration {

	private static OreDepositRegistration instance = new OreDepositRegistration();

	private OreDepositRegistry oreDepositRegistry = new OreDepositRegistry();
	private HashMap<String, Integer> copperDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> tinDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> nickelDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> silverDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> leadDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> cobaltDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> zincDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> coalDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> ironDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> goldDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> diamondDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> emeraldDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> redstoneDepositBlocks = new HashMap<>();
	private HashMap<String, Integer> lapisDepositBlocks = new HashMap<>();

	private HashMap<String, Integer> copperDepositSamples = new HashMap<>();
	private HashMap<String, Integer> tinDepositSamples = new HashMap<>();
	private HashMap<String, Integer> nickelDepositSamples = new HashMap<>();
	private HashMap<String, Integer> silverDepositSamples = new HashMap<>();
	private HashMap<String, Integer> leadDepositSamples = new HashMap<>();
	private HashMap<String, Integer> cobaltDepositSamples = new HashMap<>();
	private HashMap<String, Integer> zincDepositSamples = new HashMap<>();
	private HashMap<String, Integer> coalDepositSamples = new HashMap<>();
	private HashMap<String, Integer> ironDepositSamples = new HashMap<>();
	private HashMap<String, Integer> goldDepositSamples = new HashMap<>();
	private HashMap<String, Integer> diamondDepositSamples = new HashMap<>();
	private HashMap<String, Integer> emeraldDepositSamples = new HashMap<>();
	private HashMap<String, Integer> redstoneDepositSamples = new HashMap<>();
	private HashMap<String, Integer> lapisDepositSamples = new HashMap<>();

	public void init() {
		/*
		 * Using Block::getRegistryName.toString here b/c I'm a fucking idiot and forgot
		 * to change the mod id in these strings before. Now It shouldn't happen ever again.
		 */
		copperDepositBlocks.put(Registration.COPPER_ORE.get().getRegistryName().toString(), 100);
		tinDepositBlocks.put(Registration.TIN_ORE.get().getRegistryName().toString(), 100);
		nickelDepositBlocks.put(Registration.NICKEL_ORE.get().getRegistryName().toString(), 100);
		leadDepositBlocks.put(Registration.LEAD_ORE.get().getRegistryName().toString(), 100);
		silverDepositBlocks.put(Registration.SILVER_ORE.get().getRegistryName().toString(), 100);
		cobaltDepositBlocks.put(Registration.COBALT_ORE.get().getRegistryName().toString(), 100);
		zincDepositBlocks.put(Registration.ZINC_ORE.get().getRegistryName().toString(), 100);
		coalDepositBlocks.put(Registration.MOD_COAL_ORE.get().getRegistryName().toString(), 100);
		ironDepositBlocks.put(Registration.MOD_IRON_ORE.get().getRegistryName().toString(), 100);
		goldDepositBlocks.put(Registration.MOD_GOLD_ORE.get().getRegistryName().toString(), 100);
		diamondDepositBlocks.put(Registration.MOD_DIAMOND_ORE.get().getRegistryName().toString(), 100);
		emeraldDepositBlocks.put(Registration.MOD_EMERALD_ORE.get().getRegistryName().toString(), 100);
		redstoneDepositBlocks.put(Registration.MOD_REDSTONE_ORE.get().getRegistryName().toString(), 100);
		lapisDepositBlocks.put(Registration.MOD_LAPIS_ORE.get().getRegistryName().toString(), 100);

		copperDepositSamples.put(Registration.COPPER_SAMPLE.get().getRegistryName().toString(), 100);
		tinDepositSamples.put(Registration.TIN_SAMPLE.get().getRegistryName().toString(), 100);
		nickelDepositSamples.put(Registration.NICKEL_SAMPLE.get().getRegistryName().toString(), 100);
		leadDepositSamples.put(Registration.LEAD_SAMPLE.get().getRegistryName().toString(), 100);
		silverDepositSamples.put(Registration.SILVER_SAMPLE.get().getRegistryName().toString(), 100);
		cobaltDepositSamples.put(Registration.COBALT_SAMPLE.get().getRegistryName().toString(), 100);
		zincDepositSamples.put(Registration.ZINC_SAMPLE.get().getRegistryName().toString(), 100);
		coalDepositSamples.put(Registration.COAL_SAMPLE.get().getRegistryName().toString(), 100);
		ironDepositSamples.put(Registration.IRON_SAMPLE.get().getRegistryName().toString(), 100);
		goldDepositSamples.put(Registration.GOLD_SAMPLE.get().getRegistryName().toString(), 100);
		diamondDepositSamples.put(Registration.DIAMOND_SAMPLE.get().getRegistryName().toString(), 100);
		emeraldDepositSamples.put(Registration.EMERALD_SAMPLE.get().getRegistryName().toString(), 100);
		redstoneDepositSamples.put(Registration.REDSTONE_SAMPLE.get().getRegistryName().toString(), 100);
		lapisDepositSamples.put(Registration.LAPIS_SAMPLE.get().getRegistryName().toString(), 100);

		this.oreDepositRegistry.register(copperDepositBlocks, copperDepositSamples, 25, 52, 50, 36,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(tinDepositBlocks, tinDepositSamples, 44, 60, 45, 37,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(nickelDepositBlocks, nickelDepositSamples, 6, 40, 30, 17,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(leadDepositBlocks, leadDepositSamples, 16, 50, 40, 19,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(silverDepositBlocks, silverDepositSamples, 16, 50, 30, 19,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(zincDepositBlocks, zincDepositSamples, 35, 55, 20, 19,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(cobaltDepositBlocks, cobaltDepositSamples, 16, 32, 50, 15,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(coalDepositBlocks, coalDepositSamples, 28, 78, 40, 23,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(ironDepositBlocks, ironDepositSamples, 32, 60, 50, 22,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(goldDepositBlocks, goldDepositSamples, 5, 40, 35, 19,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(diamondDepositBlocks, diamondDepositSamples, 2, 20, 20, 15,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(redstoneDepositBlocks, redstoneDepositSamples, 5, 12, 35, 17,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(emeraldDepositBlocks, emeraldDepositSamples, 4, 32, 20, 15,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(lapisDepositBlocks, lapisDepositSamples, 10, 38, 35, 31,
				new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:stone" })),
				new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}),
				false, false, PlutonType.DENSE, 1.0f);
		this.oreDepositRegistry.register(Blocks.CLAY.getDefaultState(), Registration.CLAY_GRASS.get().getDefaultState(),
				Registration.GOLDENROD.get().getDefaultState(), 4, 36, 35, new String[] { "minecraft:the_end" },
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:dirt" })),
				new ArrayList<String>(Arrays.asList(new String[] { "minecraft:grass_block" })), 1.0f, PlutonType.LAYER);
	}

	public static OreDepositRegistration getInstance() {
		return instance;
	}
}
