package awsome.realistech.data.generators;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import awsome.realistech.util.GeneralUtils;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class AlloyRecipeBuilder {
	
	private final List<FluidStack> inputs = Lists.newArrayList();
	private final FluidStack output;
	
	public AlloyRecipeBuilder(FluidStack output) {
		this.output = output;
	}
	
	public static AlloyRecipeBuilder alloyRecipe(FluidStack output) {
		return new AlloyRecipeBuilder(output);
	}
	
	public AlloyRecipeBuilder addInput(FluidStack input) {
		this.inputs.add(input);
		return this;
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.FLUIDS.getKey(this.output.getFluid()));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.FLUIDS.getKey(this.output.getFluid());
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Alloy Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Alloy Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.FLUIDS.getKey(this.output.getFluid()).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		this.validate(id);
		consumerIn.accept(new AlloyRecipeBuilder.Result(id, this.inputs, this.output));
	}
	
	private void validate(ResourceLocation id) {
		if (GeneralUtils.findDuplicates(this.inputs).size() > 0) {
			throw new IllegalStateException("Alloy Recipe " + id + " cannot have duplicate fluid types");
		}
	}
	
	public static class Result implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final List<FluidStack> inputs;
		private final FluidStack output;
		
		public Result(ResourceLocation id, List<FluidStack> inputsIn, FluidStack output) {
			this.id = id;
			this.inputs = inputsIn;
			this.output = output;
		}

		@Override
		public void serialize(JsonObject json) {
			
			JsonArray inputs = new JsonArray();
			
			for (int i = 0; i < this.inputs.size(); i++) {
				JsonObject input1 = new JsonObject();
				input1.addProperty("id", ForgeRegistries.FLUIDS.getKey(this.inputs.get(i).getFluid()).toString());
				input1.addProperty("amount", this.inputs.get(i).getAmount());
				inputs.add(input1);
			}
			
			json.add("inputs", inputs);
			
			JsonObject fluid = new JsonObject();
			fluid.addProperty("id", ForgeRegistries.FLUIDS.getKey(this.output.getFluid()).toString());
			fluid.addProperty("amount", this.output.getAmount());
			
			json.add("output", fluid);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.ALLOY_RECIPE_SERIALIZER.get();
		}

		@Override
		public JsonObject getAdvancementJson() {
			return null;
		}

		@Override
		public ResourceLocation getAdvancementID() {
			return null;
		}
		
	}
}
