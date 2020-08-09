package awsome.techmod.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import awsome.techmod.worldgen.api.PlutonType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class OreDepositRegistration {
	
	private static OreDepositRegistration instance = new OreDepositRegistration();
	
	private OreDepositRegistry OreDepositRegistry = new OreDepositRegistry();
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
		copperDepositBlocks.put("techmod:copper_ore", 100);
		tinDepositBlocks.put("techmod:tin_ore", 100);
		nickelDepositBlocks.put("techmod:nickel_ore", 100);
		leadDepositBlocks.put("techmod:lead_ore", 100);
		silverDepositBlocks.put("techmod:silver_ore", 100);
		cobaltDepositBlocks.put("techmod:cobalt_ore", 100);
		zincDepositBlocks.put("techmod:zinc_ore", 100);
		coalDepositBlocks.put("techmod:mod_coal_ore", 100);
		ironDepositBlocks.put("techmod:mod_iron_ore", 100);
		goldDepositBlocks.put("techmod:mod_gold_ore", 100);
		diamondDepositBlocks.put("techmod:mod_diamond_ore", 100);
		emeraldDepositBlocks.put("techmod:mod_emerald_ore", 100);
		redstoneDepositBlocks.put("techmod:mod_redstone_ore", 100);
		lapisDepositBlocks.put("techmod:mod_lapis_ore", 100);
		
		copperDepositSamples.put("techmod:copper_sample", 100);
		tinDepositSamples.put("techmod:tin_sample", 100);
		nickelDepositSamples.put("techmod:nickel_sample", 100);
		leadDepositSamples.put("techmod:lead_sample", 100);
		silverDepositSamples.put("techmod:silver_sample", 100);
		cobaltDepositSamples.put("techmod:cobalt_sample", 100);
		zincDepositSamples.put("techmod:zinc_sample", 100);
		coalDepositSamples.put("techmod:coal_sample", 100);
		ironDepositSamples.put("techmod:iron_sample", 100);
		goldDepositSamples.put("techmod:gold_sample", 100);
		diamondDepositSamples.put("techmod:diamond_sample", 100);
		emeraldDepositSamples.put("techmod:emerald_sample", 100);
		redstoneDepositSamples.put("techmod:redstone_sample", 100);
		lapisDepositSamples.put("techmod:lapis_sample", 100);
		
		this.OreDepositRegistry.register(copperDepositBlocks, copperDepositSamples, 25, 52, 50, 36, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(tinDepositBlocks, tinDepositSamples, 44, 60, 45, 37, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(nickelDepositBlocks, nickelDepositSamples, 6, 40, 30, 17, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(leadDepositBlocks, leadDepositSamples, 16, 50, 40, 19, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(silverDepositBlocks, silverDepositSamples, 16, 50, 30, 19, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(zincDepositBlocks, zincDepositSamples, 35, 55, 20, 19, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(cobaltDepositBlocks, cobaltDepositSamples, 16, 32, 50, 15, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(coalDepositBlocks, coalDepositSamples, 28, 78, 40, 23, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(ironDepositBlocks, ironDepositSamples, 32, 60, 50, 22, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(goldDepositBlocks, goldDepositSamples, 5, 40, 35, 19, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(diamondDepositBlocks, diamondDepositSamples, 2, 20, 20, 15, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(redstoneDepositBlocks, redstoneDepositSamples, 5, 12, 35, 17, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(emeraldDepositBlocks, emeraldDepositSamples, 4, 32, 20, 15, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
		this.OreDepositRegistry.register(lapisDepositBlocks, lapisDepositSamples, 10, 38, 35, 31, new String[] { "minecraft:the_end" }, new ArrayList<String>(Arrays.asList(new String[] {"minecraft:stone"})), new ArrayList<Biome>(Arrays.asList(new Biome[] {})), Arrays.asList(new BiomeDictionary.Type[] {}), false, false, PlutonType.SPARSE, 1.0f);
	}

	public static OreDepositRegistration getInstance() {
		return instance;
	}
}
