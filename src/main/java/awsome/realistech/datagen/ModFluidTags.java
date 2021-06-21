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
		//Still
		this.getBuilder(FluidTags.LAVA).add(Registration.MOLTEN_BRONZE.get(), Registration.MOLTEN_COBALT.get(), Registration.MOLTEN_COPPER.get(), Registration.MOLTEN_GOLD.get(), Registration.MOLTEN_IRON.get(), Registration.MOLTEN_LEAD.get(), Registration.MOLTEN_NICKEL.get(), Registration.MOLTEN_SILVER.get(), Registration.MOLTEN_TIN.get(), Registration.MOLTEN_ZINC.get());
		this.getBuilder(FluidTags.WATER).add(Registration.STEAM.get());
		
		//Flowing
		this.getBuilder(FluidTags.LAVA).add(Registration.MOLTEN_BRONZE.getFlowing(), Registration.MOLTEN_COBALT.getFlowing(), Registration.MOLTEN_COPPER.getFlowing(), Registration.MOLTEN_GOLD.getFlowing(), Registration.MOLTEN_IRON.getFlowing(), Registration.MOLTEN_LEAD.getFlowing(), Registration.MOLTEN_NICKEL.getFlowing(), Registration.MOLTEN_SILVER.getFlowing(), Registration.MOLTEN_TIN.getFlowing(), Registration.MOLTEN_ZINC.getFlowing());
		this.getBuilder(FluidTags.WATER).add(Registration.STEAM.getFlowing());
	}

}
