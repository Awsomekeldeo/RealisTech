package awsome.realistech.datagen;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class LootTables extends BaseLootTableProvider {

	public LootTables(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void addTables() {
		
		//Machines
		blockLootTables.put(Registration.CRUCIBLE.get(), createStandardTable("crucible", Registration.CRUCIBLE.get()));
		blockLootTables.put(Registration.FIREBOX.get(), createStandardTable("firebox", Registration.FIREBOX.get()));
		blockLootTables.put(Registration.KILN.get(), createStandardTable("kiln", Registration.KILN.get()));
		
		//Ores
		blockLootTables.put(Registration.COPPER_ORE.get(), createItemDropLootTable("copper_ore", Registration.COPPER_CLUSTER.get()));
		blockLootTables.put(Registration.TIN_ORE.get(), createItemDropLootTable("tin_ore", Registration.TIN_CLUSTER.get()));
		blockLootTables.put(Registration.SILVER_ORE.get(), createItemDropLootTable("silver_ore", Registration.SILVER_CLUSTER.get()));
		blockLootTables.put(Registration.LEAD_ORE.get(), createItemDropLootTable("lead_ore", Registration.LEAD_CLUSTER.get()));
		blockLootTables.put(Registration.NICKEL_ORE.get(), createItemDropLootTable("nickel_ore", Registration.NICKEL_CLUSTER.get()));
		blockLootTables.put(Registration.ZINC_ORE.get(), createItemDropLootTable("zinc_ore", Registration.ZINC_CLUSTER.get()));
		blockLootTables.put(Registration.COBALT_ORE.get(), createItemDropLootTable("cobalt_ore", Registration.COBALT_CLUSTER.get()));
		blockLootTables.put(Registration.MOD_IRON_ORE.get(), createItemDropLootTable("mod_iron_ore", Registration.IRON_CLUSTER.get()));
		blockLootTables.put(Registration.MOD_GOLD_ORE.get(), createItemDropLootTable("mod_gold_ore", Registration.GOLD_CLUSTER.get()));
		blockLootTables.put(Registration.MOD_COAL_ORE.get(), createItemDropWithFortuneLootTabe("mod_coal_ore", Items.COAL));
		blockLootTables.put(Registration.MOD_DIAMOND_ORE.get(), createItemDropWithFortuneLootTabe("mod_diamond_ore", Items.DIAMOND));
		blockLootTables.put(Registration.MOD_EMERALD_ORE.get(), createItemDropWithFortuneLootTabe("mod_emerald_ore", Items.EMERALD));
		blockLootTables.put(Registration.MOD_REDSTONE_ORE.get(), createMultiItemDropWithFortune("mod_redstone_ore", Items.REDSTONE, 4, 5, true, 1));
		blockLootTables.put(Registration.MOD_LAPIS_ORE.get(), createMultiItemDropWithFortune("mod_lapis_ore", Items.LAPIS_LAZULI, 4, 9, false, 0));
		
		//Flowers
		blockLootTables.put(Registration.GOLDENROD.get(), createStandardTable("goldenrod", Registration.GOLDENROD.get()));
		blockLootTables.put(Registration.KAOLINITE_LILY.get(), createStandardTable("kaolinite_lily", Registration.KAOLINITE_LILY.get()));
		
		//Other
		blockLootTables.put(Registration.VANILLA_CLAY_GRASS.get(), createMultiItemDropWithSilktouch("clay_grass", Items.CLAY_BALL, Registration.VANILLA_CLAY_GRASS_ITEM.get(), 4, 4));
		blockLootTables.put(Registration.KAOLINITE_CLAY_GRASS.get(), createMultiItemDropWithSilktouch("kaolinite_clay_grass", Registration.KAOLINITE_CLAY_BALL.get(), Registration.KAOLINITE_CLAY_GRASS_ITEM.get(), 4, 4));
		blockLootTables.put(Registration.KAOLINITE_CLAY.get(), createMultiItemDropWithSilktouch("kaolinite_clay", Registration.KAOLINITE_CLAY_BALL.get(), Registration.KAOLINITE_CLAY_ITEM.get(), 4, 4));
		
		//Samples
		blockLootTables.put(Registration.COPPER_SAMPLE.get(), createItemDropLootTable("copper_sample", Registration.COPPER_CLUSTER.get()));
		blockLootTables.put(Registration.TIN_SAMPLE.get(), createItemDropLootTable("tin_sample", Registration.TIN_CLUSTER.get()));
		blockLootTables.put(Registration.SILVER_SAMPLE.get(), createItemDropLootTable("silver_sample", Registration.SILVER_CLUSTER.get()));
		blockLootTables.put(Registration.LEAD_SAMPLE.get(), createItemDropLootTable("lead_sample", Registration.LEAD_CLUSTER.get()));
		blockLootTables.put(Registration.NICKEL_SAMPLE.get(), createItemDropLootTable("nickel_sample", Registration.NICKEL_CLUSTER.get()));
		blockLootTables.put(Registration.ZINC_SAMPLE.get(), createItemDropLootTable("zinc_sample", Registration.ZINC_CLUSTER.get()));
		blockLootTables.put(Registration.COBALT_SAMPLE.get(), createItemDropLootTable("cobalt_sample", Registration.COBALT_CLUSTER.get()));
		blockLootTables.put(Registration.IRON_SAMPLE.get(), createItemDropLootTable("iron_sample", Registration.IRON_CLUSTER.get()));
		blockLootTables.put(Registration.GOLD_SAMPLE.get(), createItemDropLootTable("gold_sample", Registration.GOLD_CLUSTER.get()));
		blockLootTables.put(Registration.COAL_SAMPLE.get(), createItemDropWithFortuneLootTabe("coal_sample", Items.COAL));
		blockLootTables.put(Registration.DIAMOND_SAMPLE.get(), createItemDropWithFortuneLootTabe("diamond_sample", Items.DIAMOND));
		blockLootTables.put(Registration.EMERALD_SAMPLE.get(), createItemDropWithFortuneLootTabe("emerald_sample", Items.EMERALD));
		blockLootTables.put(Registration.REDSTONE_SAMPLE.get(), createMultiItemDropWithFortune("redstone_sample", Items.REDSTONE, 4, 5, true, 1));
		blockLootTables.put(Registration.LAPIS_SAMPLE.get(), createMultiItemDropWithFortune("lapis_sample", Items.LAPIS_LAZULI, 4, 9, false, 0));
		blockLootTableOverrides.put(new ResourceLocation(Reference.MODID, "blocks/grass_override"), createGrassTable("grass_override", Registration.PLANT_FIBER.get(), 3, 4));
	}
}
