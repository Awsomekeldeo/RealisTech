package awsome.realistech.api.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AnvilRecipeSerializer<T extends AnvilRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AnvilRecipe> {

	private final IFactory<T> factory;
	
	public AnvilRecipeSerializer(AnvilRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public AnvilRecipe read(ResourceLocation recipeId, JsonObject json) {
		final JsonElement input1Element = JSONUtils.isJsonArray(json, "first_input") ? JSONUtils.getJsonArray(json, "first_input") : JSONUtils.getJsonObject(json, "first_input");
		final Ingredient input1 = Ingredient.deserialize(input1Element);
		
		final JsonElement input2Element = JSONUtils.isJsonArray(json, "second_input") ? JSONUtils.getJsonArray(json, "second_input") : JSONUtils.getJsonObject(json, "second_input");
		final Ingredient input2 = Ingredient.deserialize(input2Element);
		
		final ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), false);
		
		return this.factory.create(recipeId, input1, input2, output);
	}

	@Override
	public AnvilRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		final Ingredient input1 = Ingredient.read(buffer);
		final Ingredient input2 = Ingredient.read(buffer);
		final ItemStack output = buffer.readItemStack();
		final ResourceLocation id = buffer.readResourceLocation();
		
		return this.factory.create(id, input1, input2, output);	
	}

	@Override
	public void write(PacketBuffer buffer, AnvilRecipe recipe) {
		recipe.ingredient1.write(buffer);
		recipe.ingredient2.write(buffer);
		buffer.writeItemStack(recipe.result);
		buffer.writeResourceLocation(recipe.id);
	}
	
	public interface IFactory<T extends AnvilRecipe> {
		T create(ResourceLocation resourceLocation, Ingredient ingredient1, Ingredient ingredient2, ItemStack output);
	}
	
	public AnvilRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}
}
