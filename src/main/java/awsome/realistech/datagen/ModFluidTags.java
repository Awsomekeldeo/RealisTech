package awsome.realistech.datagen;

import awsome.realistech.registry.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.tags.FluidTags;

public class ModFluidTags extends FluidTagsProvider {
	
	public ModFluidTags(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerTags() {
		this.getBuilder(FluidTags.LAVA).add(Registration.MOLTEN_BRONZE.get(), Registration.MOLTEN_COBALT.get(), Registration.MOLTEN_COPPER.get(), Registration.MOLTEN_GOLD.get(), Registration.MOLTEN_IRON.get(), Registration.MOLTEN_LEAD.get(), Registration.MOLTEN_NICKEL.get(), Registration.MOLTEN_SILVER.get(), Registration.MOLTEN_TIN.get(), Registration.MOLTEN_ZINC.get());
	}

}
