package awsome.realistech.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;

public abstract class BaseLootTableProvider extends LootTableProvider {
	
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	protected final Map<Block, LootTable.Builder> blockLootTables = new HashMap<>();
	protected final Map<ResourceLocation, LootTable.Builder> blockLootTableOverrides = new HashMap<>();
	private final DataGenerator generator;
	
	public BaseLootTableProvider(DataGenerator generatorIn) {
		super(generatorIn);
		this.generator = generatorIn;
	}
	
	protected abstract void addTables();
	
	protected LootTable.Builder createStandardTable(String name, Block block) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(block));
		return LootTable.builder().addLootPool(builder);
	}
	
	protected LootTable.Builder createItemDropLootTable(String name, Item item) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(item))
				.acceptFunction(ExplosionDecay.builder());
		return LootTable.builder().addLootPool(builder);
	}
	
	protected LootTable.Builder createItemDropWithFortuneLootTabe(String name, Item item) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(item)
						.acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
						.acceptFunction(ExplosionDecay.builder()));
		return LootTable.builder().addLootPool(builder);
	}
	
	protected LootTable.Builder createMultiItemDropWithSilktouch(String name, Item item, Item silktouchItem, int minDrops, int maxDrops) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(AlternativesLootEntry.builder(ItemLootEntry.builder(silktouchItem)
								.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
						).alternatively(ItemLootEntry.builder(item)
								.acceptFunction(SetCount.builder(RandomValueRange.of(minDrops, maxDrops)))
								.acceptFunction(ExplosionDecay.builder())));
		return LootTable.builder().addLootPool(builder);
	}
	
	protected LootTable.Builder createMultiItemDropWithSilktouchAndFortune(String name, Item item, Item silktouchItem, int minDrops, int maxDrops, boolean applyUniformBonus, int uniformBonusCount) {
		if (applyUniformBonus) {
			LootPool.Builder builder = LootPool.builder()
					.name(name)
					.rolls(ConstantRange.of(1))
					.addEntry(AlternativesLootEntry.builder(ItemLootEntry.builder(silktouchItem)
									.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
							).alternatively(ItemLootEntry.builder(item)
									.acceptFunction(SetCount.builder(RandomValueRange.of(minDrops, maxDrops)))
									.acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, uniformBonusCount))
									.acceptFunction(ExplosionDecay.builder())));
			return LootTable.builder().addLootPool(builder);
		}else {
			LootPool.Builder builder = LootPool.builder()
					.name(name)
					.rolls(ConstantRange.of(1))
					.addEntry(AlternativesLootEntry.builder(ItemLootEntry.builder(silktouchItem)
									.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
							).alternatively(ItemLootEntry.builder(item)
									.acceptFunction(SetCount.builder(RandomValueRange.of(minDrops, maxDrops)))
									.acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))
									.acceptFunction(ExplosionDecay.builder())));
			return LootTable.builder().addLootPool(builder);
		}
	}
	
	protected LootTable.Builder createMultiItemDropWithFortune(String name, Item item, int minDrops, int maxDrops, boolean applyUniformBonus, int uniformBonusCount) {
		if (applyUniformBonus) {
			LootPool.Builder builder = LootPool.builder()
					.name(name)
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(item)
							.acceptFunction(SetCount.builder(RandomValueRange.of(minDrops, maxDrops)))
							.acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, uniformBonusCount))
							.acceptFunction(ExplosionDecay.builder()));
			
			return LootTable.builder().addLootPool(builder);
		}else{
			LootPool.Builder builder = LootPool.builder()
					.name(name)
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(item)
							.acceptFunction(SetCount.builder(RandomValueRange.of(minDrops, maxDrops)))
							.acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
							.acceptFunction(ExplosionDecay.builder()));
			
			return LootTable.builder().addLootPool(builder);
		}
	}
	
	protected LootTable.Builder createGrassTable(String name, Item item, int minDrops, int maxDrops) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(AlternativesLootEntry.builder(
						ItemLootEntry.builder(Items.GRASS)
						.acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS)))
						.alternatively(ItemLootEntry.builder(Items.WHEAT_SEEDS)
								.acceptCondition(RandomChance.builder(0.125f))
								.acceptFunction(ExplosionDecay.builder()))
						.alternatively(ItemLootEntry.builder(item)
								.acceptCondition(RandomChance.builder(0.5f))
								.acceptFunction(SetCount.builder(RandomValueRange.of(minDrops, maxDrops)))
								.acceptFunction(ExplosionDecay.builder()))));
		return LootTable.builder().addLootPool(builder);
	}
	
	@Override
	public void act(DirectoryCache cache) {
		addTables();
		
		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		for (Map.Entry<Block, LootTable.Builder> entry : blockLootTables.entrySet()) {
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
		}
		
		for (Map.Entry<ResourceLocation, LootTable.Builder> entry : blockLootTableOverrides.entrySet()) {
			tables.put(entry.getKey(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
		}
		
		writeTables(cache, tables);
	}
	
	private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
		Path outputFolder = this.generator.getOutputFolder();
		tables.forEach((key, lootTable) -> {
			Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try {
				IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
			} catch (IOException e) {
				LOGGER.error("Couldn't write loot table {}", path, e);
			}
		});
	}
	
	@Override
	public String getName() {
		return "Realistech Loot Tables";
	}
}
