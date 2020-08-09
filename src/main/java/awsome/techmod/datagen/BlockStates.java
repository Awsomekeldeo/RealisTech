package awsome.techmod.datagen;

import java.util.List;
import java.util.function.Function;

import awsome.techmod.Reference;
import awsome.techmod.blocks.BlockModOre;
import awsome.techmod.blocks.BlockOreSample;
import awsome.techmod.registry.Registration;
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
	
	private static List<BlockModOre> ORE_LIST = BlockModOre.getOreList();
	private static List<BlockOreSample> SAMPLE_LIST = BlockOreSample.getSampleList();

	protected BlockStates(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Reference.MODID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		registerFirebox();
		registerOreBlocks();
		registerCrucible();
		registerOreSamples();
	}
	
	private void registerOreBlocks() {
		for (BlockModOre ore : ORE_LIST) {
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
	
	private void registerCrucible() {
		ExistingModelFile modelCrucible = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/machines/crucible"));
		simpleBlock(Registration.CRUCIBLE.get(), modelCrucible);
	}
	
	private void registerOreSamples() {
		for (BlockOreSample sample : SAMPLE_LIST) {
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
