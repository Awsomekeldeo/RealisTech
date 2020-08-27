package awsome.realistech.worldgen;

import com.google.common.collect.Lists;

import awsome.realistech.registry.Registration;
import awsome.realistech.worldgen.feature.ClayDepositConfig;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry {
	
	public static void addClayDeposits() {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			biome.addFeature(Decoration.UNDERGROUND_ORES, Registration.CLAY_DEPOSIT_FEATURE.get().withConfiguration(new ClayDepositConfig(Blocks.CLAY.getDefaultState(), Registration.CLAY_GRASS.get().getDefaultState(), 30, 4, Lists.newArrayList(Blocks.DIRT.getDefaultState()), Lists.newArrayList(Blocks.GRASS.getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(3))));
		}
	}
}
