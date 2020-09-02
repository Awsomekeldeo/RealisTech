package awsome.realistech.datagen;

import awsome.realistech.registry.Registration;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

public class ModBlockTags extends BlockTagsProvider {

	public ModBlockTags(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerTags() {
		this.getBuilder(Tags.Blocks.DIRT).add(Registration.VANILLA_CLAY_GRASS.get()).add(Registration.KAOLINITE_CLAY_GRASS.get());
		this.getBuilder(BlockTags.BAMBOO_PLANTABLE_ON).add(Registration.VANILLA_CLAY_GRASS.get()).add(Registration.KAOLINITE_CLAY_GRASS.get());
		this.getBuilder(BlockTags.VALID_SPAWN).add(Registration.VANILLA_CLAY_GRASS.get()).add(Registration.KAOLINITE_CLAY_GRASS.get());
		this.getBuilder(BlockTags.FLOWERS).add(Registration.GOLDENROD.get()).add(Registration.KAOLINITE_LILY.get());
		this.getBuilder(BlockTags.SMALL_FLOWERS).add(Registration.GOLDENROD.get()).add(Registration.KAOLINITE_LILY.get());
	}
	
}
