package awsome.realistech.api.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import awsome.realistech.util.GeneralUtils;
import awsome.realistech.util.RecipeHelper;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AlloyRecipeSerializer<T extends AlloyRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlloyRecipe> {

	private final AlloyRecipeSerializer.IFactory<T> factory;
	
	public AlloyRecipeSerializer(AlloyRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public AlloyRecipe read(ResourceLocation recipeId, JsonObject json) {
		
		final NonNullList<FluidStack> inputs = readFluidStacks(JSONUtils.getJsonArray(json, "inputs"));

		final JsonObject outputElement = JSONUtils.getJsonObject(json, "output");
		final FluidStack output = RecipeHelper.deserializeFluid(outputElement);
		
		if (GeneralUtils.findDuplicates(inputs).size() > 0) {
			throw new JsonSyntaxException("Alloying Recipe cannot have duplicate fluid types");
		}
		
		if (inputs.size() < 2) {
			throw new JsonSyntaxException("Alloying recipe must have at least 2 inputs");
		}
		
		return this.factory.create(inputs, output, recipeId);
	}
	
	private static NonNullList<FluidStack> readFluidStacks(JsonArray json) {
		
		NonNullList<FluidStack> stackList = NonNullList.create();
		
		for (int i = 0; i < json.size(); i++) {
			
			if (!json.get(i).isJsonObject()) {
				throw new JsonSyntaxException("Expected inputs to be an array of objects");
			}
			
			FluidStack stack = RecipeHelper.deserializeFluid(json.get(i).getAsJsonObject());
			if (!stack.isEmpty()) {
				stackList.add(stack);
			}
		}
		
		return stackList;
	}

	@Override
	public AlloyRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		
		int i = buffer.readVarInt();
		NonNullList<FluidStack> inputs = NonNullList.withSize(i, FluidStack.EMPTY);
		
		for (int j = 0; j < inputs.size(); j++) {
			inputs.set(j, buffer.readFluidStack());
		}
		
		final ResourceLocation id = buffer.readResourceLocation();
		final FluidStack output = buffer.readFluidStack();
		
		return this.factory.create(inputs, output, id);
	}

	@Override
	public void write(PacketBuffer buffer, AlloyRecipe recipe) {
		buffer.writeVarInt(recipe.inputs.size());
		for (FluidStack stack : recipe.inputs) {
			buffer.writeFluidStack(stack);
		}
		buffer.writeResourceLocation(recipe.id);
		buffer.writeFluidStack(recipe.output);
	}
	
	public interface IFactory<T extends AlloyRecipe> {
		T create(NonNullList<FluidStack> inputs, FluidStack output, ResourceLocation id);
	}
	
	public AlloyRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}

}
