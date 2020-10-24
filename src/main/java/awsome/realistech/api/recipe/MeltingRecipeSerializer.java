package awsome.realistech.api.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import awsome.realistech.util.RecipeHelper;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MeltingRecipeSerializer<T extends MeltingRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MeltingRecipe> {
	
	private final MeltingRecipeSerializer.IFactory<T> factory;
	
	public MeltingRecipeSerializer(MeltingRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public MeltingRecipe read(ResourceLocation recipeId, JsonObject json) {
		final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
		final Ingredient input = Ingredient.deserialize(inputElement);
		
		final JsonObject outputElement = JSONUtils.getJsonObject(json, "fluid");
		final FluidStack output = RecipeHelper.deserializeFluid(outputElement);
		
		final float temp = JSONUtils.getFloat(json, "temperature");
		
		return this.factory.create(recipeId, input, output, temp);
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		final Ingredient input = Ingredient.read(buffer);
		final FluidStack output = buffer.readFluidStack();
		final ResourceLocation id = buffer.readResourceLocation();
		final int temperature = buffer.readInt();
		
		return this.factory.create(id, input, output, temperature);
	}

	@Override
	public void write(PacketBuffer buffer, MeltingRecipe recipe) {
		recipe.input.write(buffer);
		buffer.writeFluidStack(recipe.output);
		buffer.writeResourceLocation(recipe.id);
		buffer.writeFloat(recipe.meltTemp);
	}
	
	public interface IFactory<T extends MeltingRecipe> {
		T create(ResourceLocation resourceLocation, Ingredient ingredient, FluidStack output, float temp);
	}
	
	public MeltingRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}
}
