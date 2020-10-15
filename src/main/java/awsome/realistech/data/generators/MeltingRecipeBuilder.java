package awsome.realistech.data.generators;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import awsome.realistech.Reference;
import awsome.realistech.registry.Registration;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class MeltingRecipeBuilder {
	
	private final Ingredient input;
	private final FluidStack output;
	private final float meltTemp;
	
	public MeltingRecipeBuilder(Ingredient ingredientIn, FluidStack outputIn, float meltTempIn) {
		this.input = ingredientIn;
		this.output = outputIn;
		this.meltTemp = meltTempIn;
	}
	
	public static MeltingRecipeBuilder meltingRecipe(Ingredient input, FluidStack output, float meltTemp) {
		return new MeltingRecipeBuilder(input, output, meltTemp);
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, ForgeRegistries.FLUIDS.getKey(this.output.getFluid()));
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, String save, boolean isRootFolder) {
		ResourceLocation resourceLocation = ForgeRegistries.FLUIDS.getKey(this.output.getFluid());
		if (!isRootFolder) {
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Kiln Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(save));
			}
		}else{
			if ((new ResourceLocation(save)).equals(resourceLocation)) {
				throw new IllegalStateException("Kiln Recipe " + save + " should remove its 'save' argument");
			}else{
				this.build(consumerIn, new ResourceLocation(Reference.MODID, save + ForgeRegistries.FLUIDS.getKey(this.output.getFluid()).getPath()));
			}
		}
	}
	
	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new MeltingRecipeBuilder.Result(id, this.input, this.output, this.meltTemp));
	}
	
	public static class Result implements IFinishedRecipe {
		
		private final ResourceLocation id;
		private final Ingredient input;
		private final FluidStack output;
		private final float meltTemp;
		
		public Result(ResourceLocation id, Ingredient ingredient, FluidStack output, float meltTemp) {
			this.id = id;
			this.input = ingredient;
			this.output = output;
			this.meltTemp = meltTemp;
		}

		@Override
		public void serialize(JsonObject json) {
			json.add("input", this.input.serialize());
			
			JsonObject fluid = new JsonObject();
			fluid.addProperty("id", ForgeRegistries.FLUIDS.getKey(this.output.getFluid()).toString());
			fluid.addProperty("amount", this.output.getAmount());
			
			json.add("fluid", fluid);
			
			json.addProperty("temperature", this.meltTemp);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return Registration.MELTING_RECIPE_SERIALIZER.get();
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
