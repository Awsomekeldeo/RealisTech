package awsome.realistech.datagen;

import java.util.List;
import java.util.function.Function;

import awsome.realistech.Reference;
import awsome.realistech.blocks.AnvilBlock;
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
	private static List<AnvilBlock> ANVIL_LIST = AnvilBlock.getAnvilList();

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
		registerAnvils();
		registerVanillaClayGrass();
		registerKaoliniteClayGrass();
		registerFluid(Registration.MOLTEN_COPPER.getBlock());
		registerFluid(Registration.MOLTEN_TIN.getBlock());
		registerFluid(Registration.MOLTEN_SILVER.getBlock());
		registerFluid(Registration.MOLTEN_NICKEL.getBlock());
		registerFluid(Registration.MOLTEN_LEAD.getBlock());
		registerFluid(Registration.MOLTEN_COBALT.getBlock());
		registerFluid(Registration.MOLTEN_ZINC.getBlock());
		registerFluid(Registration.MOLTEN_IRON.getBlock());
		registerFluid(Registration.MOLTEN_GOLD.getBlock());
		registerFluid(Registration.MOLTEN_BRONZE.getBlock());
		registerFlower(Registration.GOLDENROD.get());
		registerFlower(Registration.KAOLINITE_LILY.get());
		registerSimpleBlock(Registration.KAOLINITE_CLAY.get());
		registerSimpleBlock(Registration.FIREBRICKS.get());
		registerMediumHeatFurnace();
	}
	
	private void registerFluid(Block block) {
		ExistingModelFile modelFluid = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/fluids/molten"));
		simpleBlock(block, modelFluid);
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
	
	@SuppressWarnings("unused")
	private void registerSimpleBlock(Block block, String middleTexturePath) {
		ResourceLocation texture = new ResourceLocation(Reference.MODID, "blocks/" + middleTexturePath + block.getRegistryName().getPath());
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
		ResourceLocation tex2 = new ResourceLocation(Reference.MODID, "blocks/firebricks");
		ResourceLocation tex3 = new ResourceLocation(Reference.MODID, "blocks/machines/firebox_front");
		ResourceLocation tex4 = new ResourceLocation(Reference.MODID, "blocks/machines/firebox_front_on");
		BlockModelBuilder modelFirebox = models().cube("block/machines/firebox", tex2, tex1, tex3, tex, tex, tex).texture("particle", tex3);
		BlockModelBuilder modelFireboxLit = models().cube("block/machines/firebox_lit", tex2, tex1, tex4, tex, tex, tex).texture("particle", tex3);
		orientedBlock(Registration.FIREBOX.get(), state -> {
			if (state.get(BlockStateProperties.LIT)) {
				return modelFireboxLit;
			}else{
				return modelFirebox;
			}
		});
	}
	
	private void registerMediumHeatFurnace() {
		ResourceLocation tex = new ResourceLocation(Reference.MODID, "blocks/machines/weak_furnace_side");
		ResourceLocation tex1 = new ResourceLocation(Reference.MODID, "blocks/machines/weak_furnace_top");
		ResourceLocation tex3 = new ResourceLocation(Reference.MODID, "blocks/machines/weak_furnace_front");
		ResourceLocation tex4 = new ResourceLocation(Reference.MODID, "blocks/machines/weak_furnace_front_on");
		BlockModelBuilder modelMedHeatFurnace = models().cube("block/machines/weak_furnace", tex1, tex1, tex3, tex, tex, tex).texture("particle", tex3);
		BlockModelBuilder modelMedHeatFurnaceLit = models().cube("block/machines/weak_furnace_lit", tex1, tex1, tex4, tex, tex, tex).texture("particle", tex3);
		orientedBlock(Registration.WEAK_FURNACE.get(), state -> {
			if (state.get(BlockStateProperties.LIT)) {
				return modelMedHeatFurnaceLit;
			}else{
				return modelMedHeatFurnace;
			}
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
			if (sample.equals(Registration.ROCK.get())) {
				ResourceLocation texture2 = new ResourceLocation(Reference.MODID, "items/" + sample.getRegistryName().getPath());
				BlockModelBuilder modelRock = models().withExistingParent("block/" + sample.getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/cluster_base")).texture("2", texture2).texture("particle", texture2);
				simpleBlock(sample, modelRock);
			}else if (sample.equals(Registration.STICK.get())){
				ExistingModelFile modelStick = models().getExistingFile(new ResourceLocation(Reference.MODID, "block/stick_block"));
				simpleBlock(Registration.STICK.get(), modelStick);
			}else{
				ResourceLocation texture = new ResourceLocation(Reference.MODID, "items/clusters/" + sample.getRegistryName().getPath().substring(0, sample.getRegistryName().getPath().length() - 7));
				BlockModelBuilder modelSample = models().withExistingParent("block/ores/samples/" + sample.getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/ores/samples/cluster_base")).texture("2", texture).texture("particle", texture);
				simpleBlock(sample, modelSample);
			}
		}
	}
	
	private void registerAnvils() {
		for (AnvilBlock anvil : ANVIL_LIST) {
			if (anvil.equals(Registration.STONE_ANVIL.get())) {
				ResourceLocation tex = new ResourceLocation("minecraft:block/stone");
				ResourceLocation tex1 = new ResourceLocation("minecraft:block/smooth_stone");
				ResourceLocation tex2 = new ResourceLocation("minecraft:block/smooth_stone_slab_side");
				BlockModelBuilder modelAnvil = models().withExistingParent("block/anvils/" + anvil.getRegistryName().getPath(), new ResourceLocation(Reference.MODID, "block/anvils/anvil_base")).texture("0", tex).texture("1", tex1).texture("2", tex2).texture("particle", tex);
				orientedBlock(anvil, state -> {
					return modelAnvil;
				});
			}
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
