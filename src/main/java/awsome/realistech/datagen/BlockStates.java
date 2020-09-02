package awsome.realistech.datagen;

import java.util.List;
import java.util.function.Function;

import awsome.realistech.Reference;
import awsome.realistech.blocks.ModOreBlock;
import awsome.realistech.blocks.OreSampleBlock;
import awsome.realistech.registry.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;

public class BlockStates extends BlockStateProvider {
	
	private static List<ModOreBlock> ORE_LIST = ModOreBlock.getOreList();
	private static List<OreSampleBlock> SAMPLE_LIST = OreSampleBlock.getSampleList();

	protected BlockStates(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Reference.MODID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		registerFirebox();
		registerOreBlocks();
		registerCrucible();
		registerOreSamples();
		registerKiln();
		registerVanillaClayGrass();
		registerKaoliniteClayGrass();
		registerFlower(Registration.GOLDENROD.get());
		registerFlower(Registration.KAOLINITE_LILY.get());
		registerSimpleBlock(Registration.KAOLINITE_CLAY.get());
	}
	
	private void registerFlower(Block block) {
		ResourceLocation texture = new ResourceLocation(Reference.MODID, "blocks/flowers/" + block.getRegistryName().getPath());
		BlockModelBuilder modelFlower = models().cross("block/flowers/" + block.getRegistryName().getPath(), texture).texture("particle", texture);
		simpleBlock(block, modelFlower);
	}

	private void registerSimpleBlock(Block block) {
		ResourceLocation texture = new ResourceLocation(Reference.MODID, "blocks/" + block.getRegistryName().getPath());
		BlockModelBuilder modelSimpleBlock = models().cubeAll("block/" + block.getRegistryName().getPath(), texture).texture("particle", texture);
		simpleBlock(block, modelSimpleBlock);
	}
	
	private void registerVanillaClayGrass() {
		ExistingModelFile modelClayGrass = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/clay_grass"));
		ExistingModelFile modelSnowyClayGrass = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/snowy_clay_grass"));
		getVariantBuilder(Registration.VANILLA_CLAY_GRASS.get()).forAllStates(state -> {
			if (!state.get(BlockStateProperties.SNOWY)) {
				return ConfiguredModel.allYRotations(modelClayGrass, 0, false);
			}else {
				return ConfiguredModel.builder().modelFile(modelSnowyClayGrass).build();
			}
		});
	}
	
	private void registerKaoliniteClayGrass() {
		ExistingModelFile modelClayGrass = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/kaolinite_clay_grass"));
		ExistingModelFile modelSnowyClayGrass = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/snowy_kaolinite_clay_grass"));
		getVariantBuilder(Registration.KAOLINITE_CLAY_GRASS.get()).forAllStates(state -> {
			if (!state.get(BlockStateProperties.SNOWY)) {
				return ConfiguredModel.allYRotations(modelClayGrass, 0, false);
			}else {
				return ConfiguredModel.builder().modelFile(modelSnowyClayGrass).build();
			}
		});
	}

	private void registerOreBlocks() {
		for (ModOreBlock ore : ORE_LIST) {
			ResourceLocation texture = new ResourceLocation(Reference.MODID, "blocks/ores/" + ore.getRegistryName().getPath().substring(0, ore.getRegistryName().getPath().length() - 4));
			BlockModelBuilder modelOre = models().cubeAll("block/ores/" + ore.getRegistryName().getPath(), texture).texture("particle", texture);
			simpleBlock(ore, modelOre);
		}
	}

	private void registerFirebox() {
		ResourceLocation tex = new ResourceLocation(Reference.MODID, "blocks/machines/firebox_side");
		ResourceLocation tex1 = new ResourceLocation(Reference.MODID, "blocks/machines/firebox_top");
		ResourceLocation tex2 = new ResourceLocation(Reference.MODID, "blocks/machines/firebox_bottom");
		ResourceLocation tex3 = new ResourceLocation(Reference.MODID, "blocks/machines/firebox_front");
		BlockModelBuilder modelFirebox = models().cube("block/machines/firebox", tex2, tex1, tex3, tex, tex, tex).texture("particle", tex3);
		orientedBlock(Registration.FIREBOX.get(), state -> {
			return modelFirebox;
		});
	}
	
	private void registerKiln() {
		ExistingModelFile modelKiln = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/machines/kiln"));
		orientedBlock(Registration.KILN.get(), state -> {
			return modelKiln;
		});
	}
	
	private void registerCrucible() {
		ExistingModelFile modelCrucible = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/machines/crucible"));
		simpleBlock(Registration.CRUCIBLE.get(), modelCrucible);
	}
	
	private void registerOreSamples() {
		for (OreSampleBlock sample : SAMPLE_LIST) {
			ResourceLocation texture = new ResourceLocation(Reference.MODID, "items/clusters/" + sample.getRegistryName().getPath().substring(0, sample.getRegistryName().getPath().length() - 7));
			BlockModelBuilder modelSample = models().withExistingParent("block/ores/samples/" + sample.getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/cluster_base")).texture("2", texture).texture("particle", texture);
			simpleBlock(sample, modelSample);
		}
	}
	
	private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.HORIZONTAL_FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
                            .build();
                });
    }
}
