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
		this.getBuilder(Tags.Blocks.DIRT).add(Registration.CLAY_GRASS.get());
		this.getBuilder(BlockTags.BAMBOO_PLANTABLE_ON).add(Registration.CLAY_GRASS.get());
		this.getBuilder(BlockTags.VALID_SPAWN).add(Registration.CLAY_GRASS.get());
	}
	
}
